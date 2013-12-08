#include "multimediafactory.h"
#include "writablemultimedia.h"

MultimediaFactory *MultimediaFactory::multimediaFactory = new MultimediaFactory();

MultimediaFactory *MultimediaFactory::getFactory()
{
    return multimediaFactory;
}

MultimediaFactory::MultimediaFactory()
{
}

LibraryItem *MultimediaFactory::getLibraryItem(LibraryTableRow *row) const
{
    return new WritableMultimedia(row->getId(), row->getName(), row->getLength(), QFileInfo(row->getPath()), row->getParent());
}

LibraryItem *MultimediaFactory::getLibraryItem(const QFileInfo path) const
{
    //TODO: duration
    return new WritableMultimedia(0, path.completeBaseName(), 0, path);
}

