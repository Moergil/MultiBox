#include "getserverinforesponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetServerInfoResponse::GetServerInfoResponse(const QString &playerName)
    : playerName(playerName)
{
}

DataContent GetServerInfoResponse::toDataContent() const
{
    MessageContentWriter writer;
    writer.write(playerName);
    return writer.toDataContent();
}

qint32 GetServerInfoResponse::getMessageCode() const
{
    return MessageRecognizer::GetServerInfo;
}
