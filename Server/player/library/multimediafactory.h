#include "libraryitemfactory.h"

#pragma once

class MultimediaFactory : public LibraryItemFactory
{
    private:
        static MultimediaFactory *multimediaFactory;

    public:
        static MultimediaFactory *getFactory();

    private:
        MultimediaFactory();

    public:
        LibraryItem *getLibraryItem(LibraryTableRow *row) const;
        LibraryItem *getLibraryItem(const QFileInfo path) const;
};
