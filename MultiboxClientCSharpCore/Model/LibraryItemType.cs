using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiboxClientCSharpCore.Model
{
	public enum LibraryItemType
	{
		Directory = 1,
		Multimedia = 2,
	}

	public class LibraryItemTypeParser
	{
		private static Dictionary<string, LibraryItemType> map;

		static LibraryItemTypeParser()
		{
			map = new Dictionary<string, LibraryItemType>();

			map["DIRECTORY"] = LibraryItemType.Directory;
			map["MULTIMEDIA"] = LibraryItemType.Multimedia;
		}

		public static LibraryItemType Parse(string name)
		{
			return map[name];
		}
	}
}
