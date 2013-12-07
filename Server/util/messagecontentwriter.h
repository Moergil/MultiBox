#include <QByteArray>
#include <QJsonObject>

#include <network/datacontent.h>

#pragma once

class MessageContentWriter
{
    private:
        QByteArray byteArray;

    public:
        MessageContentWriter();
        void write(qint32 number);
        void write(qint64 number);
        void write(QString qString);
        void write(QJsonObject qJsonObject);
        void write(bool boolean);
        void reset();
        DataContent toDataContent();
};
