package net.internetworkconsulting.data.ofx;

import java.io.Serializable;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Node;

// OFX:BANKMSGSRSV1:STMTTRNRS:STATUS
// OFX:SIGNONMSGSRSV1:SONRS:STATUS
public class Status implements Serializable {
	Status(Node parent, String target) throws Exception {
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
