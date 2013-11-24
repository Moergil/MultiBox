#pragma once

#include <QObject>

#include <network/datacontent.h>

class AbstractResponse : public QObject
{
    Q_OBJECT

public:
    explicit AbstractResponse(QObject *parent = 0);
    virtual DataContent toDataContent() = 0;
};

