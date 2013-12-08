#include "abstractrequest.h"

#pragma once

class MessageRecognizer : public QObject
{
    public:
        enum MessageCode {
            GetPlayerState              = 1,
            GetPlaylist                 = 2,
            GetLibraryItem              = 3,
            AddLibraryItemToPlaylist    = 4,
            GetServerInfo               = 5,
            Pause                       = 7
        };

    private:
        PlayerHandler *handler;

    public:
        MessageRecognizer(PlayerHandler *handler, QObject *parent = 0);
        AbstractRequest *recognizeMessage(const DataMessage &dataMessage);
};
