#include "datamessage.h"
#include "messangerinterface.h"

#include <QTcpSocket>

#pragma once

class SocketMessanger : public QObject, MessangerInterface
{
    Q_OBJECT

private:
    QTcpSocket *tcpSocket;
    int socketDescriptor;

signals:
    void notEnoughDataError();
    void tooSlowTransferError();
    void connectionError(QTcpSocket::SocketError socketError);

private:
    QByteArray readByteArray(qint32 byteLength);
    qint32 readNumber();
    void writeByteArray(QByteArray byteArray);
    void writeNumber(qint32 number);
    bool nonReadDataExists();
    bool nonWrittenDataExists();

public:
    SocketMessanger(int socketDescriptor);
    ~SocketMessanger();

    void open();
    DataMessage waitForMessage();
    void writeMessage(DataMessage message);
    void close();
};
