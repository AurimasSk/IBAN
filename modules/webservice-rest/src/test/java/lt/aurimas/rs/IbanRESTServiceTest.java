package lt.aurimas.rs;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import lt.aurimas.iban.IbanResponseDTO;
import lt.aurimas.iban.exception.IbanException;
import lt.aurimas.rs.exception.IbanValidationException;


public class IbanRESTServiceTest {
	
	private static final String VALID_IBAN = "LT12 1000 0111 0100 1000";
	
	private static IbanRESTService ibanService;
	
	@BeforeClass
	public static void init() {
		ibanService = new IbanRESTService();
	}
	
	@Test
	public void checkValidIbanTest() {
		Response actualResponse = ibanService.validateIban(VALID_IBAN);		
		assertEquals(200, actualResponse.getStatus());
	}
	
	@Test (expected = IbanValidationException.class)
	public void validIbanWithWrongLengthTest() {		
		String expectedStatus = IbanException.INVALID_IBAN_LENGTH;
		
		try {
			ibanService.validateIban("LT128");
		} 
		catch (IbanValidationException exception) {
			List<IbanResponseDTO> errorList = exception.getErrors();
			assertEquals(1, errorList.size());
			assertEquals(expectedStatus, errorList.get(0).getStatus());
			throw exception;
		}
	}
	
	@Test (expected = IbanValidationException.class)
	public void validIbanContainingIvalidCharsTest() {		
		String expectedStatus = IbanException.CONTAINS_INVALID_CHARS;
		
		try {
			ibanService.validateIban("LT12.8");
		} 
		catch (IbanValidationException exception) {
			List<IbanResponseDTO> errorList = exception.getErrors();
			assertEquals(1, errorList.size());
			assertEquals(expectedStatus, errorList.get(0).getStatus());
			throw exception;
		}
	}
	
	@Test (expected = IbanValidationException.class)
	public void validIbanWithWrongFormatTest() {		
		String expectedStatus = IbanException.INVALID_IBAN_FORMAT;
		
		try {
			ibanService.validateIban("LT12 1000 0111 0100 1001");
		} 
		catch (IbanValidationException exception) {
			List<IbanResponseDTO> errorList = exception.getErrors();
			assertEquals(1, errorList.size());
			assertEquals(expectedStatus, errorList.get(0).getStatus());
			throw exception;
		}
	}
	
	@Test (expected = IbanValidationException.class)
	public void checkUnsupportedCountryIbanTest() {		
		String expectedStatus = IbanException.UNSUPPORTED_COUNTRY;
		
		try {
			ibanService.validateIban("LG12 1000 0111 0100 1001");
		} 
		catch (IbanValidationException exception) {
			List<IbanResponseDTO> errorList = exception.getErrors();
			assertEquals(1, errorList.size());
			assertEquals(expectedStatus, errorList.get(0).getStatus());
			throw exception;
		}		
	}

	@Test
	public void checkValidIbanListTest() {		
		List<String> request = Arrays.asList(VALID_IBAN, VALID_IBAN);
		
		Response response = ibanService.validateIbanList(request);		

		assertEquals(200, response.getStatus());
	}
	
	@Test (expected = IbanValidationException.class)
	public void checkIbanValidationExceptionIbanListTest() {		
		List<String> request = Arrays.asList(VALID_IBAN, "LT12");
		
		try {
			ibanService.validateIbanList(request);
		} 
		catch (IbanValidationException exception) {
			List<IbanResponseDTO> errorList = exception.getErrors();
			assertEquals(1, errorList.size());
			assertEquals(IbanException.INVALID_IBAN_LENGTH, errorList.get(0).getStatus());
			throw exception;
		}	
	}

}
