#include "bytearrayconverter.h"

#include <QDataStream>

qint32 ByteArrayConverter::parseQInt32(const QByteArray &byteArray)
{
    qint32 number;

    QDataStream numberStream(byteArray);
    numberStream.setByteOrder(QDataStream::BigEndian);

    numberStream >> number;

    return number;
}

QByteArray ByteArrayConverter::fromQInt64(const qint64 number)
{
    QByteArray byteArray;

    QDataStream byteStream(&byteArray, QIODevice::WriteOnly);
    byteStream.setByteOrder(QDataStream::BigEndian);

    byteStream << number;

    return byteArray;
}

qint64 ByteArrayConverter::parseQInt64(const QByteArray &byteArray)
{
    qint64 number;

    QDataStream numberStream(byteArray);
    numberStream.setByteOrder(QDataStream::BigEndian);

    numberStream >> number;

    return number;
}

QByteArray ByteArrayConverter::fromQInt32(const qint32 number)
{
    QByteArray byteArray;

    QDataStream byteStream(&byteArray, QIODevice::WriteOnly);
    byteStream.setByteOrder(QDataStream::BigEndian);

    byteStream << number;

    return byteArray;
}

bool ByteArrayConverter::parseBool(const QByteArray &byteArray)
{
    bool value;

    QDataStream stream(byteArray);
    stream.setByteOrder(QDataStream::BigEndian);

    stream >> value;

    return value;
}

QByteArray ByteArrayConverter::fromBool(const bool value)
{
    QByteArray byteArray;

    QDataStream byteStream(&byteArray, QIODevice::WriteOnly);
    byteStream.setByteOrder(QDataStream::BigEndian);

    byteStream << value;

    return byteArray;
}

QString ByteArrayConverter::parseQString(const QByteArray &byteArray)
{
    return QString::fromUtf8(byteArray, byteArray.length());
}

QByteArray ByteArrayConverter::fromQString(const QString &string)
{
    return string.toUtf8();
}

QJsonDocument ByteArrayConverter::parseQJsonDocument(const QByteArray &byteArray)
{
    return QJsonDocument::fromJson(byteArray);
}

QByteArray ByteArrayConverter::fromQJsonDocument(QJsonDocument &qJsonDocument)
{
    return qJsonDocument.toJson();
}
