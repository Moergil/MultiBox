#include "abstractresponse.h"

#pragma once

class GetServerInfoResponse : public AbstractResponse
{
        Q_OBJECT

    private:
        const QString playerName;

    public:
        GetServerInfoResponse(const QString &playerName);

        DataContent toDataContent() const;
        qint32 getMessageCode() const;
};
