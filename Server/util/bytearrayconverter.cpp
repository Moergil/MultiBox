#include "bytearrayconverter.h"

#include <QDataStream>
#include <QJsonDocument>
#include <QDebug>

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

QJsonObject ByteArrayConverter::parseQJsonObject(const QByteArray &byteArray)
{
    QJsonDocument document = QJsonDocument::fromJson(byteArray);
    return document.object();
}

QByteArray ByteArrayConverter::fromQJsonObject(QJsonObject &qJsonObject)
{
    QJsonDocument document;
    document.setObject(qJsonObject);
    return document.toJson();
}
