package logger;

import java.io.IOException;
import java.util.Date;

import qa.QA;
import siec.Filex;
import java.text.SimpleDateFormat;

public class Logger
{
	private static final String sciezkaLogowania = "logs";
	
	private static String plikLogowania;
	private static String zmiennyPlikLogowania;
	private static String zmiennyKatalogLogowania;
	
	private static final boolean osobnePlikiLogowania = true;
	private static boolean pierwszeLogowanie = true;
	
	/**
	 * loguje do pliku logowania
	 * @param wiadomosc - dane do zapisania
	 */
	public static void log(String wiadomosc)
	{
		
		if (Logger.osobnePlikiLogowania)
		{
			Logger.zmiennyPlikLogowania = Logger.uzyskajCzasWMoimFormacie();
			
		}
		if (Logger.pierwszeLogowanie)
		{
			Logger.zmiennyKatalogLogowania = Logger.uzyskajDateWMoimFormacie();
			Logger.utworzPlikLogowania();
			Logger.pierwszeLogowanie = false;
		}
		Logger.zapiszDoPlikuLogowania(wiadomosc+"\n");
		
	}
	
	private static void utworzPlikLogowania()
	{
		if (!Logger.osobnePlikiLogowania)
		{
			Logger.plikLogowania = "logfile.txt";
		}
		else
		{
			Logger.plikLogowania = Logger.zmiennyKatalogLogowania+"\\"+Logger.zmiennyPlikLogowania+".txt";
		}
		if (!Logger.czyPlikIstnieje(plikLogowania))
		{
			try
			{
				new java.io.File(Logger.sciezkaLogowania+'\\'+Logger.plikLogowania).createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void zapiszDoPlikuLogowania(String wiadomosc)
	{
		try
		{
			Filex.append(Logger.sciezkaLogowania+'\\'+Logger.plikLogowania, wiadomosc);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * uzyskuje datę w moim formacie
	 * @return data w moim formacie
	 */
	private static String uzyskajDateWMoimFormacie()
	{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		return f.format(d);
	}
	
	/**
	 * uzyskuje czas w moim formacie
	 * @return czas w moim formacie
	 */
	private static String uzyskajCzasWMoimFormacie()
	{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("HHmmss");
		return f.format(d);
	}
	
	private static String uzyskajDateICzasWMoimFormacie()
	{
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		return f.format(d);
	}
	
	/**
	 * sprawdza czy plik ze ścieżki logowania istnieje
	 */
	private static boolean czyPlikIstnieje(String plik)
	{
		String pl = Logger.sciezkaLogowania+'\\'+plik;
		java.io.File f = new java.io.File(pl);
		System.out.println(f.exists()+" "+pl);
		return f.exists();
	}
	
	private static boolean utworzKatalog(String katalog)
	{
		String pl = Logger.sciezkaLogowania+'\\'+katalog;
		java.io.File f = new java.io.File(pl);
		return f.mkdir();
	}

}