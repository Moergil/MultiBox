using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace CSharpNetInterface.Util
{
	public class NetworkBinaryReader
	{
		private readonly Stream stream;

		public NetworkBinaryReader(Stream stream)
		{
			this.stream = stream;
		}

		public int ReadInt()
		{
			byte[] buffer = new byte[4];
			ReadBytes(buffer);

			int value = BitConverter.ToInt32(buffer, 0);

			if (BitConverter.IsLittleEndian)
			{
				value = ByteSwapper.Swap(value);
			}

			return value;
		}

		public string ReadUTF()
		{
			int length = ReadInt();
			byte[] stringBytes = new byte[length];
			ReadBytes(stringBytes);

			return Encoding.UTF8.GetString(stringBytes, 0, length);
		}

		public void ReadBytes(byte[] buffer)
		{
			stream.Read(buffer, 0, buffer.Length);
		}
	}
}
