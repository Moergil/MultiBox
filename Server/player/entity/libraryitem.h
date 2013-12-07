#include "libraryitemtype.h"
#include "qjsonexportableinterface.h"

#include <QFileInfo>
#include <QString>

#pragma once

class LibraryItem : public QJsonExportableInterface
{
    private:
        const qint64 id;
        const QString name;
        const LibraryItemType type;
        const QFileInfo fileInfo;
        const qint64 parentId;

    public:
        LibraryItem(const qint64 id, const QString name, const QFileInfo fileInfo, const LibraryItemType type, const qint64 parentId = 0);

        qint64 getId() const;
        QString getName() const;
        QFileInfo getFileInfo() const;
        LibraryItemType getType() const;
        qint64 getParentId() const;

        virtual QJsonObject toQJsonObject() const;
};
