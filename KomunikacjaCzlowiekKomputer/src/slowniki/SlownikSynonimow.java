package slowniki;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import qa.QA;

import siec.Filex;

public class SlownikSynonimow
{
	private static final String slownikFile = "slownikSynonimow.txt";
	private List<String> dictionary;
	
	public SlownikSynonimow() throws FileNotFoundException, IOException
	{
		Vector<String> content = Filex.plikDoTablicy(SlownikSynonimow.slownikFile);
		this.dictionary = content.subList(8, content.size());
	}
	
	public boolean hasSynonyms(String word)
	{
		for (String line : this.dictionary)
		{
			//line;
		}
		return false;
	}
}
