package sk.hackcraft.util;

public class ConsoleLog implements Log
{
	@Override
	public void print(String message)
	{
		System.out.println(message);
	}

	@Override
	public void printf(String format, Object... arguments)
	{
		System.out.printf(format + "%n", arguments);
	}
}
