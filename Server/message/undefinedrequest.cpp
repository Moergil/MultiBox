#include "undefinedrequest.h"
#include <QtDebug>

UndefinedRequest::UndefinedRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

bool UndefinedRequest::canResponse() const
{
    return false;
}

void UndefinedRequest::execute()
{
    qDebug() << "Undefined message received: " << getDataMessage().getMessageType();
}
