#include "libraryitem.h"

#include <QVariantMap>

LibraryItem::LibraryItem(const qint64 id, const QString name, const QFileInfo fileInfo, const LibraryItemType type, const qint64 parentId)
    : id(id), name(name), type(type), fileInfo(fileInfo), parentId(parentId)
{
}

qint64 LibraryItem::getId() const
{
    return id;
}

QString LibraryItem::getName() const
{
    return name;
}

QFileInfo LibraryItem::getFileInfo() const
{
    return fileInfo;
}

LibraryItemType LibraryItem::getType() const
{
    return type;
}

qint64 LibraryItem::getParentId() const
{
    return parentId;
}

QJsonObject LibraryItem::toQJsonObject() const
{
    QVariantMap variantMap;
    variantMap["id"] = getId();
    variantMap["name"] = getName();
    variantMap["type"] = getType().getTextRepresentation();

    return QJsonObject::fromVariantMap(variantMap);
}
