package xmlparser;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlParser {
	public static void main(String[] args) {
		try {
			String sFile = net.internetworkconsulting.data.Helper.FileToString("C:\\Users\\Owner\\Downloads\\AccountHistory.ofx");
			int iStart = sFile.indexOf("<OFX>");
			int iEnd = sFile.indexOf("</OFX>");
			sFile = sFile.substring(iStart, iEnd + 6);
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(sFile));

			Document doc = db.parse(is);
			outputStructure(doc.getElementsByTagName("OFX").item(0));
			
			OFX obj = new OFX(doc.getElementsByTagName("OFX").item(0));
		}
		catch(Exception ex) {
			Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void outputStructure(Node item) {
		if(item == null)
			return;
		if(item.getNodeName().equals("#text"))
			return;
		
		String sNode = Helper.getXmlName(item);
		System.out.println(sNode);
		outputStructure(item.getFirstChild());
		outputStructure(item.getNextSibling());
	}
}
