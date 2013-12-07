#include "getplayerstaterequest.h"
#include "getplayerstateresponse.h"
#include <QtDebug>

GetPlayerStateRequest::GetPlayerStateRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

bool GetPlayerStateRequest::canResponse() const
{
    return true;
}

void GetPlayerStateRequest::execute()
{
    Multimedia currentMultimedia = getPlayerHandler()->getCurrentMultimedia();
    qint32 position = getPlayerHandler()->getPosition();
    bool playing = getPlayerHandler()->isPlaying();

    GetPlayerStateResponse * response = new GetPlayerStateResponse(currentMultimedia, position, playing);

    setResponse(response);
}
