#include "getplayerstateresponse.h"
#include "messagerecognizer.h"
#include <QVariantMap>
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
    QVariantMap map;

    map["playbackPosition"] = playbackPosition;
    map["playing"] = playing;

    QJsonObject object = QJsonObject::fromVariantMap(map);
    object.insert("multimedia", multimedia.toQJsonObject());

    MessageContentWriter writer;
    writer.write(object);
    return writer.toDataContent();
}

qint32 GetPlayerStateResponse::getMessageCode() const
{
    return MessageRecognizer::GetPlayerState;
}
