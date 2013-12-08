#include <QJsonObject>

#pragma once

class QJsonExportableInterface
{
    public:
        virtual QJsonObject toQJsonObject() const = 0;
};

