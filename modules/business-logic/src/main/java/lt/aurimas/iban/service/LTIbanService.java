package lt.aurimas.iban.service;

import lt.aurimas.iban.exception.IbanException;


public class LTIbanService extends AbstractIbanService {

	protected static final int IBAN_LENGTH = 20;
	
	@Override
	protected void validateIbanLength(String ibanNumber) throws IbanException {
		if (ibanNumber.length() != IBAN_LENGTH)
			throw new IbanException(IbanException.INVALID_IBAN_LENGTH);
	}

}
