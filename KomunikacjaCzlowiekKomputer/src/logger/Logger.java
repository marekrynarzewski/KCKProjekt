package logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import qa.QA;
import siec.Filex;
import java.text.SimpleDateFormat;

public class Logger
{
	private static final String LogFile = "logs/logfile.txt";
	
	public void log(String s)
	{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss a");
		String dt = f.format(d);
		try
		{
			String con = Filex.zaladujPlik(LogFile);
			con += dt+" "+s+"\n";
			Filex.saveToFile(LogFile, con);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Logger()
	{
		java.io.File f = new java.io.File(Logger.LogFile);
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}