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
    PlaylistState getPlaylistState();
    Multimedia getCurrentMultimedia();
    qint32 getPosition();
    bool isPlaying();
    qint32 getDuration();
    LibraryItem *getLibraryItem(qint64 itemId);
};
