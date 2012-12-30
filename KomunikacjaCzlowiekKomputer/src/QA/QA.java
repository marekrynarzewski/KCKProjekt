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

//owns imports
import siec.Filex;

import logger.Logger;

import exception.Error;

public class QA
{
	public static Scanner wejscie = new Scanner(System.in);
	
	private String pytanie = "";
	private String X;
	private String Y;
	private String przyimek;
	private String przymiotnik;
	
	private Vector<String> linki;
	
	private String fraza;
	
	private TreeMap<String, Integer> losk = new TreeMap<String, Integer>();
	
	private String[] elementyFrazy;
	private String[] akceptowaneTypyPlikow = {"html", "txt"};
	
	private int errorsCount = 0;
	
	public static void main(String[] args) throws IOException
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
		this.wprowadzPytanie();
		if (this.rzucWGoogle())
		{
			QA.info("Pomyślnie zapytałem Googla.");
			Logger.log("Przetwarzam linki");
			int idx = 0;
			for (String link : this.linki)
			{
				Logger.log("Przetwarzam link '" + link + "'");
				QA.info("Przetwarzam "+idx+". na "+this.linki.size()+" link");
				this.przetwarzaj(link);
				++idx;
				
			}
			QA.warn(this.errorsCount + " linków nie udało się pobrać.");
			//NavigableMap<String, Integer> nm = this.losk.descendingMap();
			//Debug.debugMap(nm);
			//this.sortujMape(losk);
		}
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
		if (!arry.Arrays.hasNullable(this.elementyFrazy))
		{
			this.fraza = this.fraza.toLowerCase();
			String HTMLdokument = siec.Google.zapytajNStron(this.fraza, 10);
			this.linki = this.znajdzLinki(HTMLdokument);
			QA.sopln("Znalazłem "+this.linki.size()+" linków");
			Logger.log("Znalazłem " + this.linki.size() + " linków dla frazy '" + this.fraza + "'.");
			return true;
		}
		Logger.log("Fraza '" + this.fraza + "' zawiera null.");
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
			QA.info("Pozytywny wynik przetwarzania.");
		}
		else
		{
			Logger.log("Nie udało mi się przetworzyć pytania '" + this.pytanie + "'.");
			QA.exit("Negatywny wynik przetwarzania.", Error.ProcessingQuestion);
		}
	}
	/**
	 * wyszukuje linki w parametrze dokument.
	 * @param dokument - text HTMLa zwrócony przez wyszukiwarkę
	 * @return wektor linków będących Stringami.
	 */
	private Vector<String> znajdzLinki(String dokument)
	{
		Vector<String> linki = new Vector<String>();
		dokument = siec.Google.zwrocDivaZWynikami(dokument);
		Pattern wzorzecLinka = Pattern.compile("<a href=\"(http://\\S+)\">");
		Matcher wynik = wzorzecLinka.matcher(dokument);
		int refusedLinks = 0;
		while (wynik.find())
		{
			String link = wynik.group(1);
			try
			{
				new URI(link);
			}
			catch (URISyntaxException e)
			{
				Logger.log("Link " + link + " zawiera błąd. Szczegóły: " + e.getMessage() + ".");
				QA.warn("Błąd w linku '" + link + "'.");
			}
			if (!link.contains("onet"))
			{
				linki.add(link);
			}
			else
			{
				//TODO: inteligentne odrzucanie linków.
				Logger.log("Odrzuciłem link '" + link + "'.");
				++refusedLinks;
			}
		}
		if (refusedLinks > 0)
		{
			QA.warn("Odrzuciłem "+refusedLinks+" linków.");
		}
		return linki;
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
			Vector<String> listaOtoczenia = pobierzOtoczenieDlaWszystkichElementowFrazy(content, 50);
			this.dodajDoLosk(listaOtoczenia);
		}
		catch (IOException e)
		{
			Logger.log("Nie udało mi się pobrać linku z URLa '" + url + "'.");
			Logger.log("Szczegóły: CallStack: "+e.getStackTrace()+", Message "+e.getMessage());
			++this.errorsCount;
			e.printStackTrace();
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
		Vector<Integer> allPositions = arry.Arrays.findAll(slowa, slowo);
		Vector<String> toVector = arry.Arrays.toVector(slowa);
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
				if (!rezultat.contains(podWynik))
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
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private void sortujMape(Hashtable<?, Integer> t)
	{
		ArrayList<Map.Entry<?, Integer>> l = new ArrayList(t.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<?, Integer>>()
		{
			public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2)
			{
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		System.out.println(l);
	}
	
	private boolean odpowiednieRozszerzeniePliku(String link)
	{
		for (String sufiks : this.akceptowaneTypyPlikow)
		{
			if (!link.endsWith(sufiks))
			{
				return false;
			}
		}
		return true;
	}
	private boolean odrzucLink(String link)
	{
		if (!this.odpowiednieRozszerzeniePliku(link))
		{
			//siec.Google.
		}
		return false;
	}
	
	public static Map<String,Integer> sortByComparator(Map<String,Integer> unsortMap) {

	    List list = new LinkedList(unsortMap.entrySet());

	    //sort list based on comparator
	    Collections.sort(list, new Comparator() {
	        public int compare(Object o1, Object o2) {
	            return ((Comparable) ((Map.Entry) (o2)).getValue())
	                    .compareTo(((Map.Entry) (o1)).getValue());
	        }
	    });

	    //put sorted list into map again
	    Map sortedMap = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }
	    return sortedMap;
	}   
}
