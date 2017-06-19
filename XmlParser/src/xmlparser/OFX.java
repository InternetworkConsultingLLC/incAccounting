package xmlparser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Node;

public class OFX {
	@SuppressWarnings("OverridableMethodCallInConstructor")
	OFX(Node ofx) throws Exception {
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
