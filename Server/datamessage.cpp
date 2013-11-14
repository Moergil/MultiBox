#include "datamessage.h"

DataMessage::DataMessage(qint32 messageType, QByteArray &byteArray)
    : messageType(messageType), byteArray(byteArray)
{
}

qint32 DataMessage::getMessageType() const
{
    return messageType;
}

QByteArray DataMessage::getByteArray() const
{
    return byteArray;
}
