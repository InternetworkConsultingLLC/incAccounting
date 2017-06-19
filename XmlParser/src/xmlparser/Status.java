package xmlparser;

import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Node;

// OFX:BANKMSGSRSV1:STMTTRNRS:STATUS
// OFX:SIGNONMSGSRSV1:SONRS:STATUS
public class Status {
	Status(Node parent, String target) {
		Code = Helper.getXmlValue(parent, target + ":CODE");
		Severity = Helper.getXmlValue(parent, target + ":SEVERITY");
	}

	// STATUS:CODE
	public String Code;
	public String getCode() { return Code; }
	public void setCode(String value) { Code = value; }
	
	// STATUS:SEVERITY
	public String Severity;
	public String getSeverity() { return Severity; }
	public void setSeverity(String value) { Severity = value; }
}
