#include "getplaylistrequest.h"
#include "getplaylistresponse.h"

GetPlaylistRequest::GetPlaylistRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

bool GetPlaylistRequest::canResponse() const
{
    return true;
}

void GetPlaylistRequest::execute()
{
    PlaylistState playlistState = getPlayerHandler()->getPlaylistState();
    GetPlaylistResponse * response = new GetPlaylistResponse(playlistState);

    setResponse(response);
}
