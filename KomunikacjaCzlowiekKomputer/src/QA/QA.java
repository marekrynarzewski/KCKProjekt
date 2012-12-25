package qa;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logger.Logger;
import exception.Error;
import java.io.*;

public class QA
{
	public static Scanner input = new Scanner(System.in, "UTF-8");
	//private static Logger logger = new Logger();
	private String pytanie = "";
	private String X;
	private String Y;
	private String przyimek;
	private String przymiotnik;
	private Vector<String> linki;
	private Vector<String> synonimy = new Vector<String>();
	
	public QA()
	{
	}
	
	public void rzucWGoogle()
	{
		String fraza = this.przymiotnik + " " + this.X + " "+this.przyimek+" " + this.Y;
		if (!fraza.contains("null"))
		{
			fraza = fraza.toLowerCase();
			String HTMLdokument = siec.Google.zapytajNStron(fraza, 10);
			this.linki = this.znajdzLinki(HTMLdokument);
		}
	}
	
	public static void main(String[] args)
	{
		QA qa = new QA();
		qa.uruchomAlgorytm();
	}
	
	public void uruchomAlgorytm()
	{
		this.wprowadzPytanie();
		this.rzucWGoogle();
		// TODO: wyszukiwanie form fleksyjnych kaĹĽdego ze sĹ‚Ăłw
		// TODO: uzyskanie synonimĂłw przymiotnikĂłw
		
		// TODO: wyszukanie
	}
	
	public void wprowadzPytanie()
	{
		QA.sopln("Pytanie: ");
		this.pytanie = QA.input.nextLine();
	    QA.sopln(this.pytanie);
		this.przetworzPytanie();
	}
	
	public static void sop(String str)
	{
		System.out.print(str);
	}
	
	public static void sopln(String str)
	{
		System.out.println(str);
	}
	
	private static String usunPolskieZnaki(String slowo)
	{
		String[] pz = {};
		String az = "ęóąśłżźćńĘÓĄŚŁŻŹĆŃ";
//		String az = "eoaslzzcnEOASLZZCN";
		for (int i = 0; i < pz.length; ++i)
		{
			slowo = slowo.replaceAll(pz[i], String.valueOf(az.charAt(i)));
		}
		return slowo;
	}
	
	/**
	 * zwraca nowe sĹ‚owo stworzone na podstawie parametru z ~ zaznaczajÄ…c polski znak.
	 * @param slowo
	 * @return String
	 */
	private static String polskieZnaki(String slowo)
	{
		String[] pz = {"Ă„â„˘", "Ä‚Ĺ‚", "Ă„â€¦", "Äąâ€ş", "Äąâ€š", "ÄąÄ˝", "ÄąĹź", "Ă„â€ˇ", "Äąâ€ž", "Ă„ďż˝", "Ä‚â€ś", "Ă„â€ž", "ÄąĹˇ", "Äąďż˝", "ÄąÂ»", "ÄąÄ…", "Ă„â€ ", "Äąďż˝"};
		String az = "eoaslzzcnEOASLZZCN";
		for (int i = 0; i < pz.length; ++i)
		{
		    String chr = String.valueOf(az.charAt(i));
			slowo = slowo.replaceAll(pz[i], String.valueOf(az.charAt(i))+'~');
		}
		return slowo;
	}
	//sl~owo:sl~ow;1:1-14;N134
	private void przetworzPytanie()
	{
		this.pytanie = QA.usunPolskieZnaki(this.pytanie);
		this.pytanie = this.pytanie.toLowerCase();
		Pattern wzorzec = Pattern.compile("([nN]aj[a-zA-Z]+[yea]) ([a-zA-Z\\ ]+) (we|w) ([a-zA-Z]+)");
		Matcher sekwencja = wzorzec.matcher(this.pytanie);
		if (sekwencja.find())
		{
			this.przymiotnik = sekwencja.group(1);
			this.X = sekwencja.group(2);
			this.przyimek = sekwencja.group(3);
			this.Y = sekwencja.group(4);
		}
	}
	
	private Vector<String> znajdzLinki(String HTMLdokument) 
	{
		Vector<String> linki = new Vector<String>();
		Pattern wzorzecLinka = Pattern.compile("<a href=\"(http://\\S+)\">");
		Matcher wynik = wzorzecLinka.matcher(HTMLdokument);
		while (wynik.find())
		{
			String link = wynik.group(1);
			try
			{
				new URI(link);
			}
			catch (URISyntaxException e)
			{
				System.exit(Error.ErrorLink);
			}
			if (!link.contains("onet"))
			{
				linki.add(link);
			}
		}
		return linki;
	}
	
}
