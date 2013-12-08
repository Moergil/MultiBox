#include "messagecontentwriter.h"
#include "bytearrayconverter.h"

MessageContentWriter::MessageContentWriter()
{
    reset();
}

void MessageContentWriter::write(qint32 number)
{
    byteArray.append(ByteArrayConverter::fromQInt32(number));
}

void MessageContentWriter::write(qint64 number)
{
    byteArray.append(ByteArrayConverter::fromQInt64(number));
}

void MessageContentWriter::write(QString qString)
{
    QByteArray rawArray = ByteArrayConverter::fromQString(qString);
    byteArray.append(ByteArrayConverter::fromQInt32(rawArray.length()));
    byteArray.append(rawArray);
}

void MessageContentWriter::write(QJsonObject qJsonObject)
{
    QByteArray rawArray = ByteArrayConverter::fromQJsonObject(qJsonObject);
    byteArray.append(ByteArrayConverter::fromQInt32(rawArray.length()));
    byteArray.append(rawArray);
}

void MessageContentWriter::write(bool boolean)
{
    byteArray.append(ByteArrayConverter::fromBool(boolean));
}

void MessageContentWriter::reset()
{
    byteArray.clear();
}

DataContent MessageContentWriter::toDataContent()
{
    return DataContent(byteArray);
}
