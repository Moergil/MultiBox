#include "messengerexception.h"

MessengerException::MessengerException(const QString message)
    : message(message)
{
}

MessengerException::~MessengerException() throw()
{
}

QString MessengerException::getMessage() const
{
    return message;
}
