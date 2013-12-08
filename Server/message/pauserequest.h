#include "abstractrequest.h"

#pragma once

class PauseRequest : public AbstractRequest
{
        Q_OBJECT

    private:
        bool playing;

    public:
        PauseRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        bool canResponse() const;
        void execute();
};
