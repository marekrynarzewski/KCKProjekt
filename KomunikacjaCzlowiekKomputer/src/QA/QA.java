package qa;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import arry.Arrays;

//owns imports
import siec.Filex;

import logger.Logger;

import exception.Error;

public class QA
{
	public static Scanner wejscie = new Scanner(System.in);
	
	private String pytanie = "";
	private String X = "";
	private String Y = "";
	private String przyimek = "";
	private String przymiotnik = "";
	private String fraza = "";
	
	private Vector<String> linki;
		
	private TreeMap<String, Integer> losk = new TreeMap<String, Integer>();
	
	private String[] elementyFrazy;
	private String[] akceptowaneTypyPlikow = {"html", "txt"};
	
	private int errorsCount = 0;
	private String[] przyimki = {"z", "do", "na", "bez", "za", "pod", "u", "w", "nad", "od", "po"};
	private String[] linkiZOnetu = {
			"http://boksy.onet.pl/",
			"http://www.onet.pl",
			"http://m.onet.pl",
			"http://biznes.onet.pl",
			"http://wiadomosci.onet.pl",
			"http://poczta.onet.pl",
			"http://www.onet.pl/wszystkie/",
			"http://www.onet.pl/prywatnosc/",
			"http://natemat.onet.pl/",
			"http://secure.onet.pl/logowanie.html?app_id=65&amp;url=zarzadzaniesg.html"
	};
	
	public static void main(String[] args)
	{
		QA qa = new QA();
		qa.uruchomAlgorytm();
	}
	
	/**
	 * skraca dostęp do System.out.print()
	 * @param str - wiadomość dla użytkownika
	 */
	public static void sop(String str)
	{
		System.out.print(str);
	}
	
	/**
	 * skraca dostęp do  System.out.println()
	 * @param str - wiadomość dla użytkownika
	 */
	public static void sopln(String str)
	{
		System.out.println(str);
	}
	
	/**
	 * kończy program z kodem wysyłając wiadomość
	 * @param message - wiadomość dla użytkownika
	 * @param noError
	 */
	public static void exit(String message, int noError)
	{
		QA.sopln("[ERROR] " + message);
		System.exit(noError);
	}
	
	/**
	 * ostrzega użytkownika wysyłając wiadomość na ekran
	 * @param message - wiadomość dla użytkownika
	 */
	public static void warn(String message)
	{
		QA.sopln("[WARNING] " + message);
	}
	
	/**
	 * informuje użytkownika wysyłając wiadomość na ekran
	 * @param message wiadomość dla użytkownika
	 */
	public static void info(String message)
	{
		QA.sopln("[INFO] " + message);
	}
	
	/**
	 * uruchamia algorytm
	 */
	public void uruchomAlgorytm()
	{
		this.przywitajSie();
		this.wprowadzPytanie();
		if (this.rzucWGoogle())
		{
			QA.info("Pomyślnie zapytałem Googla.");
			int idx = 0;
			int procent = 0;
			for (String link : this.linki)
			{
				idx = this.informujIPrzetwarzaj(idx, link, procent);
			}
			QA.info("Przetworzono 100% linków.");
			QA.warn(this.errorsCount + " linków nie udało się pobrać.");
			if (Filex.mapaDoPliku("mapa.ini", this.losk) > 0)
			{
				QA.info("Zapisałem do pliku mapa.ini mapę.");
			}
		}
	}

	private int informujIPrzetwarzaj(int idx, String link, int procent)
	{
		Logger.log("Przetwarzam link '" + link + "'");
		procent = (idx*100)/this.linki.size();
		if (procent % 5 == 0)
			QA.info("Przetworzono "+procent+"% linków");
		this.przetwarzaj(link);
		++idx;
		return idx;
	}
	
	private static boolean isNumeric(String str)  
	{
		try
		{
			Double.parseDouble(str);
		}
		catch (NumberFormatException nfe)
		{
			return false;  
	    }  
	    return true;  
	  }

