#include "socketmessanger.h"

#include <util/bytearrayconverter.h>

#include <QThread>

SocketMessanger::SocketMessanger(int socketDescriptor, QObject *parent)
    : QObject(parent)
{
    tcpSocket = new QTcpSocket(this);

    if (!tcpSocket->setSocketDescriptor(socketDescriptor))
    {
        emit connectionError(tcpSocket->error());
        qWarning("Socket connection problem!");
        return;
    }

    setConnections();
}

SocketMessanger::SocketMessanger(const QString &hostName, quint16 port, QObject *parent)
    : QObject(parent)
{
    tcpSocket = new QTcpSocket(this);

    tcpSocket->connectToHost(hostName, port);

    if (!tcpSocket->waitForConnected())
    {
        emit connectionError(tcpSocket->error());
        qWarning("Socket connection problem!");
        return;
    }

    setConnections();
}

bool SocketMessanger::canWaitForMessage()
{
    return tcpSocket->isOpen() && tcpSocket->isValid();
}

DataMessage SocketMessanger::waitForMessage()
{
    qint32 messageType = readNumber();
    qint32 messageLength = readNumber();

    QByteArray byteArray = readByteArray(messageLength);
    DataContent dataContent(byteArray);

    return DataMessage(messageType, dataContent);
}

void SocketMessanger::writeMessage(DataMessage message)
{
    writeNumber(message.getMessageType());
    writeNumber(message.getDataContent().getQByteArray().length());
    writeByteArray(message.getDataContent().getQByteArray());
}

void SocketMessanger::close()
{
    tcpSocket->disconnectFromHost();

    tcpSocket->close();
}

qint32 SocketMessanger::readNumber()
{
    QByteArray numberBuffer = readByteArray(QINT32_LENGTH);
    return ByteArrayConverter::parseQInt32(numberBuffer);
}

void SocketMessanger::writeByteArray(QByteArray byteArray)
{
    if(byteArray.length() > 0)
    {
        tcpSocket->write(byteArray.constData(), byteArray.length());
        tcpSocket->flush();

        if(nonWrittenDataExists() && !tcpSocket->waitForBytesWritten())
        {
            emit tooSlowTransferError();
            qWarning("Too slow connection!");
            return;
        }
    }
}

void SocketMessanger::writeNumber(qint32 number)
{
    QByteArray buffer = ByteArrayConverter::fromQInt32(number);
    writeByteArray(buffer);
}

bool SocketMessanger::nonReadDataExists()
{
    return tcpSocket->bytesAvailable() > 0;
}

bool SocketMessanger::nonWrittenDataExists()
{
    return tcpSocket->bytesToWrite() > 0;
}

void SocketMessanger::setConnections()
{
    connect(this, SIGNAL(notEnoughDataError()), this, SLOT(quitReading()));
    connect(this, SIGNAL(tooSlowTransferError()), this, SLOT(quitReading()));
}

void SocketMessanger::quitReading()
{
    close();
    //koniec thread, opravit citanie :-)
}

QByteArray SocketMessanger::readByteArray(qint32 bytesToRead)
{
    QByteArray buffer;

    while(bytesToRead > 0)
    {
        if(!nonReadDataExists() && !tcpSocket->waitForReadyRead())
        {
            emit notEnoughDataError();
            qWarning("Not enough data in the stream!");
            return buffer;
        }

        qint32 bytesAvailable = tcpSocket->bytesAvailable();
        qint32 bytesToReadNow = bytesAvailable < bytesToRead ? bytesAvailable : bytesToRead;

        buffer.append(tcpSocket->read(bytesToReadNow));
        bytesToRead -= bytesToReadNow;
    }

    return buffer;
}
