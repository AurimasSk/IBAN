package lt.aurimas.iban.exception;

public class IbanException extends Exception {
	
	public static final String INVALID_IBAN_LENGTH = "IBAN length is invalid";
	public static final String CONTAINS_INVALID_CHARS = "IBAN number contains invalid chars";
	public static final String INVALID_IBAN_FORMAT = "IBAN number format is invalid";
	public static final String UNSUPPORTED_COUNTRY = "Unsupported country code";
	
	public IbanException(String message){
    	super(message);
    }
}
