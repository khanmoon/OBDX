package testingApp;

import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferRequest;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class MainClass {

	public static void main(String[] args) {
//		TestClass test = new TestClass();
		
		MasterpassTransferRequest donationTransferRequest = new MasterpassTransferRequest();
		donationTransferRequest = new MasterpassTransferRequest("Donation");
//		System.out.println(donationTransferRequest.toString());
//	      if (request.getDictionaryArray() != null) {
//	        donationTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
//	      }
//	      donationTransferRequest.setSrcAccount(request.getSrcAccount());
//	      donationTransferRequest.setBillerId(request.getBillerId());
//	      donationTransferRequest.setAmount(request.getPmtAmount());
//	      
//	      donationTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
		try {
//	        File file = new File("C:\\file.xml");
	        JAXBContext jaxbContext = JAXBContext.newInstance(MasterpassTransferRequest.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	        // output pretty printed
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

//	        jaxbMarshaller.marshal(donationTransferRequest, file);
	        jaxbMarshaller.marshal(donationTransferRequest, System.out);

	      } catch (JAXBException e) {
	        e.printStackTrace();
	   }
	}

}

class TestClass{
	String testName;
	String testInt;
	@Override
	public String toString() {
		return "TestClass [testName=" + testName + ", testInt=" + testInt + "]";
	}
}
