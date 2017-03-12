package lt.aurimas.rs.exception;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import lt.aurimas.iban.IbanResponseDTO;

@Provider
public class IbanValidationException extends WebApplicationException {
	
	private List<IbanResponseDTO> errorMessages;
	
    public IbanValidationException(IbanResponseDTO errorMessages) {
        this(Arrays.asList(errorMessages));
    }
    
    public IbanValidationException(List<IbanResponseDTO> errorMessages) {
    	super(Response.status(Status.BAD_REQUEST).entity(errorMessages).build());
        this.errorMessages = errorMessages;
    }
    
    public List<IbanResponseDTO> getErrors() {
        return errorMessages;
    }
}
