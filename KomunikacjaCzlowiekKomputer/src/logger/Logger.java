package logger;

import java.io.IOException;
import java.util.Date;

import siec.Filex;
import java.text.SimpleDateFormat;

public class Logger
{
	private static final String logFile = "logs/logfile.txt";
	
	/**
	 * loguje do pliku logowania
	 * @param s
	 */
	public static void log(String s)
	{
		Logger.checkExistsLogFile();
		String dt = Logger.uzyskajDateWMoimFormacie();
		try
		{
			String con = dt+" "+s+"\n";
			Filex.append(Logger.logFile, con);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * uzyskuje datę w moim formacie
	 * @return
	 */
	private static String uzyskajDateWMoimFormacie()
	{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss a");
		return f.format(d);
	}
	
	/**
	 * sprawdza czy plik logowania istnieje
	 */
	private static void checkExistsLogFile()
	{
		java.io.File f = new java.io.File(Logger.logFile);
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}