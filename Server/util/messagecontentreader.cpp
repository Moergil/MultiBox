#include "util/bytearrayconverter.h"
#include "util/messagecontentreader.h"

MessageContentReader::MessageContentReader(const DataContent &dataContent)
    : byteArray(dataContent.getQByteArray())
{
    reset();
}

qint32 MessageContentReader::readQInt32()
{
    QByteArray array = byteArray.mid(pointer, QINT32_LENGTH);
    pointer += QINT32_LENGTH;

    return ByteArrayConverter::parseQInt32(array);
}

qint64 MessageContentReader::readQInt64()
{
    QByteArray array = byteArray.mid(pointer, QINT64_LENGTH);
    pointer += QINT64_LENGTH;

    return ByteArrayConverter::parseQInt64(array);
}

QString MessageContentReader::readQString()
{
    qint32 size = readQInt32();

    QByteArray array = byteArray.mid(pointer, size);
    pointer += size;

    return ByteArrayConverter::parseQString(array);
}

QJsonDocument MessageContentReader::readQJsonDocument()
{
    qint32 size = readQInt32();

    QByteArray array = byteArray.mid(pointer, size);
    pointer += size;

    return ByteArrayConverter::parseQJsonDocument(array);
}

bool MessageContentReader::readBool()
{
    QByteArray array = byteArray.mid(pointer, BOOL_LENGTH);
    pointer += BOOL_LENGTH;

    return ByteArrayConverter::parseBool(array);
}

void MessageContentReader::reset()
{
    pointer = 0;
}

bool MessageContentReader::endOfArray()
{
    return pointer >= byteArray.length();
}
