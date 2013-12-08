#include "abstractresponse.h"

#include <player/entity/libraryitem.h>

#pragma once

class GetLibraryItemResponse : public AbstractResponse
{
        Q_OBJECT

    private:
        const LibraryItem *item;

    public:
        GetLibraryItemResponse(const LibraryItem *item);

        DataContent toDataContent() const;
        qint32 getMessageCode() const;
};
