#include "abstractresponse.h"

#include <network/datacontent.h>
#include <player/entity/multimedia.h>

#pragma once

class GetPlayerStateResponse : public AbstractResponse
{
        Q_OBJECT

    private:
        const Multimedia multimedia;
        const qint32 playbackPosition;
        const bool playing;
        const qint32 duration;

    public:
        GetPlayerStateResponse(Multimedia multimedia,
                               qint32 playbackPosition,
                               bool playing,
                               qint32 duration,
                               QObject *parent = 0);

        DataContent toDataContent() const;
        qint32 getMessageCode() const;
};
