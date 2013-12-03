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
    PlaylistState playlistState = getPlayerHandler()->getPlaylistState();

    GetPlayerStateResponse * response = new GetPlayerStateResponse(playlistState, 0, false);

    setResponse(response);
}
