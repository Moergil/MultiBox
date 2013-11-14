#include "bytearrayparser.h"

#include <QDataStream>

qint32 ByteArrayParser::parseQInt32(const QByteArray byteArray)
{
    qint32 number;

    QDataStream numberStream(byteArray);
    numberStream.setByteOrder(QDataStream::BigEndian);

    numberStream >> number;

    return number;
}

QByteArray ByteArrayParser::fromQInt32(const qint32 number)
{
    QByteArray byteArray;

    QDataStream byteStream(&byteArray, QIODevice::WriteOnly);
    byteStream.setByteOrder(QDataStream::BigEndian);

    byteStream << number;

    return byteArray;
}

QString ByteArrayParser::parseQString(const QByteArray byteArray, const qint32 length)
{
    return QString::fromUtf8(byteArray, length);
}

QByteArray ByteArrayParser::fromQString(const QString string)
{
    return string.toUtf8();
}
