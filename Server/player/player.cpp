#include "player.h"

void Player::setConnections()
{
    connect(this, SIGNAL(songRequested()), this->playlist, SLOT(shiftPlaylist()));
    connect(this->playlist, SIGNAL(currentItemChanged()), this, SLOT(playNext()));
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

Playlist *Player::getPlaylist() const
{
    return this->playlist;
}

Library *Player::getLibrary() const
{
    return this->library;
}
