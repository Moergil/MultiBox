#include "getplaylistresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetPlaylistResponse::GetPlaylistResponse(PlaylistState playlistState, QObject *parent)
    : AbstractResponse(parent), playlistState(playlistState)
{
}

DataContent GetPlaylistResponse::toDataContent() const
{
    MessageContentWriter writer;
    writer.write(playlistState.toQJsonObject());

    return writer.toDataContent();
}

qint32 GetPlaylistResponse::getMessageCode() const
{
    return MessageRecognizer::GetPlaylist;
}
