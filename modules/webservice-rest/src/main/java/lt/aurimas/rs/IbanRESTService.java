package lt.aurimas.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lt.aurimas.iban.IbanResponseDTO;
import lt.aurimas.iban.IbanServiceFactory;
import lt.aurimas.iban.exception.IbanException;
import lt.aurimas.iban.service.AbstractIbanService;
import lt.aurimas.rs.exception.IbanValidationException;

@Path("/iban")
public class IbanRESTService {

	@GET
	@Path("/validate/{iban}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response validateIban(@PathParam("iban") String ibanNumber) throws IbanValidationException {
		IbanResponseDTO errorMessage = validateInternal(ibanNumber);
		
		if (errorMessage != null)
			throw new IbanValidationException(errorMessage);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/validate/list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response validateIbanList(@QueryParam("iban") List<String> ibanList) throws IbanValidationException {
		List<IbanResponseDTO> errorMessages = new ArrayList<IbanResponseDTO>();
		for (String ibanNumber: ibanList) {
			IbanResponseDTO errorEntity = validateInternal(ibanNumber);
			if (errorEntity != null)
				errorMessages.add(errorEntity);
		}

		if (!errorMessages.isEmpty())
			throw new IbanValidationException(errorMessages);
		
		return Response.ok().build();
	}
	
	private IbanResponseDTO validateInternal(String ibanNumber) {
		AbstractIbanService ibanService = IbanServiceFactory.getIbanService(ibanNumber);
		if (ibanService == null) {
			return new IbanResponseDTO(ibanNumber, false, IbanException.UNSUPPORTED_COUNTRY);
		}
		
		try {
			ibanService.ibanValidation(ibanNumber);
		} catch (IbanException ibanException) {
			return new IbanResponseDTO(ibanNumber, false, ibanException.getMessage());
		}
		return null;
	}
}
