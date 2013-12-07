#include "addlibraryitemtoplaylistresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

AddLibraryItemToPlaylistResponse::AddLibraryItemToPlaylistResponse(const bool result, const Multimedia *multimedia)
    : result(result), multimedia(multimedia)
{
}

DataContent AddLibraryItemToPlaylistResponse::toDataContent() const
{
    MessageContentWriter writer;
    writer.write(result);
    writer.write(multimedia->toQJsonObject());
    return writer.toDataContent();
}

qint32 AddLibraryItemToPlaylistResponse::getMessageCode() const
{
    return MessageRecognizer::AddLibraryItemToPlaylist;
}
