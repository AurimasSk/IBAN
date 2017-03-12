package lt.aurimas.iban;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IBAN_RESPONSE_DTO")
public class IbanResponseDTO {
	
	private static final String SUCCESS = "OK";
	
	private String number;
	private boolean isValid;	
	private String status;

	public IbanResponseDTO () {		
	}
	
	public IbanResponseDTO (String number, boolean isValid) {
		this(number, isValid, SUCCESS);
	}
	
	public IbanResponseDTO (String number, boolean isValid, String status) {
		this.number = number;
		this.isValid = isValid;
		this.status = status;
	}
	
	@XmlElement
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	@XmlAttribute
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
