#include "abstractresponse.h"

#include <network/datacontent.h>

#include <player/entity/multimedia.h>

#pragma once

class GetPlayerStateResponse : public AbstractResponse
{
private:
    const Multimedia multimedia;
    const qint32 playbackPosition;
    const bool playing;

public:
    GetPlayerStateResponse(Multimedia multimedia,
                           qint32 playbackPosition,
                           bool playing,
                           QObject *parent = 0);
    DataContent toDataContent();
    qint32 getMessageCode();
};
