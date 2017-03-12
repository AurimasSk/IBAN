package lt.aurimas.soap;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class to get list of iban numbers
 */
@XmlRootElement(name = "IbanRequest")
public class IbanListRequest {
	
	private List<String> ibanList;

	@XmlElement(name = "ibanNumber")
	public List<String> getIbanList() {
		return ibanList;
	}

	public void setIbanList(List<String> ibanList) {
		this.ibanList = ibanList;
	}

}