	private void przywitajSie()
	{
		QA.sopln("Program do odpowiadania\nna pytanie na podstawie wyników z Internetu.");
	}
	/**
	 * wysyła zapytanie do {siec.Google.SzukajOnet} frazę zbudowaną
	 * z przymiotnika, rzeczownika (X), przyimka i rzeczownika (Y)
	 * @return prawdę jeśli dowolny element frazy nie jest nullem
	 */
	private boolean rzucWGoogle()
	{
		this.fraza = this.przymiotnik + " " + this.X + " " + this.przyimek + " " + this.Y;
		QA.sopln("Przygotowuję do wysłania frazę: "+this.fraza);
		String[] elementyFrazy = { this.przymiotnik, this.X, this.przyimek, this.Y };
		this.elementyFrazy = elementyFrazy;
		this.linki = new Vector<String>();
		if (!arry.Arrays.hasNullable(this.elementyFrazy))
		{
			this.fraza = this.fraza.toLowerCase();
			this.linki.addAll(siec.Google.zapytajNStron(this.fraza, 10));
			QA.sopln("Znalazłem "+this.linki.size()+" linków");
			Logger.log("Znalazłem " + this.linki.size() + " linków dla frazy '" + this.fraza + "'.");
			return true;
		}
		return false;
	}
	
	/**
	 * prosi użytkownika o podanie pytania i je przetwarza.
	 */
	private void wprowadzPytanie()
	{
		QA.sopln("Pytanie: ");
		this.pytanie = QA.wejscie.nextLine();
		this.przetworzPytanie();
	}
	
	/**
	 * przetwarza pytania wyrażeniami regularnymi.
	 */
	private void przetworzPytanie()
	{
		QA.info("Przetwarzam pytanie");
		// TODO: polskie znaki w pytaniu
		// this.pytanie = PolskieZnaki.zastap(this.pytanie);
		this.pytanie = this.pytanie.toLowerCase();
		Pattern wzorzec = Pattern.compile("([nN]aj[a-zA-Z]+[yea]) ([a-zA-Z\\ ]+) (we|w|z|na) ([a-zA-Z\\ ]+)");
		Matcher sekwencja = wzorzec.matcher(this.pytanie);
		if (sekwencja.find())
		{
			this.przymiotnik = sekwencja.group(1);
			this.X = sekwencja.group(2);
			this.przyimek = sekwencja.group(3);
			this.Y = sekwencja.group(4);
			Logger.log("przymiotnik = '" + this.przymiotnik + "'.");
			Logger.log("X = '" + this.X + "'.");
			Logger.log("przyimek = '" + this.przyimek + "'.");
			Logger.log("Y = '" + this.Y + "'.");
			Logger.log("\n");
			QA.info("Pozytywny wynik przetwarzania.");
		}
		else
		{
			Logger.log("Nie udało mi się przetworzyć pytania '" + this.pytanie + "'.");
			QA.exit("Negatywny wynik przetwarzania.", Error.ProcessingQuestion);
		}
	}

	private int czyZawieraElementFrazy(Vector<String> linki, int refusedLinks, String link)
	{
		if (Arrays.contains(this.linkiZOnetu, link))
		{
			return refusedLinks;
		}
		int zawieraElementowFrazy = 0;
		for (String elementFrazy : this.elementyFrazy)
		{
			if (link.contains(elementFrazy) && elementFrazy != this.przyimek)
			{
				++zawieraElementowFrazy;
			}
		}
		if (zawieraElementowFrazy >= 1)
		{
			linki.add(link);
		}
		else
		{
			Logger.log("Odrzuciłem link '" + link + "'.");
			++refusedLinks;
		}
		return refusedLinks;
	}
	
	private boolean rozszerzeniePliku(String nazwaPliku)
	{
		String typPliku = Filex.getContentType(nazwaPliku);
		Logger.log("Link '"+nazwaPliku+"' jest typu: "+typPliku);
		for (String rozszerzenie : this.akceptowaneTypyPlikow)
		{
			if (typPliku.contains(rozszerzenie))
			{
				
				return true;
			}
		}
		return false;
	}

	/**
	 * przetwarza poszczególny URL.
	 * @param url - adres www strony lub pliku.
	 */
	private void przetwarzaj(String url)
	{
		String content;
		try
		{
			content = Filex.pobierzZUrla(url);
			content = siec.Google.convertHTMLtoTXT(content);
			Vector<String> listaOtoczenia = pobierzOtoczenieDlaWszystkichElementowFrazy(content, 30);
			this.dodajDoLosk(listaOtoczenia);
		}
		catch (IOException e)
		{
			Logger.log("Nie udało mi się pobrać linku z URLa '" + url + "'.");
			Logger.log("Message "+e.getMessage());
			++this.errorsCount;
		}
	}
	
