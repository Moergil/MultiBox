#include "abstractresponse.h"

AbstractResponse::AbstractResponse(QObject *parent) :
    QObject(parent)
{
}

DataMessage AbstractResponse::toDataMessage()
{
    return DataMessage(getMessageCode(), toDataContent());
}
