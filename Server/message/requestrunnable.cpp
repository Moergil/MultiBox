#include "requestrunnable.h"
#include "abstractrequest.h"

RequestRunnable::RequestRunnable(AbstractRequest *request)
    : request(request)
{
}

PlayerHandler *RequestRunnable::getPlayerHandler() const
{
    return request->getPlayerHandler();
}

AbstractRequest *RequestRunnable::getRequest() const
{
    return request;
}
