#include "player.h"

void Player::setConnections()
{
    connect(this, SIGNAL(songRequested()), this->playlist, SLOT(shiftPlaylist()));
    connect(this->playlist, SIGNAL(currentItemChanged()), this, SLOT(playNext()));
    connect(this->playlist, SIGNAL(playlistIsEmpty()), this, SLOT(setPlayingToFalse()));
}

void Player::setPlayingToFalse()
{
    setPlaying(false);
}

void Player::emitSongRequestedSignal()
{
    emit songRequested();
}

Player::Player()
{
    this->playlist = new Playlist(this);
    this->library = new Library(this);

    this->setConnections();
}

Player::~Player()
{
}

void Player::skipSong()
{
    emitSongRequestedSignal();
}

QString Player::getPlayerName() const
{
    return playerName;
}

Playlist *Player::getPlaylist() const
{
    return this->playlist;
}

Library *Player::getLibrary() const
{
    return this->library;
}

void Player::setPlayerName(const QString &name)
{
    playerName = name;
}
