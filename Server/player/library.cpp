#include "library.h"

#include <QFileInfo>

#include <player/library/directoryfactory.h>
#include <player/library/writabledirectory.h>

Library::Library(QObject *parent)
    : QObject(parent)
{
    dbManager = new LibraryDbManager(parent);
    dbManager->open();
}

Library::~Library()
{
    dbManager->close();
}

LibraryItem *Library::readRootDir()
{
    return new WritableDirectory(0, "Library", QFileInfo(), *dbManager->readByParentId(0));
}

LibraryItem *Library::readById(qint64 itemId)
{
    if(itemId == 0)
    {
        return readRootDir();
    }
    else
    {
        return dbManager->readById(itemId);
    }
}

QList<LibraryItem *> *Library::readAll()
{
    return dbManager->readAll();
}

bool Library::write(LibraryWritableInterface &item)
{
    if(!exists(item))
    {
        dbManager->write(item);
        return true;
    }
    else
    {
        return false;
    }
}

bool Library::exists(LibraryWritableInterface &item)
{
    return dbManager->exists(item);
}

void Library::remove(LibraryItem *item)
{
    return dbManager->remove(item);
}
