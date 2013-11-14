#include "connectionthread.h"
#include "server.h"

Server::Server(QObject *parent) :
    QTcpServer(parent)
{
}

void Server::incomingConnection(qintptr socketDescriptor)
{
    ConnectionThread *thread = new ConnectionThread(socketDescriptor, this);

    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    thread->start();
}
