#include "getlibraryitemresponse.h"
#include "messagerecognizer.h"

#include <util/messagecontentwriter.h>

GetLibraryItemResponse::GetLibraryItemResponse(const LibraryItem *item)
    : item(item)
{
}

DataContent GetLibraryItemResponse::toDataContent() const
{
    MessageContentWriter writer;
    writer.write(item->toQJsonObject());
    return writer.toDataContent();
}

qint32 GetLibraryItemResponse::getMessageCode() const
{
    return MessageRecognizer::GetLibraryItem;
}
