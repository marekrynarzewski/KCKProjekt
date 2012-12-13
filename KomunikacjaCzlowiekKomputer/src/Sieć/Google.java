package Sieć;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URLEncoder;

import QA.QA;

public class Google
{
	private static final String SzukajOnet = "http://szukaj.onet.pl/wyniki.html?qt=";
	private static final String GoogleAPI = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	
	public static String zapytajGoogla(String pytanie)
	{
		String adres = Google.GoogleAPI+pytanie;
		//adres = "http://www.google.pl?q="+pytanie;
		try
		{
			adres = Google.GoogleAPI+URLEncoder.encode(pytanie, "UTF-8");
			;
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
			adres = Google.GoogleAPI+pytanie;
			adres = URLEncoder.encode(pytanie, "UTF-8");
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
			
		}
		catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "";
	}
	
}