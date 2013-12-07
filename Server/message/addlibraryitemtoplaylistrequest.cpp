#include "addlibraryitemtoplaylistrequest.h"
#include "addlibraryitemtoplaylistresponse.h"

#include <util/messagecontentreader.h>
#include <QVariant>

AddLibraryItemToPlaylistRequest::AddLibraryItemToPlaylistRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    QJsonObject json = reader.readQJsonObject();
    multimediaId = json.value("multimediaId").toVariant().toLongLong();
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
