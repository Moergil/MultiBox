#include "network/datamessage.h"
#include "abstractresponse.h"
#include "playerhandler.h"

#include <QObject>

#pragma once

class AbstractRequest : public QObject
{
        Q_OBJECT

    private:
        DataMessage dataMessage;
        PlayerHandler *handler;
        AbstractResponse *response;

    public:
        AbstractRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        DataMessage &getDataMessage();
        PlayerHandler *getPlayerHandler() const;
        AbstractResponse *getResponse() const;

        void setResponse(AbstractResponse *response);

        virtual bool canResponse() const = 0;
        virtual void execute() = 0;
};
