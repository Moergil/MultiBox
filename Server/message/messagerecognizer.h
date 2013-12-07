#include "abstractrequest.h"

#pragma once

class MessageRecognizer : public QObject
{
    public:
        enum MessageCode {
            GetPlayerState              = 1,
            GetPlaylist                 = 2,
            GetLibraryItem              = 3,
            GetLibraryItemInfo          = 4,
            AddItemToLibrary            = 5,
            AddItemToPlaylist           = 6,
            Pause                       = 7
        };

    private:
        PlayerHandler *handler;

    public:
        MessageRecognizer(PlayerHandler *handler, QObject *parent = 0);
        AbstractRequest *recognizeMessage(const DataMessage &dataMessage);
};
