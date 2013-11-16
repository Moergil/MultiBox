#pragma once

#include "message/abstractrequest.h"
#include "network/messangerinterface.h"

#include <QObject>

class MessageProcessor : public QObject
{
    Q_OBJECT

private:
    MessangerInterface *messanger;
    PlayerHandler *handler;

    AbstractRequest *recognizeRequest(DataMessage &dataMessage);

public:
    enum MessageCode {
        GetPlayerState              = 1,
        GetPlaylist                 = 2,
        GetLibraryDirectoryContent  = 3,
        GetLibraryItemInfo          = 4,
        AddItemToLibrary            = 5,
        AddItemToPlaylist           = 6,
        Pause                       = 7
    };

    MessageProcessor(PlayerHandler *handler, int socketDescriptor, QObject *parent = 0);
    PlayerHandler *getPlayerHandler();

    void proccess();
};
