package lt.aurimas.iban.service;

import java.math.BigInteger;

import lt.aurimas.iban.exception.IbanException;

public abstract class AbstractIbanService {
	
	private static final BigInteger MODULUS = new BigInteger("97");
		
	/**
     * Validates iban number. Throws exception only if iban is not valid
	 *
	 * @param ibanNumber
	 * @throws IbanException - throws if validation fails at any validation step
	 */
	public final void ibanValidation(String ibanNumber) throws IbanException {
		String changedIban = removeAllWhiteSpaces(ibanNumber);
		validateIbanSymbols(changedIban);
		validateIbanLength(changedIban);
		changedIban = moveFirstFourSymbolsToTail(changedIban);
		changedIban = replaceLettersWithNumbers(changedIban);
		int remainer = applyMod97Operation(changedIban);
		expectedRemainer(remainer);
	}
	
	/**
	 * Implemented by super class
     *
	 * @param ibanNumber
	 * @throws IbanException - throws if iban length is not equal to expected country iban size
	 */
	protected abstract void validateIbanLength(String ibanNumber) throws IbanException;
	
	public String removeAllWhiteSpaces(String ibanNumber) {
		return ibanNumber.replaceAll("\\s+", "");
	}
	
	/**
	 * @param ibanNumber
	 * @throws IbanException - throws exception if number contains any non upper case letter or number
	 */
	public void validateIbanSymbols(String ibanNumber) throws IbanException {
		if (!ibanNumber.matches("^[A-Z0-9]*$"))
			throw new IbanException(IbanException.CONTAINS_INVALID_CHARS);
	}
	
	private String moveFirstFourSymbolsToTail(String ibanNumber) {
		return ibanNumber.substring(4) + ibanNumber.substring(0, 4);
	}
	
	/**
	 * @param ibanNumber
	 * @return return ibanNumber where all letter are changed to numbers (A=10, B=11, ... , Z=35)
	 */
	private String replaceLettersWithNumbers(String ibanNumber) {
		StringBuffer ibanWithoutLetters = new StringBuffer();
		for (int i = 0; i < ibanNumber.length(); i++) {
			ibanWithoutLetters.append(Character.getNumericValue(ibanNumber.charAt(i)));		
		}
		return ibanWithoutLetters.toString();
	}
	
	/**
	 * @param ibanNumber
	 * @return returns remainer of operation ibanNumber mod MODULUS
	 */
	private int applyMod97Operation(String ibanNumber) {
		BigInteger ibanNumberInt = new BigInteger(ibanNumber);
		return ibanNumberInt.mod(MODULUS).intValue();
	}
	
	/**
     * Checks iban number remainer and if it !=1, throws exception
     *
	 * @param remainer
	 * @throws IbanException - throws if remainer != 1
	 */
	private void expectedRemainer(int remainer) throws IbanException {
		if (remainer != 1)
			throw new IbanException(IbanException.INVALID_IBAN_FORMAT);
	}
	
}
