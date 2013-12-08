#include "librarytablerow.h"

#include <player/entity/libraryitem.h>

#pragma once

class LibraryItemFactory
{
    public:
        virtual LibraryItem *getLibraryItem(LibraryTableRow *row) const = 0;
        virtual LibraryItem *getLibraryItem(const QFileInfo path) const = 0;
};
