#include "getplayerstaterequest.h"
#include "messagerecognizer.h"
#include "pauserequest.h"
#include "undefinedrequest.h"


MessageRecognizer::MessageRecognizer(PlayerHandler *handler, QObject *parent)
    : QObject(parent), handler(handler)
{
}

AbstractRequest *MessageRecognizer::recognizeMessage(const DataMessage &dataMessage)
{
    switch(dataMessage.getMessageType())
    {
        case GetPlayerState:
            return new GetPlayerStateRequest(dataMessage, handler, this);

        /* case GetPlaylist:
            break;

        case GetLibraryDirectoryContent:
            break;

        case GetLibraryItemInfo:
            break;

        case AddItemToLibrary:
            break;

        case AddItemToPlaylist:
            break; */

        case Pause:
            return new PauseRequest(dataMessage, handler);

        default:
            return new UndefinedRequest(dataMessage, handler);
    }
}
