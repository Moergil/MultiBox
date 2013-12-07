#include "abstractrequest.h"

#pragma once

class GetServerInfoRequest : public AbstractRequest
{
        Q_OBJECT

    public:
        GetServerInfoRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        bool canResponse() const;
        void execute();
};
