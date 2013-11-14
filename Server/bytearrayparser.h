#include <QByteArray>

#pragma once

class ByteArrayParser
{
public:
    static qint32 parseQInt32(const QByteArray byteArray);
    static QByteArray fromQInt32(const qint32 number);
    static QString parseQString(const QByteArray byteArray, const qint32 length);
    static QByteArray fromQString(const QString string);
};
