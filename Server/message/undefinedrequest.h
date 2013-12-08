#include "abstractrequest.h"

#pragma once

class UndefinedRequest : public AbstractRequest
{
    Q_OBJECT

public:
    UndefinedRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

    bool canResponse() const;
    void execute();
};
