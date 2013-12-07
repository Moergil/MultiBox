#include "datamessage.h"

DataMessage::DataMessage(const qint32 messageTypeId, const DataContent &dataContent)
    : messageTypeId(messageTypeId), dataContent(dataContent)
{
}

qint32 DataMessage::getMessageType() const
{
    return messageTypeId;
}

DataContent DataMessage::getDataContent() const
{
    return dataContent;
}
