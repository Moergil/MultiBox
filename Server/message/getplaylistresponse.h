#include "abstractresponse.h"

#include <network/datacontent.h>

#include <player/entity/playliststate.h>

#pragma once

class GetPlayerStateResponse : public AbstractResponse
{
private:
    const PlaylistState playlistState;
    const qint32 playbackPosition;
    const bool playing;

public:
    GetPlayerStateResponse(PlaylistState playlistState,
                           qint32 playbackPosition,
                           bool playing,
                           QObject *parent = 0);
    DataContent toDataContent();
    qint32 getMessageCode();
};
