#include "playliststate.h"

#include <QJsonArray>

PlaylistState::PlaylistState(QList<Multimedia *> items)
    : items(items)
{
}

QList<Multimedia *> PlaylistState::getItems() const
{
    return items;
}

QJsonObject PlaylistState::toQJsonObject() const
{
    QJsonArray array;

    foreach(Multimedia *item, getItems())
    {
        array.append(item->toQJsonObject());
    }

    QJsonObject object;
    object.insert("playlist", array);

    return object;
}
