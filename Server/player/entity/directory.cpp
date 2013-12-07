#include "directory.h"

#include <QVariantMap>
#include <QJsonArray>

Directory::Directory(const qint64 id, const QString name, const QFileInfo file, QList<LibraryItem *> items, const qint64 parentId)
    : LibraryItem(id, name, file, LibraryItemType::DIRECTORY, parentId), items(items)
{
}

QList<LibraryItem *> Directory::getItems()
{
    return items;
}

QJsonObject Directory::toQJsonObject() const
{
    QJsonObject object = LibraryItem::toQJsonObject();

    QJsonArray array;

    foreach(LibraryItem *item, items)
    {
        array.append(item->LibraryItem::toQJsonObject());
    }

    object.insert("items", array);

    return object;
}
