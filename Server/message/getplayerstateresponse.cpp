#include "getplayerstateresponse.h"
#include "messagerecognizer.h"
#include <util/messagecontentwriter.h>

GetPlayerStateResponse::GetPlayerStateResponse(Multimedia multimedia,
                                                qint32 playbackPosition,
                                                bool playing, qint32 duration,
                                                QObject *parent)
    : AbstractResponse(parent), multimedia(multimedia),
      playbackPosition(playbackPosition), playing(playing), duration(duration)
{
}

DataContent GetPlayerStateResponse::toDataContent() const
{
    QJsonObject object;
    object.insert("playbackPosition", playbackPosition);
    object.insert("playing", playing);

    QJsonObject multimediaObject = multimedia.toQJsonObject();
    multimediaObject.insert("length", duration);

    object.insert("multimedia", multimediaObject);

    MessageContentWriter writer;
    writer.write(object);
    return writer.toDataContent();
}

qint32 GetPlayerStateResponse::getMessageCode() const
{
    return MessageRecognizer::GetPlayerState;
}
