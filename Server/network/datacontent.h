#include <QByteArray>

#pragma once

class DataContent
{
private:
    const QByteArray byteArray;
public:
    DataContent(const QByteArray &byteArray);
    QByteArray getQByteArray() const;
};
