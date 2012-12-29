package arry;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;

public class Arrays
{
	/**
	 * sprawdza czy argument ma null 
	 * @param array - testowana tablica
	 * @return prawdę jeśli ma.
	 */
	public static <T> boolean hasNullable(T[] array)
	{
		for (T item : array)
		{
			if (item == null)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * znajduje najniższy indeks w tablicy, który = slowo
	 * @param array - tablica
	 * @param item - szukana wartosc
	 * @return
	 */
	public static <T> int findFirst(T[] array, T item)
	{
		int idx = 0;
		for (T s : array)
		{
			if (s.equals(item))
			{
				return idx;
			}
			++idx;
		}
		return -1;
	}
	
	/**
	 * znajduje wszystkie indeksy w tablicy które równają się item
	 * @param array - tablica
	 * @param item - szukana wartość
	 * @return wektor indeksów
	 */
	public static <T> Vector<Integer> findAll(T[] array, T item)
	{
		Vector<Integer> rezultat = new Vector<Integer>();
		int idx = 0;
		for (T s : array)
		{
			if (s.equals(item))
			{
				rezultat.add(new Integer(idx));
			}
			++idx;
		}
		return rezultat;
	}

	/**
	 * przekształca tablicę na wektor
	 * @param slowa - tablica
	 * @return wektor
	 */
	public static <T> Vector<T> toVector(T[] slowa)
	{
		Vector<T> rezultat = new Vector<T>();
		for (T item : slowa)
		{
			rezultat.add(item);
		}
		return rezultat;
	}
}
	

