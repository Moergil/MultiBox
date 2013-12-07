#include "datacontent.h"

DataContent DataContent::emptyDataContent()
{
    return DataContent(QByteArray());
}

DataContent::DataContent(const QByteArray &byteArray)
    : byteArray(byteArray)
{
}

QByteArray DataContent::getQByteArray() const
{
    return byteArray;
}
