package polskieZnaki;

public final class PolskieZnaki
{
	private static final String polskieZnaki = "ęóąśłżźćńĘÓĄŚŁŻŹĆŃ";
	
	private static final String angielskieZnaki = "eoaslzzcnEOASLZZCN";
	/**
	 * deletes polish chars
	 * @param slowo
	 * @return new word
	 */
	public static String zastap(String slowo)
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
	 * przygotowuję słowo tak, by każdy polski znak miał następujący po nim
	 * tyldę dla znaków (ęóąśłżćń) lub dwie tyldy (~~) dla ź.
	 * @param slowo - słowo na którym działam
	 * @return nowe słowo
	 */
	
	public static String zamienNaTyldy(String slowo)
	{
		String pz = PolskieZnaki.polskieZnaki;
		String az = PolskieZnaki.angielskieZnaki;
		for (int i = 0; i < pz.length(); ++i)
		{
			char chr = pz.charAt(i);
			if (chr == 'ź' || chr == 'Ź')
			{
				slowo = slowo.replace(String.valueOf(pz.charAt(i)), az.charAt(i)+"~~");
			}
			else
			{
				slowo = slowo.replaceAll(String.valueOf(pz.charAt(i)), String.valueOf(az.charAt(i))+'~');
			}
		}
		return slowo;
	}
}
