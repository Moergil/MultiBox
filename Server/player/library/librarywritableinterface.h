#include <QString>

#pragma once

class LibraryWritableInterface
{
    public:
        virtual QString getIdentifier() const = 0;
        virtual QString getInsertString() const = 0;
};
