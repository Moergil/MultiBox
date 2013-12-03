#include "getplayerstateresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetPlayerStateResponse::GetPlayerStateResponse(PlaylistState playlistState,
                                                qint32 playbackPosition,
                                                bool playing,
                                                QObject *parent)
    : AbstractResponse(parent), playlistState(playlistState),
      playbackPosition(playbackPosition), playing(playing)
{
}

DataContent GetPlayerStateResponse::toDataContent()
{
    MessageContentWriter writer;
    writer.write(playlistState.toQJsonObject());
    writer.write(playbackPosition);
    writer.write(playing);

    return writer.toDataContent();
}

qint32 GetPlayerStateResponse::getMessageCode()
{
    return MessageRecognizer::GetPlayerState;
}
