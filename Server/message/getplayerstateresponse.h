#include "abstractresponse.h"

#include <QJsonDocument>

#include <network/datacontent.h>

#pragma once

class GetPlayerStateResponse : public AbstractResponse
{
private:
    const QJsonDocument multimedia;
    const qint32 playbackPosition;
    const bool playing;

public:
    GetPlayerStateResponse(QJsonDocument multimedia,
                           qint32 playbackPosition,
                           bool playing,
                           QObject *parent = 0);
    DataContent toDataContent();
};
