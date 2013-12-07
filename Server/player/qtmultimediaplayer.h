#pragma once

#include <QMediaPlayer>

#include "player.h"

class QtMultimediaPlayer : public Player
{
        Q_OBJECT

    private:
        QMediaPlayer *qMediaPlayer;
        bool playing;

    public slots:
        void onChangeMediaStatus(QMediaPlayer::MediaStatus status);

    public:
        QtMultimediaPlayer(const QString &playerName);
        ~QtMultimediaPlayer();

    public slots:
        // Player interface
        void start();
        void setPlaying(bool playing);
        void setVolume(int volume);
        void playNext();
        bool isPlaying() const;
        int getVolume() const;
        int getPosition() const;
        int getDuration() const;
};
