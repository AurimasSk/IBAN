package lt.aurimas.soap.exception;

public class UnsupportedCountryFault {

	public static final String UNSUPPORTED_IBAN_COUNTRY = "Unsupported country code for iban %s";
	public static final String CLIENT_ERROR_CODE = "SOAP-ENV:Client";
	
	private String faultCode;
    private String faultString;
    
    public UnsupportedCountryFault() {    	
    }
    
    public UnsupportedCountryFault (String faultCode, String faultString) {
    	this.faultCode = faultCode;
    	this.faultString = faultString;
    }

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultString() {
		return faultString;
	}

	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	
}
