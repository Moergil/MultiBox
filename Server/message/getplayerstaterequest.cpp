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
    //Multimedia currentMultimedia = getPlayerHandler()->getCurrentMultimedia();
    /*GetPlayerStateResponse * response = new GetPlayerStateResponse(currentMultimedia, 0, false);

    setResponse(response);*/
}
