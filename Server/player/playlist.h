#pragma once

#include <QList>
#include <QObject>

#include <player/entity/playliststate.h>
#include <player/entity/multimedia.h>

class Playlist : public QObject
{
    Q_OBJECT

private:
    QList<Multimedia *> *waitingItems;
    Multimedia *currentItem;

signals:
    void waitingListChanged();
    void currentItemChanged();

private slots:
    void autoShiftPlaylist();

private:
    void emitWaitingListChangedSignal();
    void emitCurrentItemChanged();

public slots:
    void shiftPlaylist();

    PlaylistState getPlaylistState() const;
    Multimedia *getCurrentItem() const;

    //void addItem(qint64 multimediaId);

public:
    Playlist(QObject *parent = 0);
    ~Playlist();

    void addItem(Multimedia *playlistItem);

    QList<Multimedia *> getListOfItems() const;

    void clear();

    bool isEmpty() const;
    int count() const;
};

