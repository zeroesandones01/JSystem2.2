package Utilities;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Database.pgUpdate;
import Functions.UserInfo;

public class UPDetail {
	private File file;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private NodeList nodeList;
	private Node node;
	private Element eElement;

	public void importXML(String path) {
		deleteTmpDetail();
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
					eElement = (Element) node;  
					
					UPInsertFromXML xml = new UPInsertFromXML();
					xml.insertToDetail(
							eElement.getAttribute("client_no"), 
							eElement.getAttribute("part_type"), 
							eElement.getAttribute("bank"), 
							eElement.getAttribute("branch"), 
							eElement.getAttribute("acct_no"), 
							eElement.getAttribute("check_no"), 
							eElement.getAttribute("check_date"), 
							eElement.getAttribute("amount"), 
							eElement.getAttribute("receipt_no"), 
							eElement.getAttribute("receipt_type"), 
							eElement.getAttribute("status_id"));
				}  
			}
	}
	
	private void deleteTmpDetail() {
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_payment_detail where emp_code = '"+UserInfo.EmployeeCode+"'", true);
		dbExec.commit();
	}


}
