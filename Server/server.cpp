#include "connectionthread.h"
#include "server.h"

Server::Server(Player *player, QObject *parent) :
    QTcpServer(parent), player(player)
{
}

void Server::incomingConnection(qintptr socketDescriptor)
{
    ConnectionThread *thread = new ConnectionThread(player, socketDescriptor, this);

    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    thread->start();
}
