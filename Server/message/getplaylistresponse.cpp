#include "getplaylistresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetPlaylistResponse::GetPlaylistResponse(PlaylistState playlistState,
                                                QObject *parent)
    : AbstractResponse(parent), playlistState(playlistState)
{
}

DataContent GetPlaylistResponse::toDataContent()
{
    MessageContentWriter writer;
    writer.write(playlistState.toQJsonObject());

    return writer.toDataContent();
}

qint32 GetPlaylistResponse::getMessageCode()
{
    return MessageRecognizer::GetPlaylist;
}
