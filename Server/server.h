#pragma once

#include <QTcpServer>
#include <QStringList>

class Server : public QTcpServer
{
    Q_OBJECT
public:
    explicit Server(QObject *parent = 0);

protected:
    void incomingConnection(qintptr socketDescriptor);
};
