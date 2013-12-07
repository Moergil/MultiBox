#include "abstractresponse.h"

#pragma once

class GetLibraryItemResponse : public AbstractResponse
{
    public:
        GetLibraryItemResponse();

        DataContent toDataContent();
        qint32 getMessageCode();
};
