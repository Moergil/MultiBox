using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiboxClientCSharpCore.Model
{
	public class GenericLibraryItem : ILibraryItem
	{
		private readonly long id;
		private readonly LibraryItemType type;
		private readonly string name;

		public GenericLibraryItem(long id, LibraryItemType type, string name)
		{
			this.id = id;
			this.type = type;
			this.name = name;
		}

		public long Id
		{
			get { return id; }
		}

		public LibraryItemType Type
		{
			get { return type; }
		}

		public string Name
		{
			get { return name; }
		}

		public override bool Equals(object obj)
		{
			if (obj == null)
			{
				return false;
			}

			if (!(obj is ILibraryItem))
			{
				return false;
			}

			ILibraryItem item = obj as ILibraryItem;

			return Equals(item);
		}

		public bool Equals(ILibraryItem item)
		{
			return item.Id == this.Id;
		}

		public override int GetHashCode()
		{
			return id.GetHashCode();
		}

		public override string ToString()
		{
			return "#" + Id + " " + Name + " (" + Type + ")";
		}
	}
}
