package siec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.Vector;

import qa.QA;

import logger.Logger;

public class Filex
{
	/**
	 * uzyskuję zawartość pliku.
	 * @param nazwa - nazwa pliku, którego zawartość chcemy uzyskać
	 * @return zawartość pliku
	 * @throws FileNotFoundException rzuca jeśli nie znalazł pliku
	 * @throws IOException gdy wystąpił błąd wejścia lub wyjścia
	 */
	public static String zaladujPlik(String nazwa) throws FileNotFoundException, IOException
	{
		String wynik = "";
		FileReader fr;
		fr = new FileReader(nazwa);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null)
		{
			wynik = wynik + s + "\n";
		}
		br.close();
		fr.close();
		return wynik;
	}
	
	/**
	 * uzyskuje zawartość pliku i zapisuje do wektora
	 * @param nazwa - nazwa pliku
	 * @return zawartość
	 * @throws IOException gdy wystąpił błąd wejścia lub wyjścia
	 */
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
	
	/**
	 * pobiera plik z URLa
	 * @param path - ścieżka jako string
	 * @return zawartość pliku
	 * @throws IOException gdy wystąpił błąd wejścia lub wyjścia
	 */
	public static String pobierzZUrla(String path) throws IOException
	{
		URL url = new URL(path);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
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
	
	/**
	 * zapisuje do pliku
	 * @param filename - nazwa pliku do zapisu
	 * @param content - zawartość
	 * @return nowa długość pliku lub 0
	 */
	public static long saveToFile(String filename, String content)
	{
		long beforeSave = Filex.size(filename);
		long afterSave = 0;
		try
		{
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
			afterSave = Filex.size(filename);
		}
		catch (IOException ioe)
		{
			return 0;
		}
		return afterSave - beforeSave;
	}
	
	/**
	 * uzyskuje rozmiar pliku
	 * @param filename plik
	 * @return
	 */
	public static long size(String filename)
	{
		java.io.File f = new java.io.File(filename);
		return f.length();
	}
	
	/**
	 * dodaje do pliku
	 * @param filename - nazwa pliku do zapisu
	 * @param s - zawartość
	 * @return długość dodaną do pliku
	 * @throws IOException gdy wystąpił błąd wejścia lub wyjścia
	 */
	public static long append(String filename, String s) throws IOException
	{
		long beforeAppend = Filex.size(filename);
		FileWriter fw = new FileWriter(filename, true);
		fw.write(s);
		fw.close();
		long afterAppend = Filex.size(filename);
		return afterAppend - beforeAppend;
	}
	
	public static String getContentType(String plik)
	{
		URL url;
		try
		{
			url = new URL(plik);
			URLConnection urlc = url.openConnection();
			return urlc.getHeaderField("Content-Type");
		}
		catch (MalformedURLException e)
		{
			Logger.log("Malformed URL");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			Logger.log("Occured IO Exception");
			e.printStackTrace();
		}
		return "";
	}
	
	public static <T, K> long mapaDoPliku(String plik, Map<T, K> mapa)
	{
		String content = "";
		for (Map.Entry<T, K> wpis : mapa.entrySet())
		{
			content += wpis.getKey()+" = "+wpis.getValue()+"\n";
		}
		return Filex.saveToFile(plik, content);
	}

}
