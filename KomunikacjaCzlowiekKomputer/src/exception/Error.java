package exception;

public class Error
{
	/**
	 * określa błąd w kodowaniu
	 */
	public static final int ErrorEncoding = -2;
	
	/**
	 * określa nieznany host
	 */
	public static final int ErrorHost = -3;
	
	/**
	 * określa błąd wejścia i wyjścia
	 */
	public static final int ErrorIO = -4;
	
	/**
	 * określa błąd w linku
	 */
	public static final int ErrorInUrl = -5;
	
	/**
	 * określa błąd podczas przetwarzania pytania.
	 */
	public static final int ProcessingQuestion = -6;
}
