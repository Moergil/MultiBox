#include "datamessage.h"
#include "messangerinterface.h"

#include <QTcpSocket>

#pragma once

class SocketMessanger : public QObject, public MessangerInterface
{
    Q_OBJECT

private:
    static const qint32 QINT32_LENGTH = 4;

    QTcpSocket *tcpSocket;

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
    void setConnections();

private slots:
    void quitReading();

public:
    SocketMessanger(int socketDescriptor, QObject *parent = 0);
    SocketMessanger(const QString &hostName, quint16 port, QObject *parent = 0);

    bool canWaitForMessage();
    DataMessage waitForMessage();
    void writeMessage(DataMessage message);
    void close();
};
