#include <QString>
#include <exception>

#pragma once

class MessangerException : public std::exception
{
private:
    QString message;

public:
    MessangerException(QString message);
    //QString getMessage() const;
};
