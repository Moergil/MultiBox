#include "socketmessenger.h"

#include <util/bytearrayconverter.h>

SocketMessenger::SocketMessenger(int socketDescriptor, QObject *parent) throw(MessengerException)
    : QObject(parent)
{
    tcpSocket = new QTcpSocket(this);

    if (!tcpSocket->setSocketDescriptor(socketDescriptor))
    {
        throw MessengerException("Socket connection problem!");
    }
}

SocketMessenger::SocketMessenger(const QString &hostName, quint16 port, QObject *parent) throw(MessengerException)
    : QObject(parent)
{
    tcpSocket = new QTcpSocket(this);

    tcpSocket->connectToHost(hostName, port);

    if (!tcpSocket->waitForConnected())
    {
        throw MessengerException("Socket connection problem!");
    }
}

bool SocketMessenger::canWaitForMessage()
{
    return tcpSocket->isOpen() && tcpSocket->isValid();
}

DataMessage SocketMessenger::waitForMessage() throw(MessengerException)
{
    qint32 messageType = readNumber();
    qint32 messageLength = readNumber();

    QByteArray byteArray = readByteArray(messageLength);
    DataContent dataContent(byteArray);

    return DataMessage(messageType, dataContent);
}

void SocketMessenger::writeMessage(DataMessage message) throw(MessengerException)
{
    writeNumber(message.getMessageType());
    writeNumber(message.getDataContent().getQByteArray().length());
    writeByteArray(message.getDataContent().getQByteArray());
}

void SocketMessenger::close()
{
    tcpSocket->disconnectFromHost();

    tcpSocket->close();
}

qint32 SocketMessenger::readNumber() throw(MessengerException)
{
    QByteArray numberBuffer = readByteArray(QINT32_LENGTH);
    return ByteArrayConverter::parseQInt32(numberBuffer);
}

void SocketMessenger::writeByteArray(QByteArray byteArray) throw(MessengerException)
{
    if(byteArray.length() > 0)
    {
        tcpSocket->write(byteArray.constData(), byteArray.length());
        tcpSocket->flush();

        if(nonWrittenDataExists() && !tcpSocket->waitForBytesWritten())
        {
            throw MessengerException("Too slow connection!");
        }
    }
}

void SocketMessenger::writeNumber(qint32 number) throw(MessengerException)
{
    QByteArray buffer = ByteArrayConverter::fromQInt32(number);
    writeByteArray(buffer);
}

bool SocketMessenger::nonReadDataExists()
{
    return tcpSocket->bytesAvailable() > 0;
}

bool SocketMessenger::nonWrittenDataExists()
{
    return tcpSocket->bytesToWrite() > 0;
}

QByteArray SocketMessenger::readByteArray(qint32 bytesToRead) throw(MessengerException)
{
    QByteArray buffer;

    while(bytesToRead > 0)
    {
        if(!nonReadDataExists() && !tcpSocket->waitForReadyRead())
        {
            throw MessengerException("Not enough data in the stream!");
        }

        qint32 bytesAvailable = tcpSocket->bytesAvailable();
        qint32 bytesToReadNow = bytesAvailable < bytesToRead ? bytesAvailable : bytesToRead;

        buffer.append(tcpSocket->read(bytesToReadNow));
        bytesToRead -= bytesToReadNow;
    }

    return buffer;
}
