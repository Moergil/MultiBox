#include "getserverinforesponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>
#include <QVariantMap>

GetServerInfoResponse::GetServerInfoResponse(const QString &playerName)
    : playerName(playerName)
{
}

DataContent GetServerInfoResponse::toDataContent() const
{
    QVariantMap map;
    map["serverName"] = playerName;

    MessageContentWriter writer;
    writer.write(QJsonObject::fromVariantMap(map));
    return writer.toDataContent();
}

qint32 GetServerInfoResponse::getMessageCode() const
{
    return MessageRecognizer::GetServerInfo;
}
