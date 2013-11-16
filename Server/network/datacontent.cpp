#include "datacontent.h"

DataContent::DataContent(const QByteArray &byteArray)
    : byteArray(byteArray)
{
}

QByteArray DataContent::getQByteArray() const
{
    return byteArray;
}
