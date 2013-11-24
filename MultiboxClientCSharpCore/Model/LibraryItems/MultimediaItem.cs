using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiBoxCSharpCore
{
	class MultimediaItem : GenericLibraryItem
	{
		public MultimediaItem(long id, string name)
		: base(id, LibraryItemType.Multimedia, name)
		{
		}
	}
}
