#include "abstractrequest.h"

AbstractRequest::AbstractRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : QObject(parent), dataMessage(dataMessage), handler(handler)
{
}

DataMessage &AbstractRequest::getDataMessage()
{
    return dataMessage;
}

PlayerHandler *AbstractRequest::getPlayerHandler() const
{
    return handler;
}

AbstractResponse *AbstractRequest::getResponse() const
{
    return response;
}

void AbstractRequest::setResponse(AbstractResponse *response)
{
    this->response = response;
}
