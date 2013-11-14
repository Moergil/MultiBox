#pragma once

#include "playlist.h"

#include <QObject>

class Player : public QObject
{
    Q_OBJECT

private:
    Playlist *playlist;

signals:
    void songRequested();

private:
    void setConnections();

protected:
    void emitSongRequestedSignal();

public slots:
    virtual void start() = 0;
    virtual void pause() = 0;
    virtual void resume() = 0;
    virtual void setVolume(int volume) = 0;

    virtual void playNext() = 0;

    void skipSong();

public:
    Player();
    ~Player();

    Playlist *getPlaylist() const;
};
