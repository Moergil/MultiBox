#include "writablemultimedia.h"

WritableMultimedia::WritableMultimedia(const qint64 id, const QString name, const qint32 length, const QFileInfo file, const qint64 parentId)
    : Multimedia(id, name, length, file, parentId)
{
}

QString WritableMultimedia::getIdentifier() const
{
    return getFileInfo().absoluteFilePath();
}

QString WritableMultimedia::getInsertString() const
{
    return QString("INSERT INTO library (name, length, path, type, parent) "
                   "VALUES ('%1', %2, '%3', %4, (SELECT id FROM library WHERE path = '%5'))")
            .arg(getName().replace("'", "''"))
            .arg(getLength())
            .arg(getFileInfo().absoluteFilePath().replace("'", "''"))
            .arg(getType().getId())
            .arg(getFileInfo().absolutePath().replace("'", "''"));
}
