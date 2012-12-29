package qa;

import java.util.Map;
import java.util.Map.Entry;

final class Debug
{
	/**
	 * wypisuje zawartość mapy mapę
	 * @param mapa - mapa do debugowania
	 */
	public static <T, K> void debugMap(Map<T, K> mapa)
	{
		for (Entry<T, K> entry : mapa.entrySet())
		{
			QA.sopln(entry.getKey()+", "+entry.getValue());
		}
	}
}
