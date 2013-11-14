#pragma once

#include "socketmessanger.h"

#include <QTcpSocket>
#include <QThread>
#include <QObject>

class ConnectionThread : public QThread
{
    Q_OBJECT

public:
    ConnectionThread(int socketDescriptor, QObject *parent);

    void run();

private:
    int socketDescriptor;
    QTcpSocket *tcpSocket;
    SocketMessanger *messanger;

    qint32 readNumber();
    QByteArray readByteArray(qint32 byteLength);
};
