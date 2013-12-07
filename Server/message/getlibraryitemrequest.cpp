#include "getlibraryitemrequest.h"
#include "getlibraryitemresponse.h"
#include <util/messagecontentreader.h>
#include <QVariant>
#include <QDebug>

GetLibraryItemRequest::GetLibraryItemRequest(const DataMessage &dataMessage, PlayerHandler *handler, QObject *parent)
    : AbstractRequest(dataMessage, handler, parent)
{
    MessageContentReader reader(getDataMessage().getDataContent());
    QJsonObject json = reader.readQJsonObject();
    itemId = json.value("itemId").toVariant().toLongLong();

    qDebug() << itemId << "itemId";
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
