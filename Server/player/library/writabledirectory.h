#include "librarywritableinterface.h"

#include <player/entity/directory.h>

#pragma once

class WritableDirectory : public Directory, public LibraryWritableInterface
{
    public:
        WritableDirectory(const qint64 id, const QString name, const QFileInfo file, QList<LibraryItem *> items = QList<LibraryItem *>(), qint64 parentId = 0);

        QString getIdentifier() const;
        QString getInsertString() const;
};