	/**
	 * dodaje wektor slow do drzewa czerwonoczarnego zliczając ilość wystąpień.
	 * @param listaOtoczenia - wektor słów do dodania
	 */
	private void dodajDoLosk(Vector<String> listaOtoczenia)
	{
		for (String otoczenie : listaOtoczenia)
		{
			otoczenie = otoczenie.toLowerCase();
			if (!QA.isNumeric(otoczenie) && !Arrays.contains(this.przyimki, otoczenie))
			{
				if (!this.losk.containsKey(otoczenie))
				{
					this.losk.put(otoczenie, 1);
				}
				else
				{
					this.losk.put(otoczenie, (Integer)this.losk.get(otoczenie) + 1);
				}
			}
		}
	}
	
	/**
	 * pobiera otoczenie słów w pobliżu słowa slowo w wejscie'u w otoczeniu
	 * ileWyrazow.
	 * @param wejscie - slowo, w którym pobieramy otoczenie.
	 * @param slowo - będziemy szukać otoczenia tego słowa.
	 * @param ileWyrazow - tyle wyrazów znajdujących się przed i po slowo będziemy
	 *            uznawali za jego otoczenie.
	 * @return Wektor Stringów zawierających znalezione słowa otoczenia
	 */
	@SuppressWarnings("unused")
	private Vector<String> pobierzOtoczenie(String wejscie, String slowo, int ileWyrazow)
	{
		Vector<String> rezultat = new Vector<String>();
		Pattern wzorzec = Pattern.compile("([\\węóąśłżźćńĘÓĄŚŁŻŹĆŃ]+\\s*){0," + ileWyrazow + "}\\s+" + slowo + "\\s+([\\węóąśłżźćńĘÓĄŚŁŻŹĆŃ]+\\s*){0," + ileWyrazow + "}");
		Matcher wyniki = wzorzec.matcher(wejscie);
		while (wyniki.find())
		{
			for (int i = 1; i <= wyniki.groupCount(); ++i)
			{
				String[] slowa = wyniki.group(i).split(" ");
				for (String word:slowa)
				{
					rezultat.add(word);
				}
				Logger.log("Znalazłem "+rezultat.size()+" słów.");
			}
		}
		return rezultat;
	}
	
	/**
	 * pobiera otoczenie dla słowo slowo w słowie wejscie 
	 * znajdujących nie dalej niż ileWyrazów przed i po 
	 * @param wejscie - słowo wejściowe.
	 * @param slowo - szukamy otoczenia dla tego słowa.
	 * @param ileWyrazow - tyle wyrazów określa otoczenie.
	 * @return
	 */
	private Vector<String> pobierzOtoczenieAlternatywnie(String wejscie, String slowo, int ileWyrazow)
	{
		Vector<String> rezultat = new Vector<String>();
		String[] slowa = wejscie.split(" ");
		Vector<Integer> allPositions = Arrays.findAll(slowa, slowo);
		Vector<String> toVector = Arrays.toVector(slowa);
		for (Integer s : allPositions)
		{
			int fromIndex = s - ileWyrazow;
			if (fromIndex < 0)
			{
				fromIndex = 0;
			}
			int toIndex = s + ileWyrazow;
			if (toIndex > toVector.size())
			{
				toIndex = toVector.size();
			}
			List<String> podRezultat = toVector.subList(fromIndex, toIndex);
			for (String podWynik : podRezultat)
			{
				if (!slowo.equals(podWynik) && !rezultat.contains(podWynik))
				{
					rezultat.add(podWynik);
				}
			}
		}
		return rezultat;
	}
	
	/**
	 * wykonuje metody pobierzOtoczenie()
	 * lub pobierzOtoczenieAlternatywnie()
	 * dla każdego z elementu frazy.
	 * @param wejscie - słowo wejściowe
	 * @param ileWyrazow - określa ile wyrazów wchodzi w otoczenie
	 * @return wektor słów będących sumą unikalną otoczeń dla każdego
	 * z elementu frazy.
	 */
	private Vector<String> pobierzOtoczenieDlaWszystkichElementowFrazy(String wejscie, int ileWyrazow)
	{
		Vector<String> rezultat = new Vector<String>();
		for (String elementFrazy : this.elementyFrazy)
		{
			Vector<String> podRezultat = this.pobierzOtoczenieAlternatywnie(wejscie, elementFrazy, ileWyrazow);
			for (String podWynik : podRezultat)
			{
				if (!rezultat.contains(podWynik))
				{
					rezultat.add(podWynik);
				}
			}
		}
		return rezultat;
	}
	
	private static Map sortByValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	              .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 
	
	private void sortowaniePrzezZliczanie(Map<?, Integer> mapa)
	{
	}
}
