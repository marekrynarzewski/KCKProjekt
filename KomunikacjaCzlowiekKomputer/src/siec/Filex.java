package siec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qa.QA;

public class Filex
{
	public static String zaladujPlik(String nazwa) throws FileNotFoundException, IOException
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
		boolean intag = false;
		String outp = "";
		for (int i=0; i < inp.length(); ++i)
		{
		    if (!intag && inp.charAt(i) == '<')
	        {
	            intag = true;
	            
	            continue;
	        }
	        if (intag && inp.charAt(i) == '>')
	        {
	            intag = false;
	            continue;
	        }
	        if (!intag)
	        {      	
	            outp = outp + inp.charAt(i);	        }
		}
		return outp;
	}
	
	public static long saveToFile(String filename, String content)
	{
		
		long BeforeSave = Filex.size(filename);
		long AfterSave = 0;
		try
		{
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
			AfterSave = Filex.size(filename);
		}
		catch(IOException ioe)
		{
			QA.sopln("Logger.log() cannot file found!");
		}
		return AfterSave - BeforeSave;
	}
	
	public static long size(String filename)
	{
		java.io.File f = new java.io.File(filename);
		return f.length();
	}
	
	public static long append(String filename, String s)
	{
		long result = 0;
		return result;
	}
}
