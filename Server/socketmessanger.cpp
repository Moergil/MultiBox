#include "bytearrayparser.h"
#include "socketmessanger.h"

SocketMessanger::SocketMessanger(int socketDescriptor)
    : socketDescriptor(socketDescriptor)
{
    tcpSocket = new QTcpSocket();
}

SocketMessanger::~SocketMessanger()
{
    delete tcpSocket;
}

void SocketMessanger::open()
{
    if (!tcpSocket->setSocketDescriptor(socketDescriptor))
    {
        emit connectionError(tcpSocket->error());
        qFatal("Socket connection problem!");
        return;
    }
}

DataMessage SocketMessanger::waitForMessage()
{
    qint32 messageType = readNumber();
    qint32 messageLength = readNumber();

    QByteArray byteArray = readByteArray(messageLength);

    return DataMessage(messageType, byteArray);
}

void SocketMessanger::writeMessage(DataMessage message)
{
    writeNumber(message.getMessageType());
    writeNumber(message.getByteArray().length());
    writeByteArray(message.getByteArray());
}

void SocketMessanger::close()
{
    tcpSocket->disconnectFromHost();
}

qint32 SocketMessanger::readNumber()
{
    QByteArray numberBuffer = readByteArray(4);
    return ByteArrayParser::parseQInt32(numberBuffer);
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
            qFatal("Too slow connection!");
            return;
        }
    }
}

void SocketMessanger::writeNumber(qint32 number)
{
    QByteArray buffer = ByteArrayParser::fromQInt32(number);
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

QByteArray SocketMessanger::readByteArray(qint32 bytesToRead)
{
    QByteArray buffer;

    while(bytesToRead > 0)
    {
        if(!nonReadDataExists() && !tcpSocket->waitForReadyRead())
        {
            emit notEnoughDataError();
            qFatal("Not enough data in the stream!");
            return buffer;
        }

        qint32 bytesAvailable = tcpSocket->bytesAvailable();
        qint32 bytesToReadNow = bytesAvailable < bytesToRead ? bytesAvailable : bytesToRead;

        buffer.append(tcpSocket->read(bytesToReadNow));
        bytesToRead -= bytesToReadNow;
    }

    return buffer;
}
