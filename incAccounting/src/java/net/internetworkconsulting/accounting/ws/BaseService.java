package net.internetworkconsulting.accounting.ws;

import java.util.HashMap;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.pervasive.Statement;
import org.w3c.dom.Element;

public class BaseService {
	protected User getUser(WebServiceContext wsContext) throws Exception {		
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	
		return (User) session.getAttribute("Controller.User");		
	}

	protected AdapterInterface getAdapter(WebServiceContext wsContext) throws Exception {
		User activeUser = getUser(wsContext);
		if(activeUser == null)
			throw new Exception("Cannot find an active user.  Please login.");
		
		return activeUser.login();
	}
	
	protected void setUser(WebServiceContext wsContext, User user) {
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	

		session.setAttribute("Controller.User", user);
	}
	
	protected void denodeHashMaps(AdapterInterface adapter_interface, String TABLE_NAME, Row row) throws Exception {
		Adapter adapter = (Adapter) adapter_interface;
		HashMap<String, String> hmColumns = adapter.loadColumns(TABLE_NAME);

		convertMap(row.getChanges(), hmColumns);
		convertMap(row.getOriginals(), hmColumns);
	}
	private void convertMap(HashMap<String, Object> noded, HashMap<String, String> columnTypes) throws Exception {
		for(String key : columnTypes.keySet()) {
		
			if(noded.containsKey(key)) {
				String sValue = null;
				Object objValue = null;
				
				try {
					Element node = (Element) noded.get(key);
					sValue = node.getFirstChild().getNodeValue();
					Class cls = Statement.getJavaTypeForSqlType(columnTypes.get(key));
					objValue = Statement.parseStringToValue(cls, sValue);					
				}
				catch(Exception ex) {}
				
				noded.put(key, objValue);				
			}
		}
	}
}
