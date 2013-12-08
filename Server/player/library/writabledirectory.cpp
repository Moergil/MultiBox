#include "writabledirectory.h"

WritableDirectory::WritableDirectory(const qint64 id, const QString name, const QFileInfo file, QList<LibraryItem *> items, qint64 parentId)
    : Directory(id, name, file, items, parentId)
{
}

QString WritableDirectory::getIdentifier() const
{
    return getFileInfo().absoluteFilePath();
}

QString WritableDirectory::getInsertString() const
{
    return QString("INSERT INTO library (name, path, type, parent) "
                   "VALUES ('%1', '%2', %3, (SELECT id FROM library WHERE path = '%4'));")
            .arg(getName().replace("'", "''"))
            .arg(getFileInfo().absoluteFilePath().replace("'", "''"))
            .arg(getType().getId())
            .arg(getFileInfo().absolutePath().replace("'", "''"));
}
