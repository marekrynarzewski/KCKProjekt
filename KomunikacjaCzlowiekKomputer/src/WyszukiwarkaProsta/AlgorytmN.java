package WyszukiwarkaProsta;
import java.util.Vector;

public class AlgorytmN
{
	public String a = new String("baba");
	
	public static Vector<Integer> AlgorytmNaiwny(String wzorzec, String tekst)
	{
		int m, n, i, j;
		Vector<Integer> pozycje = new Vector<Integer>();
		n = tekst.length();
		m = wzorzec.length();
		i = 0;
		while (i <= n - m)
		{
			j = 0;
			while ((j < m) && (wzorzec.charAt(j) == tekst.charAt(i + j)))
				j++;
			if (j == m)
				pozycje.add(i + 1);
			i++;
		}
		return pozycje;
	}
	
	public static void main(String[] args)
	{
		int i = 0;
		zwieksz(i);
		System.out.println("i = "+i);
		AlgorytmN an = new AlgorytmN();
		dodaj(an.a);
		System.out.println("a2 = "+an.a);
	}

	private static void dodaj(String a2)
	{
		a2 += 'a';
		System.out.println("a2 = "+a2);
	}

	private static void zwieksz(int i)
	{
		i++;
		System.out.println("i = "+i);		
	}
}
