#pragma once

#include <QMediaPlayer>

#include "player.h"

class QtMultimediaPlayer : public Player
{
    Q_OBJECT

private:
    QMediaPlayer *qMediaPlayer;

public slots:
    void onChangeMediaStatus(QMediaPlayer::MediaStatus status);

public:
    QtMultimediaPlayer();
    ~QtMultimediaPlayer();

public slots:
    // Player interface
    void start();
    void pause();
    void resume();
    void setVolume(int volume);
    void playNext();
};
