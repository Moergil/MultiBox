#include "datamessage.h"

DataMessage::DataMessage(qint32 messageType, DataContent &dataContent)
    : messageTypeId(messageType), dataContent(dataContent)
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
