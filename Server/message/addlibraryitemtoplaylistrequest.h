#include "abstractrequest.h"

#pragma once

class AddLibraryItemToPlaylistRequest : public AbstractRequest
{
        Q_OBJECT

    private:
        qint64 multimediaId;

    public:
        AddLibraryItemToPlaylistRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent = 0);

        bool canResponse() const;
        void execute();
};
