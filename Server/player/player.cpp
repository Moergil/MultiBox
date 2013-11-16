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
    this->playlist = new Playlist();

    this->setConnections();
}

Player::~Player()
{
    delete this->playlist;
}

void Player::skipSong()
{
    emitSongRequestedSignal();
}

Playlist *Player::getPlaylist() const
{
    return this->playlist;
}
