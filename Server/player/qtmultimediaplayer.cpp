#include "qtmultimediaplayer.h"

void QtMultimediaPlayer::onChangeMediaStatus(QMediaPlayer::MediaStatus status)
{
    if(status == QMediaPlayer::EndOfMedia || status == QMediaPlayer::UnknownMediaStatus)
    {
        emitSongRequestedSignal();
    }
}

QtMultimediaPlayer::QtMultimediaPlayer(const QString &playerName)
    : Player(playerName)
{
    qMediaPlayer = new QMediaPlayer;
    playing = false;

    connect(qMediaPlayer, SIGNAL(mediaStatusChanged(QMediaPlayer::MediaStatus)), this, SLOT(onChangeMediaStatus(QMediaPlayer::MediaStatus)));
}

QtMultimediaPlayer::~QtMultimediaPlayer()
{
    delete qMediaPlayer;
}

void QtMultimediaPlayer::start()
{
    emitSongRequestedSignal();
}

void QtMultimediaPlayer::setPlaying(bool playing)
{
    if(playing)
    {
        qMediaPlayer->play();
    }
    else
    {
        qMediaPlayer->pause();
    }

    this->playing = playing;
}

void QtMultimediaPlayer::setVolume(int volume)
{
    this->qMediaPlayer->setVolume(volume);
}

void QtMultimediaPlayer::playNext()
{
    Multimedia *item = getPlaylist()->getCurrentItem();
    qMediaPlayer->setMedia(item->getQUrl());

    setPlaying(true);
}

bool QtMultimediaPlayer::isPlaying() const
{
    return playing;
}

int QtMultimediaPlayer::getVolume() const
{
    return qMediaPlayer->volume();
}

int QtMultimediaPlayer::getPosition() const
{
    return qMediaPlayer->position() / 1000;
}

int QtMultimediaPlayer::getDuration() const
{
    return qMediaPlayer->duration() / 1000;
}
