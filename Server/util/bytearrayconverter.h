#include <QByteArray>
#include <QJsonObject>

#pragma once

class ByteArrayConverter
{
public:
    static qint32 parseQInt32(const QByteArray &byteArray);
    static QByteArray fromQInt32(const qint32 number);
    static qint64 parseQInt64(const QByteArray &byteArray);
    static QByteArray fromQInt64(const qint64 number);
    static QString parseQString(const QByteArray &byteArray);
    static QByteArray fromQString(const QString &string);
    static QJsonObject parseQJsonObject(const QByteArray &byteArray);
    static QByteArray fromQJsonObject(QJsonObject &qJsonObject);
    static bool parseBool(const QByteArray &byteArray);
    static QByteArray fromBool(const bool value);
};
