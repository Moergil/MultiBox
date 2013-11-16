#pragma once

#include <QTcpServer>
#include <QStringList>

#include <player/player.h>

class Server : public QTcpServer
{
    Q_OBJECT

private:
    Player *player;

public:
    Server(Player *player, QObject *parent = 0);

protected:
    void incomingConnection(qintptr socketDescriptor);
};
