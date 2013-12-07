#include "getlibraryitemrequest.h"
#include "getplayerstaterequest.h"
#include "getplaylistrequest.h"
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

        case GetPlaylist:
            return new GetPlaylistRequest(dataMessage, handler, this);

        case GetLibraryItem:
            return new GetLibraryItemRequest(dataMessage, handler, this);

        /*case GetLibraryItemInfo:
            break;

        case AddItemToLibrary:
            break;

        case AddItemToPlaylist:
            break; */

        case Pause:
            return new PauseRequest(dataMessage, handler, this);

        default:
            return new UndefinedRequest(dataMessage, handler, this);
    }
}
