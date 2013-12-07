#include "getlibraryitemrequest.h"

GetLibraryItemRequest::GetLibraryItemRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
}

qint32 GetLibraryItemRequest::getItemId() const
{
    return itemId;
}

bool GetLibraryItemRequest::canResponse() const
{
    return true;
}

void GetLibraryItemRequest::execute()
{

}
