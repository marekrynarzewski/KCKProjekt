package qa;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.Logger;
import logger.LoggerError;

import exception.Error;

public class QA
{
	public static Scanner input = new Scanner(System.in);
	private static Logger logger = new Logger();
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
		QA.logger.log("Buduję frazę "+fraza);
		fraza = fraza.toLowerCase();
		QA.logger.log("lowerCase of fraza is \""+fraza+"\"");
		try
		{
			fraza = URLEncoder.encode(fraza, "UTF-8");
			QA.logger.log("koduję frazę w UTF8");
			String HTMLdokument = siec.Google.zapytajNStron(fraza, 10);
			QA.logger.log("Pytam 10 stron o frazę");
			Vector<String> links = this.znajdzLinki(HTMLdokument);
			QA.logger.log("Szukam linkow w znalezionym dokumenci");
			QA.logger.log("Znalezione linki:");
			for (String link : links)
			{
				QA.sopln(link);
				QA.logger.log(link);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			QA.sopln("niewspierane kodowanie");
			logger.log("Błąd: Niewspieranie logowanie");
			System.exit(Error.ErrorEncoding);
		}
	}
	
	public static void main(String[] args)
	{
		//Logger logger = new Logger();
		//FileUtils.writeStringToFile(Logger.getLogfile());
		/*
		QA qa = new QA();
		QA.logger.log("Utworzylem nowy obiekt QA");
		qa.wprowadzPytanie();
		QA.logger.log("Odczytałem pytanie");
		qa.rzucWGoogle();
		QA.logger.log("rzuciłem w Google");
		*/
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
		QA.logger.log("Pytanie = \""+pytanie+"\"");
		this.przetworzPytanie();
		QA.logger.log("Przetwarzam pytanie");
		QA.logger.log("Wyniki");
		QA.logger.log("przymiotnik = "+this.przymiotnik);
		QA.logger.log("X = "+this.X);
		QA.logger.log("Y = "+this.Y);
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
		String[] pz = {"Ä™", "Ăł", "Ä…", "Ĺ›", "Ĺ‚", "ĹĽ", "Ĺş", "Ä‡", "Ĺ„", "Ä�", "Ă“", "Ä„", "Ĺš", "Ĺ�", "Ĺ»", "Ĺą", "Ä†", "Ĺ�"};
		String az = "eoaslzzcnEOASLZZCN";
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
		Pattern wzorzec = Pattern.compile("([nN]aj[a-zA-Z]+[yea]) ([a-zA-Z]+) w ([a-zA-Z]+)");
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
				QA.sopln("Nie zidentyfikowany link!");
				QA.logger.log("Błąd: Niezidentyfikowany link");
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
