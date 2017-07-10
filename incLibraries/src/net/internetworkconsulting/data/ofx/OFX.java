package net.internetworkconsulting.data.ofx;

import java.io.Serializable;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OFX implements Serializable {
	private String sStructure = "";
	public String getStructure() { return sStructure; }
	
	public OFX(String sFile) throws Exception {
		//String sFile = net.internetworkconsulting.data.Helper.FileToString(filename);

		int iStart = sFile.indexOf("<OFX>");
		int iEnd = sFile.indexOf("</OFX>");
		sFile = sFile.substring(iStart, iEnd + 6);

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(sFile));

		Document doc = db.parse(is);
		sStructure = outputStructure(doc.getElementsByTagName("OFX").item(0));

		Node ofx = doc.getElementsByTagName("OFX").item(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String sDate = Helper.getXmlValue(ofx, "OFX:SIGNONMSGSRSV1:SONRS:DTSERVER").substring(0, 14);
		
		ServerDateTime = sdf.parse(sDate);
		FinancialInstitutionIentifier = Helper.getXmlValue(ofx, "OFX:SIGNONMSGSRSV1:SONRS:FI:FID");
		FinancialInstitutionOrganization = Helper.getXmlValue(ofx, "OFX:SIGNONMSGSRSV1:SONRS:FI:ORG");
		ServerLanguage = Helper.getXmlValue(ofx, "OFX:SIGNONMSGSRSV1:SONRS:LANGUAGE");
		
		Status = new Status(ofx, "OFX:SIGNONMSGSRSV1:SONRS:STATUS");
		
		List<Node> lstNodes = Helper.getXmlNodes(ofx, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS");
		setStatements(new LinkedList<>());
		for(Node curr : lstNodes) {
			Statement stmt = new Statement(curr);
			Statements.add(stmt);
		}
	}

	private static String outputStructure(Node item) {
		String out = "";
		
		if(item == null)
			return "";
		if(item.getNodeName().equals("#text"))
			return "";
		
		String sNode = Helper.getXmlName(item);
		out += sNode + "\n";
		
		NodeList nlChildren = item.getChildNodes();
		for(int cnt = 0; cnt < nlChildren.getLength(); cnt++)
			out += outputStructure(nlChildren.item(cnt));
		
		return out;
	}	

	// OFX:SIGNONMSGSRSV1:SONRS:DTSERVER
	// 20170613233458.687[-5]
	// YYYYMMddhhmmss.fff[z]
	private Date ServerDateTime;
	public Date getServerDateTime() { return ServerDateTime; }
	public void setServerDateTime(Date value) { ServerDateTime = value; }

	// OFX:SIGNONMSGSRSV1:SONRS:FI:FID
	private String FinancialInstitutionIentifier;
	public String getFinancialInstitutionIentifier() { return FinancialInstitutionIentifier; }
	public void setFinancialInstitutionIentifier(String value) { FinancialInstitutionIentifier = value; }

	// OFX:SIGNONMSGSRSV1:SONRS:FI:ORG
	private String FinancialInstitutionOrganization;
	public String getFinancialInstitutionOrganization() { return FinancialInstitutionOrganization; }
	public void setFinancialInstitutionOrganization(String value) { FinancialInstitutionOrganization = value; }

	// OFX:SIGNONMSGSRSV1:SONRS:LANGUAGE
	private String ServerLanguage;
	public String getServerLanguage() { return ServerLanguage; }
	public void setServerLanguage(String value) { ServerLanguage = value; }
	
	// OFX:SIGNONMSGSRSV1:SONRS:STATUS
	private Status Status;
	public Status getStatus() { return Status; }
	public void setStatus(Status value) { Status = value; }
	
	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS
	private List<Statement> Statements;
	public List<Statement> getStatements() { return Statements; }
	public void setStatements(List<Statement> value) { Statements = value; }	
}
