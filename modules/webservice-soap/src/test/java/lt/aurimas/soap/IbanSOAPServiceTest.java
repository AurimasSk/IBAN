package lt.aurimas.soap;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import lt.aurimas.iban.IbanResponseDTO;
import lt.aurimas.iban.exception.IbanException;
import lt.aurimas.soap.exception.UnsupportedCountryException;
import lt.aurimas.soap.exception.UnsupportedCountryFault;

public class IbanSOAPServiceTest {
	
	private static final String VALID_IBAN = "LT12 1000 0111 0100 1000";
	
	private static IbanService ibanService;
	
	@BeforeClass
	public static void init() {
		ibanService = new IbanServiceImpl();
	}
	
	@Test
	public void checkValidIbanTest() throws UnsupportedCountryException {		
		IbanResponseDTO actualResponse = ibanService.validateIban(VALID_IBAN);		
		assertEquals(true, actualResponse.isValid());
	}
	
	@Test
	public void validIbanWithWrongLengthTest() throws UnsupportedCountryException {		
		String expectedStatus = IbanException.INVALID_IBAN_LENGTH;
		
		IbanResponseDTO actualResponse = ibanService.validateIban("LT182");	
		
		assertEquals(false, actualResponse.isValid());
		assertEquals(expectedStatus, actualResponse.getStatus());
	}
	
	@Test
	public void validIbanContainingIvalidCharsTest() throws UnsupportedCountryException {		
		String expectedStatus = IbanException.CONTAINS_INVALID_CHARS;
		
		IbanResponseDTO actualResponse = ibanService.validateIban("LT182.85");	
		
		assertEquals(false, actualResponse.isValid());
		assertEquals(expectedStatus, actualResponse.getStatus());
	}
	
	@Test
	public void validIbanWithWrongFormatTest() throws UnsupportedCountryException {		
		String expectedStatus = IbanException.INVALID_IBAN_FORMAT;
		
		IbanResponseDTO actualResponse = ibanService.validateIban("LT12 1000 0111 0100 1001");	
		
		assertEquals(false, actualResponse.isValid());
		assertEquals(expectedStatus, actualResponse.getStatus());
	}
	
	@Test (expected = UnsupportedCountryException.class)
	public void checkUnsupportedCountryIbanTest() throws UnsupportedCountryException {		
		ibanService.validateIban("LG18");		
	}
	
	@Test
	public void checkUnsupportedCountryFaultMessageTest() {
		String validatedIban = "LG18";
		String expectedFaultString = String.format(UnsupportedCountryFault.UNSUPPORTED_IBAN_COUNTRY, validatedIban);
		
		try {
			ibanService.validateIban(validatedIban);
		} catch (UnsupportedCountryException e) {
			assertEquals(UnsupportedCountryFault.CLIENT_ERROR_CODE, e.getFaultInfo().getFaultCode());
			assertEquals(expectedFaultString, e.getFaultInfo().getFaultString());
		}		
	}
	
	@Test
	public void checkValidIbanListTest() throws UnsupportedCountryException {		
		IbanListRequest request = prepareIbanListRequest(VALID_IBAN, VALID_IBAN);
		int expectedIbanListSize = request.getIbanList().size();
		
		IbanListResponse actualResponse = ibanService.validateIbanList(request);		
		List<IbanResponseDTO> actualIbanList = actualResponse.getIbanList();
		
		for (int i = 0; i < expectedIbanListSize; i++)
			assertEquals(true, actualIbanList.get(i).isValid());
	}
	
	@Test
	public void checkInvalidIbanListTest() throws UnsupportedCountryException {		
		IbanListRequest request = prepareIbanListRequest("LT12384", "LT.12");
		int expectedIbanListSize = request.getIbanList().size();
		
		IbanListResponse actualResponse = ibanService.validateIbanList(request);		
		List<IbanResponseDTO> actualIbanList = actualResponse.getIbanList();
		
		for (int i = 0; i < expectedIbanListSize; i++)
			assertEquals(false, actualIbanList.get(i).isValid());
	}
	
	@Test (expected = UnsupportedCountryException.class)
	public void checkUnsupportedCountryIbanListTest() throws UnsupportedCountryException {		
		IbanListRequest request = prepareIbanListRequest("LT12384", "LG12554");
		
		ibanService.validateIbanList(request);		

	}
	
	private IbanListRequest prepareIbanListRequest(String... ibanArray) {
		IbanListRequest request = new IbanListRequest();
		request.setIbanList(Arrays.asList(ibanArray));
		return request;
	}

}
