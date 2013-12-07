#include "getlibraryitemresponse.h"
#include "messagerecognizer.h"

GetLibraryItemResponse::GetLibraryItemResponse()
{
}

DataContent GetLibraryItemResponse::toDataContent()
{

}

qint32 GetLibraryItemResponse::getMessageCode()
{
    return MessageRecognizer::GetLibraryItem;
}
