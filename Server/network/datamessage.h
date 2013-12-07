#include "datacontent.h"

#include <QByteArray>

#pragma once

class DataMessage
{
private:
    const qint32 messageTypeId;
    const DataContent dataContent;

public:
    DataMessage(const qint32 messageTypeId, const DataContent &dataContent = DataContent::emptyDataContent());

    qint32 getMessageType() const;
    DataContent getDataContent() const;
};
