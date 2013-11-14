#include "qtmultimediaplayer.h"

void QtMultimediaPlayer::onChangeMediaStatus(QMediaPlayer::MediaStatus status)
{
    if(status == QMediaPlayer::EndOfMedia)
    {
        emitSongRequestedSignal();
    }
}

QtMultimediaPlayer::QtMultimediaPlayer() : Player()
{
    this->qMediaPlayer = new QMediaPlayer;

    connect(this->qMediaPlayer, SIGNAL(mediaStatusChanged(QMediaPlayer::MediaStatus)), this, SLOT(onChangeMediaStatus(QMediaPlayer::MediaStatus)));
}

QtMultimediaPlayer::~QtMultimediaPlayer()
{
    delete this->qMediaPlayer;
}

void QtMultimediaPlayer::start()
{
    emitSongRequestedSignal();
}

void QtMultimediaPlayer::resume()
{
    this->qMediaPlayer->play();
}

void QtMultimediaPlayer::pause()
{
    this->qMediaPlayer->pause();
}

void QtMultimediaPlayer::setVolume(int volume)
{
    this->qMediaPlayer->setVolume(volume);
}

void QtMultimediaPlayer::playNext()
{
    PlaylistItem item = getPlaylist()->getCurrentItem();
    this->qMediaPlayer->setMedia(item.getItemUrl());

    this->qMediaPlayer->play();
}
