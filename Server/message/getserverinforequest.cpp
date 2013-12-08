#include "getserverinforequest.h"
#include "getserverinforesponse.h"

GetServerInfoRequest::GetServerInfoRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

bool GetServerInfoRequest::canResponse() const
{
    return true;
}

void GetServerInfoRequest::execute()
{
    QString name = getPlayerHandler()->getPlayerName();
    setResponse(new GetServerInfoResponse(name));
}
