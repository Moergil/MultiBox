#include "directoryfactory.h"
#include "libraryitemfactorychooser.h"
#include "writabledirectory.h"

DirectoryFactory *DirectoryFactory::directoryFactory = new DirectoryFactory();

DirectoryFactory *DirectoryFactory::getFactory()
{
    return directoryFactory;
}

DirectoryFactory::DirectoryFactory()
{
}

LibraryItem *DirectoryFactory::getLibraryItem(LibraryTableRow *row) const
{
    QList<LibraryItem *> descendants;

    foreach(LibraryTableRow *son, row->getDescendants())
    {
        LibraryItemFactory *factory = LibraryItemFactoryChooser::getFactory(son->getType());
        descendants.append(factory->getLibraryItem(son));
    }

    return new WritableDirectory(row->getId(), row->getName(), QFileInfo(row->getPath()), descendants, row->getParent());
}

LibraryItem *DirectoryFactory::getLibraryItem(const QFileInfo path) const
{
    return new WritableDirectory(0, path.baseName(), path);
}
