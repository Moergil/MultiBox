using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiBoxCSharpCore
{
	class BackNavigationLibraryItem : GenericLibraryItem
	{
		public BackNavigationLibraryItem()
		: base(LibraryConstants.BackNavigation, LibraryItemType.BackNavigation, "..")
		{
		}
	}
}
