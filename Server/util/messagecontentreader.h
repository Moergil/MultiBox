#pragma once

#include <QByteArray>
#include <QJsonDocument>

#include <network/datacontent.h>

class MessageContentReader
{
private:
    static const qint32 BOOL_LENGTH     = 1;
    static const qint32 QINT32_LENGTH   = 4;
    static const qint32 QINT64_LENGTH   = 8;

    qint32 pointer;
    const QByteArray byteArray;

public:
    MessageContentReader(const DataContent &dataContent);
    qint32 readQInt32();
    qint64 readQInt64();
    QString readQString();
    QJsonObject readQJsonObject();
    bool readBool();
    void reset();
    bool endOfArray();
};
