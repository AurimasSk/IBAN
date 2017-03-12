package lt.aurimas.soap;

import javax.jws.WebService;

import lt.aurimas.iban.IbanResponseDTO;
import lt.aurimas.iban.IbanServiceFactory;
import lt.aurimas.iban.exception.IbanException;
import lt.aurimas.iban.service.AbstractIbanService;
import lt.aurimas.soap.exception.UnsupportedCountryException;
import lt.aurimas.soap.exception.UnsupportedCountryFault;

@WebService(endpointInterface = "lt.aurimas.soap.IbanService")
public class IbanServiceImpl implements IbanService {
	
	@Override
	public IbanResponseDTO validateIban(String ibanNumber) throws UnsupportedCountryException {
		AbstractIbanService ibanService = IbanServiceFactory.getIbanService(ibanNumber);
		if (ibanService == null) {
			throw createUnsupportedCountryException(ibanNumber);
		}
		try {
			ibanService.ibanValidation(ibanNumber);
		} catch (IbanException ibanException) {
			return new IbanResponseDTO(ibanNumber, false, ibanException.getMessage());
		}
		return new IbanResponseDTO(ibanNumber, true);
	}
	
	@Override
	public IbanListResponse validateIbanList(IbanListRequest ibanList) throws UnsupportedCountryException {
		IbanListResponse response = new IbanListResponse();
		for (String ibanNumber: ibanList.getIbanList()) {
			AbstractIbanService ibanService = IbanServiceFactory.getIbanService(ibanNumber);
			if (ibanService == null)
				throw createUnsupportedCountryException(ibanNumber);
			try {
				ibanService.ibanValidation(ibanNumber);
			} catch (IbanException ibanException) {
				response.addElement(new IbanResponseDTO(ibanNumber, false, ibanException.getMessage()));
				continue;
			}
			response.addElement(new IbanResponseDTO(ibanNumber, true));
		}	
		return response;
	}
	
	private UnsupportedCountryException createUnsupportedCountryException(String ibanNumber) {
		String faultString = String.format(UnsupportedCountryFault.UNSUPPORTED_IBAN_COUNTRY, ibanNumber);
		UnsupportedCountryFault fault = new UnsupportedCountryFault(UnsupportedCountryFault.CLIENT_ERROR_CODE, faultString);
		return new UnsupportedCountryException(fault);
	}

}
