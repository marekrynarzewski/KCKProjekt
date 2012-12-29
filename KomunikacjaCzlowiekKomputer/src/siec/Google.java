package siec;

import exception.Error;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qa.QA;


public class Google
{
	private static final String SzukajOnet = "http://szukaj.onet.pl/wyniki.html?qt=";
	private static final String SzukajOnetStrona =  "http://szukaj.onet.pl/0,{page},query.html?qt=";
	@SuppressWarnings("unused")
	private static final String GoogleAPI = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	private static final String EncodingUTF8 = "UTF-8";
	
	/**
	 * wysyła zapytanie do strony {SzukajOnet}
	 * @param pytanie - zapytanie do wysłania
	 * @return wynik lub pusty ciąg
	 */
	public static String zapytajGoogla(String pytanie)
	{
		try
		{
			String adres = Google.SzukajOnet+URLEncoder.encode(pytanie, Google.EncodingUTF8);
			return Filex.pobierzZUrla(adres);
		}
		catch (MalformedURLException e)
		{
			QA.warn("Link wywołał błąd: "+e.getMessage());
		}
		catch(UnknownHostException uhe)
		{
			QA.warn("Nieznany host lub Brak internetu");
		}
		catch (IOException e)
		{
			QA.warn("IOException");
		}
		
		return "";
	}
	
	/**
	 * wysyła zapytanie do konkretnej strony wyników
	 * @param pytanie 
	 * @param page - strona
	 * @return wynik
	 */
	public static String zapytajStrone(String pytanie, int page)
	{
		String adres = Google.SzukajOnetStrona;
		adres = adres.replaceFirst("\\{page\\}", String.valueOf(page));
		try
		{
			pytanie = URLEncoder.encode(pytanie, Google.EncodingUTF8);
			adres = adres+pytanie;
			try
			{
				return Filex.pobierzZUrla(adres);
			}
			catch (MalformedURLException e)
			{
				QA.warn("MalformedURLException");
			}
			catch (IOException e)
			{
				QA.warn("IOException");
			}
			
		}
		catch (UnsupportedEncodingException e1)
		{
			QA.warn("Unsupported Encoding");
		}
		
		return "";
	}
	
	/**
	 * wysyła zapytanie do wielu stron
	 * @param pytanie - zapytanie
	 * @param ileStron tyle pyta stron
	 * @return suma wyników ze wszystkich stron
	 */
	public static String zapytajNStron(String pytanie, int ileStron)
	{
		String result = "";
		for (int i = 0; i < ileStron; ++i)
		{
			result += Google.zapytajStrone(pytanie, i);
		}
		return result;
	}
	
	/**
	 * usuwa tagi html z pobranej strony prostą metodą tj. z wnętrznościami
	 * @param inp - wejściowy łańcuch
	 * @return wynikowy łańcuch
	 */
	public static String stripTagsSimple(String inp)
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
		            outp = outp + inp.charAt(i);
		        }
		}   
		return outp;
	}
	
	/**
	 * usuwa tagi html wraz zawartością dla np. script lub style
	 * @param inp - kod strony
	 * @return tekst bez tagów
	 */
	public static String stripTagsAll(String inp)
	{
		String[] regexs = {"(?siu)<head[^>]*?>.*?</head>",
		"(?siu)<style[^>]*?>.*?</style>",
		"(?siu)<script[^>]*?.*?</script>",
		"(?siu)<object[^>]*?.*?</object>",
		"(?siu)<embed[^>]*?.*?</embed>",
		"(?siu)<applet[^>]*?.*?</applet>",
		"(?siu)<noframes[^>]*?.*?</noframes>",
		"(?siu)<noscript[^>]*?.*?</noscript>",
		"(?siu)<noembed[^>]*?.*?</noembed>",
		// Add line breaks before and after blocks
		"(?iu)</?((address)|(blockquote)|(center)|(del))",
		"(?iu)</?((div)|(h[1-9])|(ins)|(isindex)|(p)|(pre))",
		"(?iu)</?((dir)|(dl)|(dt)|(dd)|(li)|(menu)|(ol)|(ul))",
		"(?iu)</?((table)|(th)|(td)|(caption))",
		"(?iu)</?((form)|(button)|(fieldset)|(legend)|(input))",
		"(?iu)</?((label)|(select)|(optgroup)|(option)|(textarea))",
		"(?iu)</?((frameset)|(frame)|(iframe))",
		"(?iu)</?((span)|(br))"};
		int idx = 0;
		for (String regex : regexs)
		{
			if (idx > 8)
			{
				inp = inp.replaceAll(regex, "$0");
			}
			else
			{
				inp = inp.replaceAll(regex, " ");
			}
			idx ++;	
		}
		return Google.stripTagsSimple(inp);
	}
	
	/**
	 * konwertuje kod strony (inputString) do zwykłego tekstu
	 * pozbawiając go tagów, znaków interpunkcyjnych i białych
	 * znaków pozostawiając pojedyncze spacje.
	 * @param inputString wejściowy
	 * @return String
	 */
	public static String convertHTMLtoTXT(String inputString)
	{
		inputString = inputString.replace("><", "> <");
		inputString = inputString.replace("&nbsp;", " ");
		inputString = Google.stripTagsAll(inputString);
		inputString = inputString.replaceAll("\\p{Punct}", " ");
		inputString = inputString.replaceAll("\\s\\s+", " ");
		return inputString;
	}
	
	/**
	 * zwraca diva z wynikami. Działa tylko dla {siec.Google.SzukajOnet}
	 * @param dokument
	 * @return
	 */
	public static String zwrocDivaZWynikami(String dokument)
	{
		Pattern wzorzecLinka = Pattern.compile("<div class=\"boxResult2\">(.*)</div>");
		Matcher wynik = wzorzecLinka.matcher(dokument);
		if (wynik.find())
		{
			return wynik.group(1);
		}
		return null;
	}
	
}