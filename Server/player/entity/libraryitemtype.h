#include <QString>

#pragma once

class LibraryItemType
{
    public:
        static const LibraryItemType DIRECTORY;
        static const LibraryItemType MULTIMEDIA;

    private:
        const qint32 id;
        const QString textRepresentation;

        LibraryItemType(const qint32 id, const QString textRepresentation);

    public:
        qint32 getId() const;
        QString getTextRepresentation() const;
};

