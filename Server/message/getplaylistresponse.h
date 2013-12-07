#include "abstractresponse.h"

#include <network/datacontent.h>
#include <player/entity/playliststate.h>

#pragma once

class GetPlaylistResponse : public AbstractResponse
{
        Q_OBJECT

    private:
        const PlaylistState playlistState;

    public:
        GetPlaylistResponse(PlaylistState playlistState, QObject *parent = 0);

        DataContent toDataContent() const;
        qint32 getMessageCode() const;
};
