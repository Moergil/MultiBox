#include "qtmultimediaplayer.h"

void QtMultimediaPlayer::onChangeMediaStatus(QMediaPlayer::MediaStatus status)
{
    if(status == QMediaPlayer::EndOfMedia || status == QMediaPlayer::UnknownMediaStatus)
    {
        emitSongRequestedSignal();
    }
}

QtMultimediaPlayer::QtMultimediaPlayer() : Player()
{
    this->qMediaPlayer = new QMediaPlayer;
    this->playing = false;

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

void QtMultimediaPlayer::setPlaying(bool playing)
{
    if(playing)
    {
        this->qMediaPlayer->play();
    }
    else
    {
        this->qMediaPlayer->pause();
    }

    this->playing = playing;
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

bool QtMultimediaPlayer::isPlaying() const
{
    return playing;
}

int QtMultimediaPlayer::getVolume() const
{
    return this->qMediaPlayer->volume();
}
