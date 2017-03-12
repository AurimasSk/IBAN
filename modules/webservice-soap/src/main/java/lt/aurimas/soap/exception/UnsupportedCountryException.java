package lt.aurimas.soap.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "UnsupportedCountryFault")
public class UnsupportedCountryException extends Exception {
	 
    private static final String ERROR = "ERROR";
    
    private UnsupportedCountryFault fault;
 
    public UnsupportedCountryException(UnsupportedCountryFault fault) {
        this(ERROR, fault);
    }

    public UnsupportedCountryException(String errorMessage, UnsupportedCountryFault fault) {
    	super(errorMessage);
        this.fault = fault;
        System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false");
    }
    
	public UnsupportedCountryFault getFaultInfo() {
        return fault;
    }
 
}
