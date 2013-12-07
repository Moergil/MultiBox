#include "getlibraryitemrequest.h"
#include "getlibraryitemresponse.h"
#include <util/messagecontentreader.h>

GetLibraryItemRequest::GetLibraryItemRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    itemId = reader.readQInt64();
}

bool GetLibraryItemRequest::canResponse() const
{
    return true;
}

void GetLibraryItemRequest::execute()
{
    LibraryItem *item = getPlayerHandler()->getLibraryItem(itemId);
    GetLibraryItemResponse *response = new GetLibraryItemResponse(item);

    setResponse(response);
}
