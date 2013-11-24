#include <QObject>

#include <player/player.h>

#pragma once

class PlayerHandler : QObject
{
    Q_OBJECT

private:
    Player *player;

signals:
    void setPlaying(bool playing);

private:
    void setConnections();

public:
    PlayerHandler(Player *player, QObject *parent = 0);
};
