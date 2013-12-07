#pragma once

#include <QTcpSocket>
#include <QThread>
#include <QObject>

#include <network/socketmessenger.h>
#include <player/player.h>

class ConnectionThread : public QThread
{
    Q_OBJECT

private:
    int socketDescriptor;
    QTcpSocket *tcpSocket;
    Player *player;
    SocketMessenger *messanger;

public:
    ConnectionThread(Player *player, int socketDescriptor, QObject *parent);

    void run();
};
