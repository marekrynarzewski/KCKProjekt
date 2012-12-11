package Sieć;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import QA.QA;

import exception.NoLocalFileException;


public class File
{
	public static String zaladujPlik(String nazwa) throws NoLocalFileException, FileNotFoundException, IOException
	{
		String wynik = "";
		FileReader fr;
		fr = new FileReader(nazwa);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null)
		{
			wynik = wynik+s+"\n";
		}
		br.close();
		fr.close();
		return wynik;
	}
	
	public static Vector<String> plikDoTablicy(String nazwa) throws IOException
	{
		Vector<String> result = new Vector<String>();
		FileReader fr;
		fr = new FileReader(nazwa);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null)
		{
			result.add(s);
		}
		br.close();
		fr.close();
		return result;
	}
	
	public static String pobierzZUrla(String path) throws IOException
	{
		URL url = new URL(path);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new  InputStreamReader(is);
		BufferedReader in = new BufferedReader(isr);
		String result = new String("");
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		{
			result = result.concat(inputLine);
		}
		in.close();
		isr.close();
		is.close();
		return result;
	}
	
	public static String stripTags(String inp)
	{
		Vector<String> znaczniki = new Vector<String>();
		String HTMLTag = "";
		boolean intag = false;
		String outp = "";
		Character prevChar = new Character(' ');
		for (int i=0; i < inp.length(); ++i)
		{
		    if (!intag && inp.charAt(i) == '<')
	        {
	            intag = true;
	            prevChar = inp.charAt(i);
	            continue;
	        }
		    if (intag && inp.charAt(i) == '/' && prevChar == '<')
		    {
		    	
		    }
	        if (intag && inp.charAt(i) == '>')
	        {
	            intag = false;
	            continue;
	        }
	        if (!intag)
	        {
	        	znaczniki.add(HTMLTag);
	            outp = outp + inp.charAt(i);
	            HTMLTag = "";
	        }
	        else
	        {
	        	HTMLTag += inp.charAt(i);
	        }
		}
		Iterator<String> it = znaczniki.iterator();
		while (it.hasNext())
		{
			QA.sop(it.next()+", ");
		}
		return outp;
	}
	public static String stripTags2(String inp)
	{
		Pattern wzorzec = Pattern.compile("<([a-zA-Z]+\\s+)>");
		Matcher sekwencja = wzorzec.matcher(inp);
		String outp = "";
		while (sekwencja.find())
		{
			QA.sopln(sekwencja.group());
			//QA.sopln(sekwencja.group(2));
			//QA.sopln(sekwencja.group(3));
		}
		//outp = outp + inp.charAt(i);
		return outp;
		
		//Jak się nazywa największy pałac w Europie
	}
}
