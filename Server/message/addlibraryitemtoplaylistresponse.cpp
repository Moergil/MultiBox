#include "addlibraryitemtoplaylistresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

AddLibraryItemToPlaylistResponse::AddLibraryItemToPlaylistResponse(const bool result, const Multimedia *multimedia)
    : result(result), multimedia(multimedia)
{
}

DataContent AddLibraryItemToPlaylistResponse::toDataContent() const
{
    QJsonObject object;
    object.insert("result", result);
    object.insert("multimedia", multimedia->toQJsonObject());

    MessageContentWriter writer;
    writer.write(object);
    return writer.toDataContent();
}

qint32 AddLibraryItemToPlaylistResponse::getMessageCode() const
{
    return MessageRecognizer::AddLibraryItemToPlaylist;
}
