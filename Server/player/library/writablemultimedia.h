#include "librarywritableinterface.h"

#include <player/entity/multimedia.h>

#pragma once

class WritableMultimedia : public Multimedia, public LibraryWritableInterface
{
    public:
        WritableMultimedia(const qint64 id, const QString name, const qint32 length, const QFileInfo file, const qint64 parentId = 0);

        QString getIdentifier() const;
        QString getInsertString() const;
};
