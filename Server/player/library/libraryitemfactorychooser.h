#include "libraryitemfactory.h"

#pragma once

class LibraryItemFactoryChooser
{
    public:
        static LibraryItemFactory *getFactory(qint32 type);
};
