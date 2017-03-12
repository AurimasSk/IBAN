package lt.aurimas.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lt.aurimas.iban.IbanResponseDTO;


/**
 * Wrapper class to return list of response elements
 */
@XmlRootElement(name = "IbanList")
public class IbanListResponse {

    private List<IbanResponseDTO> ibanList;
    
    @XmlElement(name = "iban")
    public void setIbanList(List<IbanResponseDTO> ibanList) {
        this.ibanList = ibanList;
    }

    public List<IbanResponseDTO> getIbanList() {
    	if (ibanList == null)
    		ibanList = new ArrayList<IbanResponseDTO>();
        return ibanList;
    }
    
    public void addElement(IbanResponseDTO iban) {
    	getIbanList().add(iban);
    }
}
