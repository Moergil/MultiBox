#include "playlist.h"

void Playlist::shiftPlaylist()
{
    if(!playlistItems->isEmpty())
    {
        currentItem = playlistItems->takeFirst();
        emitCurrentItemChanged();
        emitPlaylistChangedSignal();
    }
    else
    {
        currentItem = NULL;
    }
}

void Playlist::autoShiftPlaylist()
{
    if(!playlistItems->isEmpty() && currentItem == NULL)
    {
        shiftPlaylist();
    }
}

void Playlist::emitPlaylistChangedSignal()
{
    emit playlistChanged();
}

void Playlist::emitCurrentItemChanged()
{
    emit currentItemChanged();
}

Playlist::Playlist()
{
    this->playlistItems = new QList<PlaylistItem *>();
    this->currentItem = NULL;

    connect(this, SIGNAL(playlistChanged()), this, SLOT(autoShiftPlaylist()));
}

Playlist::~Playlist()
{
    delete this->playlistItems;
    delete this->currentItem;
}

void Playlist::addItem(PlaylistItem *playlistItem)
{
    this->playlistItems->append(playlistItem);

    emitPlaylistChangedSignal();
}

PlaylistItem Playlist::getCurrentItem() const
{
    return *this->currentItem;
}

QList<PlaylistItem *> Playlist::getListOfItems() const
{
    return QList<PlaylistItem *>(*this->playlistItems);
}

void Playlist::clear()
{
    playlistItems->clear();

    emitPlaylistChangedSignal();
}

bool Playlist::isEmpty() const
{
    return playlistItems->isEmpty();
}

int Playlist::count() const
{
    return playlistItems->count();
}
