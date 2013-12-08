using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiboxClientCSharpCore.Model
{
	public class MultimediaItem : ILibraryItem
	{
		private readonly long id;
		private readonly string name;
		private readonly int length;

		public MultimediaItem(long id, string name, int length)
		{
			this.id = id;
			this.name = name;
			this.length = length;
		}

		public MultimediaItem(MultimediaItem multimedia)
		{
			this.id = multimedia.id;
			this.name = multimedia.name;
			this.length = multimedia.length;
		}

		public long Id
		{
			get { return id; }
		}

		public LibraryItemType Type
		{
			get { return LibraryItemType.Multimedia; }
		}

		public string Name
		{
			get { return name; }
		}
		public int Length
		{
			get { return length; }
		}

		public override bool Equals(object obj)
		{
			if (obj == null)
			{
				return false;
			}

			if (!(obj is MultimediaItem))
			{
				return false;
			}

			MultimediaItem multimedia = (MultimediaItem)obj;
			return Equals(multimedia);
		}

		public bool Equals(MultimediaItem multimedia)
		{
			return multimedia.id == this.id;
		}

		public override int GetHashCode()
		{
			return id.GetHashCode();
		}

		public override string ToString()
		{
			return "#" + Id + " " + Name;
		}
	}
}