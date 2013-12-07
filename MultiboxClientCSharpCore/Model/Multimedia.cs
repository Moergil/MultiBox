using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace MultiBoxCSharpCore
{
	public class Multimedia
	{
		private readonly long id;
		private readonly string name;
		private readonly int length;

		public Multimedia(long id, string name, int length)
		{
			this.id = id;
			this.name = name;
			this.length = length;
		}

		public Multimedia(Multimedia multimedia)
		{
			this.id = multimedia.id;
			this.name = multimedia.name;
			this.length = multimedia.length;
		}

		public long Id
		{
			get { return id; }
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

			if (!(obj is Multimedia))
			{
				return false;
			}

			Multimedia multimedia = (Multimedia)obj;
			return Equals(multimedia);
		}

		public bool Equals(Multimedia multimedia)
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