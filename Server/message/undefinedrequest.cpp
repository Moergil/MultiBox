#include "undefinedrequest.h"
#include <QtDebug>

UndefinedRequest::UndefinedRequest(DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

RequestRunnable *UndefinedRequest::getRunnable()
{
    return new UndefinedRequest::Runnable(this);
}

UndefinedRequest::Runnable::Runnable(AbstractRequest *request)
    : RequestRunnable(request)
{
}

void UndefinedRequest::Runnable::run()
{
    DataMessage dataMessage = getRequest()->getDataMessage();

    qDebug() << "Undefined message received: " << dataMessage.getMessageType();
}
