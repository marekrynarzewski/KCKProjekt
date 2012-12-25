package siec;

import exception.Error;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import qa.QA;


public class Google
{
	private static final String SzukajOnet = "http://szukaj.onet.pl/wyniki.html?qt=";
	private static final String SzukajOnetStrona =  "http://szukaj.onet.pl/0,{page},query.html?qt=";
	private static final String GoogleAPI = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	private static final String EncodingUTF8 = "UTF-8";
	
	public static String zapytajGoogla(String pytanie)
	{
		String adres = Google.SzukajOnet+pytanie;
		try
		{
			adres = Google.GoogleAPI+URLEncoder.encode(pytanie, "UTF-8");
			return Filex.pobierzZUrla(adres);
		}
		catch (MalformedURLException e)
		{
			QA.sop("MalformedURLException");
			System.exit(Error.ErrorInUrl);
		}
		catch(UnknownHostException uhe)
		{
			QA.sopln("Nieznany host lub Brak internetu");
			System.exit(Error.ErrorHost);
		}
		catch (IOException e)
		{
			QA.sop("IOException");
			System.exit(Error.ErrorIO);
		}
		
		return "";
	}
	
	public static String zapytajStrone(String pytanie, int page)
	{
		String adres = Google.SzukajOnetStrona;
		adres = adres.replaceFirst("\\{page\\}", String.valueOf(page));
		try
		{
			pytanie = URLEncoder.encode(pytanie, Google.EncodingUTF8);
			adres = adres+pytanie;
			QA.sopln(adres);
			try
			{
				return Filex.pobierzZUrla(adres);
			}
			catch (MalformedURLException e)
			{
				QA.sopln("MalformedURLException");
				System.exit(Error.ErrorLink);
			}
			catch (IOException e)
			{
				QA.sopln("IOException");
				System.exit(Error.ErrorIO);
			}
			
		}
		catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return "";
	}
	
	public static String zapytajNStron(String pytanie, int ileStron)
	{
		String result = "";
		for (int i = 0; i < ileStron; ++i)
		{
			result += Google.zapytajStrone(pytanie, i);
		}
		return result;
	}
	
}