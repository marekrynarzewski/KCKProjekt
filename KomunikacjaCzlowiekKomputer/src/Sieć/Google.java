package Sieć;

import java.io.IOException;

import java.net.MalformedURLException;

import QA.QA;

public class Google
{
	public static String zapytajGoogla(String pytanie)
	{
		String adres = "http://szukaj.onet.pl/wyniki.html?qt="+pytanie;
		//adres = "http://www.google.pl?q="+pytanie;
		try
		{
			return File.pobierzZUrla(adres);
		}
		catch (MalformedURLException e)
		{
			QA.sop("MalformedURLException");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			QA.sop("IOException");
			e.printStackTrace();
		}
		return "";
	}
	
	public static String zapytajGoogla(String pytanie, int page)
	{
		String adres = "http://szukaj.onet.pl/0,"+page+",query.html?qt="+pytanie;
		try
		{
			return File.pobierzZUrla(adres);
		}
		catch (MalformedURLException e)
		{
			QA.sop("MalformedURLException");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			QA.sop("IOException");
			e.printStackTrace();
		}
		return "";
	}
	
}