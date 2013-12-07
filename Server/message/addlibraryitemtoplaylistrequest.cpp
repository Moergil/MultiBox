#include "addlibraryitemtoplaylistrequest.h"
#include "addlibraryitemtoplaylistresponse.h"

#include <util/messagecontentreader.h>

AddLibraryItemToPlaylistRequest::AddLibraryItemToPlaylistRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    multimediaId = reader.readQInt64();
}

bool AddLibraryItemToPlaylistRequest::canResponse() const
{
    return true;
}

void AddLibraryItemToPlaylistRequest::execute()
{
    Multimedia *multimedia = (Multimedia *) getPlayerHandler()->getLibraryItem(multimediaId);
    emit getPlayerHandler()->addToPlaylist(multimedia);

    //TODO: vzdy true
    AddLibraryItemToPlaylistResponse *response = new AddLibraryItemToPlaylistResponse(true, multimedia);
    setResponse(response);
}
