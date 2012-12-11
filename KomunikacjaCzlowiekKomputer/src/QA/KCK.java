package QA;

import java.util.*;

public class KCK
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Twoje pytanie: ");
		String chain = sc.nextLine();
		
		StringTokenizer sTo = new StringTokenizer(chain); // StirngTokenizer
															// będzie rozbijać
															// nasz łańcuch na
															// podłańcuchy przez
															// uwzględnienie w
															// nim tokenów
		
		int noOfTokens = sTo.countTokens(); // noOfTokens - zmienna
											// przechowująca liczbę tokenów w
											// naszym łańcuchu
		
		String[] subchains = new String[noOfTokens]; // Tablica "długości"
														// równej liczbie
														// tokenów przechowująca
														// podłańcuchy łańcucha
		
		for (int i = 0; i < subchains.length; i++)
		{
			if (sTo.hasMoreTokens()) // Jeśli istnieją jakieś tokeny...
				subchains[i] = sTo.nextToken(); // ...to zapisujemy je do
												// tablicy
		}
		
		if (subchains.length > 6)
		{
			System.out.println(subchains[subchains.length - 4]);
			System.out.println(subchains[subchains.length - 2]);
			System.out.println(subchains[subchains.length - 1]);
		}
		
		if (subchains.length <= 6)
		{
			System.out.println(subchains[subchains.length - 3]);
			System.out.println(subchains[subchains.length - 1]);
		}
		
	}
}