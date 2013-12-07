#include "playlist.h"

#include <QJsonArray>

void Playlist::shiftPlaylist()
{
    if(!waitingItems->isEmpty())
    {
        currentItem = waitingItems->takeFirst();
        emitCurrentItemChanged();
        emitWaitingListChangedSignal();
    }
    else
    {
        currentItem = NULL;
    }
}

void Playlist::autoShiftPlaylist()
{
    if(!waitingItems->isEmpty() && currentItem == NULL)
    {
        shiftPlaylist();
    }
}

void Playlist::emitWaitingListChangedSignal()
{
    emit waitingListChanged();
}

void Playlist::emitCurrentItemChanged()
{
    emit currentItemChanged();
}

Playlist::Playlist(QObject *parent)
    : QObject(parent)
{
    waitingItems = new QList<Multimedia *>();
    currentItem = NULL;

    connect(this, SIGNAL(waitingListChanged()), this, SLOT(autoShiftPlaylist()));
}

Playlist::~Playlist()
{
    delete waitingItems;
}

void Playlist::addItem(Multimedia *playlistItem)
{
    //pridanie id v playliste

    waitingItems->append(playlistItem);

    emitWaitingListChangedSignal();
}

Multimedia *Playlist::getCurrentItem() const
{
    return currentItem;
}

/*void Playlist::addItem(qint64 multimediaId)
{

}*/

QList<Multimedia *> Playlist::getListOfItems() const
{
    QList<Multimedia *> list = QList<Multimedia *>(*this->waitingItems);

    list.push_front(currentItem);

    return list;
}

PlaylistState Playlist::getPlaylistState() const
{
    return PlaylistState(getListOfItems());
}

void Playlist::clear()
{
    waitingItems->clear();

    emitWaitingListChangedSignal();
}

bool Playlist::isEmpty() const
{
    return count() == 0;
}

int Playlist::count() const
{
    return currentItem == NULL ? 0 : waitingItems->count() + 1;
}
