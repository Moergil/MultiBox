#include "getplayerstateresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetPlayerStateResponse::GetPlayerStateResponse(Multimedia multimedia,
                                                qint32 playbackPosition,
                                                bool playing,
                                                QObject *parent)
    : AbstractResponse(parent), multimedia(multimedia),
      playbackPosition(playbackPosition), playing(playing)
{
}

DataContent GetPlayerStateResponse::toDataContent() const
{
    MessageContentWriter writer;
    writer.write(multimedia.toQJsonObject());
    writer.write(playbackPosition);
    writer.write(playing);

    return writer.toDataContent();
}

qint32 GetPlayerStateResponse::getMessageCode() const
{
    return MessageRecognizer::GetPlayerState;
}
