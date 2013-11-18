#pragma once

#include <QList>
#include <QObject>

#include "playlistitem.h"

class Playlist : public QObject
{
    Q_OBJECT

private:
    QList<PlaylistItem *> *playlistItems;
    PlaylistItem *currentItem;

signals:
    void playlistChanged();
    void currentItemChanged();

private slots:
    void autoShiftPlaylist();

private:
    void emitPlaylistChangedSignal();
    void emitCurrentItemChanged();

public slots:
    void shiftPlaylist();

public:
    Playlist();
    ~Playlist();

    void addItem(PlaylistItem *playlistItem);

    PlaylistItem getCurrentItem() const;
    QList<PlaylistItem *> getListOfItems() const;

    void clear();

    bool isEmpty() const;
    int count() const;
};

