#include <QByteArray>

#pragma once

class DataMessage
{
private:
    const qint32 messageType;
    const QByteArray byteArray;

public:
    DataMessage(qint32 messageType, QByteArray &byteArray);

    qint32 getMessageType() const;
    QByteArray getByteArray() const;
};
