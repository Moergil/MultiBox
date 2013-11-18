#include "connectionthread.h"
#include "messageprocessor.h"

#include <QDataStream>
#include <QByteArray>

ConnectionThread::ConnectionThread(Player *player, int socketDescriptor, QObject *parent)
    : QThread(parent), socketDescriptor(socketDescriptor), player(player)
{
}

void ConnectionThread::run()
{
    QObject object;

    PlayerHandler *handler = new PlayerHandler(player, &object);
    MessageProcessor processor(handler, socketDescriptor);

    processor.proccess();
}

