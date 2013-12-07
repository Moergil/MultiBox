#include "datamessage.h"
#include "messangerinterface.h"
#include "messengerexception.h"

#include <QTcpSocket>

#pragma once

class SocketMessenger : public QObject, public MessengerInterface
{
    Q_OBJECT

private:
    static const qint32 QINT32_LENGTH = 4;

    QTcpSocket *tcpSocket;

private:
    QByteArray readByteArray(qint32 byteLength) throw(MessengerException);
    qint32 readNumber() throw(MessengerException);
    void writeByteArray(QByteArray byteArray) throw(MessengerException);
    void writeNumber(qint32 number) throw(MessengerException);
    bool nonReadDataExists();
    bool nonWrittenDataExists();

public:
    SocketMessenger(int socketDescriptor, QObject *parent = 0) throw(MessengerException);
    SocketMessenger(const QString &hostName, quint16 port, QObject *parent = 0) throw(MessengerException);

    bool canWaitForMessage();
    DataMessage waitForMessage() throw(MessengerException);
    void writeMessage(DataMessage message) throw(MessengerException);
    void close();
};
