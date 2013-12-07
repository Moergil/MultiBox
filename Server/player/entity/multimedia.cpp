#include "multimedia.h"

#include <QVariantMap>

Multimedia Multimedia::createEmpty()
{
    return Multimedia(0, "", 0, QFileInfo());
}

Multimedia::Multimedia(const qint64 id, const QString name, const qint32 length, const QFileInfo file, const qint64 parentId)
    : LibraryItem(id, name, file, LibraryItemType::MULTIMEDIA, parentId), length(length)
{
}

qint32 Multimedia::getLength() const
{
    return length;
}

QUrl Multimedia::getQUrl() const
{
    return QUrl::fromLocalFile(getFileInfo().absoluteFilePath());
}

QJsonObject Multimedia::toQJsonObject() const
{
    QJsonObject object = LibraryItem::toQJsonObject();

    object.insert("length", getLength());

    return object;
}
