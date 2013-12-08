using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiboxClientCSharpCore.Model
{
	public interface ILibraryItem
	{
		long Id
		{
			get;
		}

		LibraryItemType Type
		{
			get;
		}

		string Name
		{
			get;
		}
	}
}
