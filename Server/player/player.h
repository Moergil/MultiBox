#pragma once

#include "library.h"
#include "playlist.h"

#include <QObject>

class Player : public QObject
{
        Q_OBJECT

    private:
        Playlist *playlist;
        Library *library;
        const QString playerName;

    signals:
        void songRequested();

    private:
        void setConnections();

    private slots:
        virtual void start() = 0;

    protected:
        void emitSongRequestedSignal();

    public slots:
        virtual void setPlaying(bool playing) = 0;
        virtual void setVolume(int volume) = 0;
        virtual void playNext() = 0;
        virtual bool isPlaying() const = 0;
        virtual int getVolume() const = 0;
        virtual int getPosition() const = 0;
        virtual int getDuration() const = 0;

        void skipSong();
        QString getPlayerName() const;

    public:
        Player(const QString &playerName);
        ~Player();

        Playlist *getPlaylist() const;
        Library *getLibrary() const;
};
