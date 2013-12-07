#include "abstractrequest.h"

#pragma once

class GetLibraryItemRequest : public AbstractRequest
{
    private:
        qint32 itemId;

    public:
        GetLibraryItemRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        qint32 getItemId() const;

        bool canResponse() const;
        void execute();
};
