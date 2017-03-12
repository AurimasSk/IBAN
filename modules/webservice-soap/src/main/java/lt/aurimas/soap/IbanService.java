package lt.aurimas.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import lt.aurimas.iban.IbanResponseDTO;
import lt.aurimas.soap.exception.UnsupportedCountryException;


@WebService
@SOAPBinding(style = Style.RPC)
public interface IbanService {

	
	@WebMethod
	public IbanResponseDTO validateIban(@WebParam(name="ibanNumber") String ibanNumber) throws UnsupportedCountryException;
	
	@WebMethod
	public IbanListResponse validateIbanList(@WebParam(name = "ibanList") IbanListRequest ibanList) throws UnsupportedCountryException;
}
