#pragma once

#include "abstractresponse.h"

#include <player/entity/multimedia.h>

class AddLibraryItemToPlaylistResponse : public AbstractResponse
{
        Q_OBJECT

    private:
        const bool result;
        const Multimedia *multimedia;

    public:
        AddLibraryItemToPlaylistResponse(const bool result, const Multimedia *multimedia);

        DataContent toDataContent() const;
        qint32 getMessageCode() const;
};
