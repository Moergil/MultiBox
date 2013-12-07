#include "abstractrequest.h"

#pragma once

class GetLibraryItemRequest : public AbstractRequest
{
        Q_OBJECT

    private:
        qint32 itemId;

    public:
        GetLibraryItemRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        bool canResponse() const;
        void execute();
};
