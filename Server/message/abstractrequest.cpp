#include "abstractrequest.h"

AbstractRequest::AbstractRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : QObject(parent), dataMessage(dataMessage), handler(handler)
{
}

DataMessage &AbstractRequest::getDataMessage()
{
    return dataMessage;
}

PlayerHandler *AbstractRequest::getPlayerHandler()
{
    return handler;
}
