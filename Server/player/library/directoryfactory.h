#include "libraryitemfactory.h"

#pragma once

class DirectoryFactory : public LibraryItemFactory
{
    private:
        static DirectoryFactory *directoryFactory;

    public:
        static DirectoryFactory *getFactory();

    private:
        DirectoryFactory();

    public:
        LibraryItem *getLibraryItem(LibraryTableRow *row) const;
        LibraryItem *getLibraryItem(const QFileInfo path) const;
};
