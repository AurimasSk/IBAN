package lt.aurimas.iban;

import lt.aurimas.iban.service.AbstractIbanService;
import lt.aurimas.iban.service.LTIbanService;


/**
 *  Factory to get concrete IBAN service according to IBAN country code
 */
public class IbanServiceFactory {

	public static AbstractIbanService getIbanService(String ibanNumber) {
		if (ibanNumber.isEmpty())
			return null;
		String countryCode = getIBANCountryCode(ibanNumber);
		
		switch (countryCode) {
			case "LT" : return new LTIbanService();
			default : return null;
		}
	}
	
	private static String getIBANCountryCode(String ibanNumber) {
		return ibanNumber.substring(0, 2);
	}
}
