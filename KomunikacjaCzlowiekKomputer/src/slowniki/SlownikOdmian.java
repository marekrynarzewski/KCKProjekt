package slowniki;

import java.io.IOException;
import java.net.URL;

import siec.Filex;

public class SlownikOdmian 
{
    private static final String adres = "http://odmiana.net/odmiana-przez-przypadki/";
    
    public static String odmien(String slowo, String przypadek)
    {
        String adres = SlownikOdmian.adres+slowo;
        try 
        {
            String HTML = Filex.pobierzZUrla(adres);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
