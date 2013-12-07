#include "abstractresponse.h"

#include <network/datacontent.h>

#include <player/entity/playliststate.h>

#pragma once

class GetPlaylistResponse : public AbstractResponse
{
private:
    const PlaylistState playlistState;

public:
    GetPlaylistResponse(PlaylistState playlistState,
                           QObject *parent = 0);

    DataContent toDataContent();
    qint32 getMessageCode();
};
