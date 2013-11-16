#include "playlistitem.h"

PlaylistItem::PlaylistItem(QUrl itemUrl)
{
    this->itemUrl = itemUrl;
}

QUrl PlaylistItem::getItemUrl()
{
    return this->itemUrl;
}
