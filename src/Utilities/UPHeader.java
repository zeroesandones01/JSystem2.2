package Utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Database.pgUpdate;
import Functions.UserInfo;


public class UPHeader {
	private File file;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private NodeList nodeList;
	private Node node;

	public void importXML(String path) {
		deleteTmpHeader();
		try {  

			//creating a constructor of file class and parsing an XML file  
			file = new File(path);  
			//an instance of factory that gives a document builder  
			dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			db = dbf.newDocumentBuilder();  
			doc = db.parse(file);  
			doc.getDocumentElement().normalize();  
//			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
			nodeList = doc.getElementsByTagName("z:row");  
		} catch (Exception e) {  
			System.out.println("Something went wrong");  
		}   
			// nodeList is not iterable, so we are using for loop  
			for (int itr = 0; itr < nodeList.getLength(); itr++) {  
				node = nodeList.item(itr);  
//				System.out.println("\nNode Name :" + node.getNodeName());
				System.out.println("\nROW : " + (itr + 1));  
				if (node.getNodeType() == Node.ELEMENT_NODE) {  
					Element eElement = (Element) node;  		
					
					UPInsertFromXML xml = new UPInsertFromXML();
					xml.insertToHeader(
							eElement.getAttribute("client_no"), 
							eElement.getAttribute("trans_date"),
							eElement.getAttribute("proj_id"), 
							eElement.getAttribute("pbl_id"), 
							eElement.getAttribute("seq_no"), 
							eElement.getAttribute("entity_id"), 
							eElement.getAttribute("selling_price"), 
							eElement.getAttribute("total_amt_paid"), 
							eElement.getAttribute("status_id"), 
							eElement.getAttribute("co_id"));
				}  
			}
	}
	private void deleteTmpHeader() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_payment_header where emp_code = '"+UserInfo.EmployeeCode+"'", true);
		dbExec.commit();
	}

}
