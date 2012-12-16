package QA;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QA
{
	public static Scanner input = new Scanner(System.in);
	
	private String pytanie = "";
	private String X;
	private String Y;
	
	private String przymiotnik;
	
	public QA()
	{
	}
	
	public void rzucWGoogle()
	{
		String fraza = this.przymiotnik + " " + this.X + " w " + this.Y;
		//String fraza = this.pytanie;
		fraza = fraza.toLowerCase();
		try
		{
			fraza = URLEncoder.encode(fraza, "UTF-8");
			String HTMLdokument = Sieć.Google.zapytajNStron(fraza, 10);
			QA.sopln(HTMLdokument);
			Vector<String> links = this.znajdzLinki(HTMLdokument);
			for (String link : links)
			{
				QA.sopln(link);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			QA.sopln("niewspierane kodowanie");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		QA qa = new QA();
		qa.pytanie ="praca domowa";
		qa.rzucWGoogle();
		
	}
	
	public void uruchomAlgorytm()
	{
		// TODO: usuwanie znaków interpunkcyjnych w this.pytanie
		// TODO: podział this.pytania na słowa
		// TODO: wyszukiwanie form fleksyjnych każdego ze słów
		// TODO: uzyskanie synonimów przymiotników
		// TODO: wyszukanie
	}
	
	public void wprowadzPytanie()
	{
		QA.sopln("Pytanie: ");
		this.pytanie = QA.input.nextLine();
		this.przetworzPytanie();
	}
	
	public static void sop(String str)
	{
		System.out.print(str);
	}
	
	public static void sopln(String p)
	{
		System.out.println(p);
	}
	
	private static String usunPolskieZnaki(String slowo)
	{
		String[] pz = {"Ä™", "Ăł", "Ä…", "Ĺ›", "Ĺ‚", "ĹĽ", "Ĺş", "Ä‡", "Ĺ„", "Ä�", "Ă“", "Ä„", "Ĺš", "Ĺ�", "Ĺ»", "Ĺą", "Ä†", "Ĺ�"};
		String az = "eoaslzzcnEOASLZZCN";
		//QA.sopln(az.toUpperCase());
		for (int i = 0; i < pz.length; ++i)
		{
			slowo = slowo.replaceAll(pz[i], String.valueOf(az.charAt(i)));
		}
		return slowo;
	}
	
	private void przetworzPytanie()
	{
		this.pytanie = QA.usunPolskieZnaki(this.pytanie);
		this.pytanie = this.pytanie.toLowerCase();
		Pattern wzorzec = Pattern.compile("([nN]ajwieksz[yea]) ([a-zA-Z]+) w ([a-zA-Z]+)");
		Matcher sekwencja = wzorzec.matcher(this.pytanie);
		if (sekwencja.find())
		{
			this.przymiotnik = sekwencja.group(1);
			this.X = sekwencja.group(2);
			this.Y = sekwencja.group(3);
		}
		
	}
	
	private Vector<String> znajdzLinki(String HTMLdokument)
	{
		Vector<String> linki = new Vector<String>();
		Pattern wzorzecLinka = Pattern.compile("<a href=\"(http://\\S+)\">");// [a-zA-Z]+\">");
		Matcher wynik = wzorzecLinka.matcher(HTMLdokument);
		while (wynik.find())
		{
			String link = wynik.group(1);
			try
			{
				URI uri = new URI(link);
			}
			catch (URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				QA.sopln("Nie zidentyfikować link!");
			}
			if (!link.contains("onet"))
			{
				linki.add(link);
			}
		}
		return linki;
	}
	
}
