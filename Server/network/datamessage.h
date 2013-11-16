#include "datacontent.h"

#include <QByteArray>

#pragma once

class DataMessage
{
private:
    const qint32 messageTypeId;
    const DataContent dataContent;

public:
    DataMessage(qint32 messageTypeId, DataContent &dataContent);

    qint32 getMessageType() const;
    DataContent getDataContent() const;
};
