#include "libraryitemtype.h"

const LibraryItemType LibraryItemType::DIRECTORY = LibraryItemType(1, "DIRECTORY");
const LibraryItemType LibraryItemType::MULTIMEDIA = LibraryItemType(2, "MULTIMEDIA");

LibraryItemType::LibraryItemType(const qint32 id, const QString textRepresentation)
    : id(id), textRepresentation(textRepresentation)
{
}

qint32 LibraryItemType::getId() const
{
    return id;
}

QString LibraryItemType::getTextRepresentation() const
{
    return textRepresentation;
}
