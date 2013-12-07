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

void Library::addDirectory(QDir &dir)
{
    DirectoryFactory *factory = DirectoryFactory::getFactory();
    WritableDirectory *directory = (WritableDirectory *) factory->getLibraryItem(QFileInfo(dir.absolutePath()));
    dbManager->write(*directory);
}

QList<LibraryItem *> *Library::listDirectories()
{
    return dbManager->readByParentId(0);
}

LibraryItem *Library::readRootDir()
{
    return new WritableDirectory(0, "Libraries", QFileInfo(), *listDirectories());
}

void Library::removeDirectory(QDir &dir)
{
    DirectoryFactory *factory = DirectoryFactory::getFactory();
    LibraryItem *directory = factory->getLibraryItem(QFileInfo(dir.absolutePath()));
    dbManager->remove(directory);
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
