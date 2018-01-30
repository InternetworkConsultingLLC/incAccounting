package net.internetworkconsulting.accounting.ws;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;

public class BaseService {
	protected User getUser(WebServiceContext wsContext) throws Exception {		
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	
		return (User) session.getAttribute("Controller.User");		
	}

	protected AdapterInterface getAdapter(WebServiceContext wsContext) throws Exception {
		return getUser(wsContext).login();
	}
	
	protected void setUser(WebServiceContext wsContext, User user) {
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	

		session.setAttribute("Controller.User", user);
	}
}
