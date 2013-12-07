#include "libraryitem.h"
#include <QList>

#pragma once

class Directory : public LibraryItem
{
    private:
        QList<LibraryItem *> items;

    public:
        Directory(const qint64 id, const QString name, const QFileInfo file, QList<LibraryItem *> items, const qint64 parentId = 0);

        QList<LibraryItem *> getItems();

        QJsonObject toQJsonObject() const;
};
