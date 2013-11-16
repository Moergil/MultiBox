#include "getplayerstateresponse.h"
#include "util/bytearrayconverter.h"

#include <util/messagecontentwriter.h>

GetPlayerStateResponse::GetPlayerStateResponse( QJsonDocument multimedia,
                                                qint32 playbackPosition,
                                                bool playing,
                                                QObject *parent)
    : AbstractResponse(parent), multimedia(multimedia),
      playbackPosition(playbackPosition), playing(playing)
{
}

DataContent GetPlayerStateResponse::toDataContent()
{
    MessageContentWriter writer;
    writer.write(multimedia);
    writer.write(playbackPosition);
    writer.write(playing);

    return writer.toDataContent();
}
