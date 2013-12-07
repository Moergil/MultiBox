#include "directoryfactory.h"
#include "libraryitemfactorychooser.h"
#include "multimediafactory.h"

#include <player/entity/libraryitemtype.h>

LibraryItemFactory *LibraryItemFactoryChooser::getFactory(qint32 type)
{
    if(type == LibraryItemType::DIRECTORY.getId())
    {
            return DirectoryFactory::getFactory();
    }
    else if(type == LibraryItemType::MULTIMEDIA.getId())
    {
            return MultimediaFactory::getFactory();
    }
    else
    {
        return NULL;
    }
}
