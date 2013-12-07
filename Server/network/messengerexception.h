#include <QString>
#include <exception>

#pragma once

class MessengerException : public std::exception
{
    private:
        const QString message;

    public:
        MessengerException(const QString message);
        ~MessengerException() throw();
        QString getMessage() const;
};
