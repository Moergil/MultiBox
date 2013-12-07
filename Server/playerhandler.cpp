#include "playerhandler.h"

void PlayerHandler::setConnections()
{
    connect(this, SIGNAL(setPlaying(bool)), player, SLOT(setPlaying(bool)));
}

PlayerHandler::PlayerHandler(Player *player, QObject *parent)
    : QObject(parent), player(player)
{
    setConnections();
}

PlaylistState PlayerHandler::getPlaylistState()
{
    PlaylistState playlistState;

    QMetaObject::invokeMethod(player->getPlaylist(), "getPlaylistState",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(PlaylistState, playlistState));

    return playlistState;
}

Multimedia PlayerHandler::getCurrentMultimedia()
{
    Multimedia *multimedia;

    QMetaObject::invokeMethod(player->getPlaylist(), "getCurrentItem",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(Multimedia *, multimedia));

    return *multimedia;
}

qint32 PlayerHandler::getPosition()
{
    qint32 position = 0;

    QMetaObject::invokeMethod(player, "getPosition",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(int, position));

    return position;
}

bool PlayerHandler::isPlaying()
{
    bool playing = 0;

    QMetaObject::invokeMethod(player, "isPlaying",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(bool, playing));

    return playing;
}

qint32 PlayerHandler::getDuration()
{
    qint32 duration = 0;

    QMetaObject::invokeMethod(player, "getDuration",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(int, duration));

    return duration;
}

LibraryItem *PlayerHandler::getLibraryItem(qint64 itemId)
{
    LibraryItem *item;

    QMetaObject::invokeMethod(player->getLibrary(), "readById",
                              Qt::BlockingQueuedConnection,
                              Q_RETURN_ARG(LibraryItem *,item),
                              Q_ARG(qint64, itemId));

    return *item;
}
