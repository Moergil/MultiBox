#include "librarytablerow.h"


QList<LibraryTableRow *> LibraryTableRow::getDescendants() const
{
    return descendants;
}

void LibraryTableRow::setDescendants(const QList<LibraryTableRow *> &value)
{
    descendants = value;
}

LibraryTableRow::LibraryTableRow()
{
}

void LibraryTableRow::setId(qint64 id)
{
    this->id = id;
}

void LibraryTableRow::setName(QString name)
{
    this->name = name;
}

void LibraryTableRow::setType(qint32 type)
{
    this->type = type;
}

void LibraryTableRow::setLength(qint32 length)
{
    this->length = length;
}

void LibraryTableRow::setPath(QString path)
{
    this->path = path;
}

void LibraryTableRow::setParent(qint64 parent)
{
    this->parent = parent;
}

qint64 LibraryTableRow::getId() const
{
    return id;
}

QString LibraryTableRow::getName() const
{
    return name;
}

qint32 LibraryTableRow::getType() const
{
    return type;
}

qint32 LibraryTableRow::getLength() const
{
    return length;
}

QString LibraryTableRow::getPath() const
{
    return path;
}

qint64 LibraryTableRow::getParent() const
{
    return parent;
}
