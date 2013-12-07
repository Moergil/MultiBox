#include <QByteArray>

#pragma once

class DataContent
{
    public:
        static DataContent emptyDataContent();

    private:
        const QByteArray byteArray;
    public:
        DataContent(const QByteArray &byteArray);
        QByteArray getQByteArray() const;
};
