/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.internetworkconsulting.accounting.ws;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.Group;
import net.internetworkconsulting.accounting.entities.List;
import net.internetworkconsulting.accounting.entities.ListFilter;
import net.internetworkconsulting.accounting.entities.Log;
import net.internetworkconsulting.accounting.entities.Membership;
import net.internetworkconsulting.accounting.entities.Setting;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.pervasive.Statement;
import org.w3c.dom.Element;

/**
 *
 * @author shawn
 */
@WebService(serviceName = "API")
public class API {
	// Globals /////////////////////////////////////////////////////////////////
	@Resource
	private WebServiceContext wsContext;
	
	private User getUser(WebServiceContext wsContext) throws Exception {		
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	
		return (User) session.getAttribute("Controller.User");		
	}
	private void setUser(WebServiceContext wsContext, User user) {
		MessageContext mc = wsContext.getMessageContext();
		ServletRequest sr = (ServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
		HttpServletRequest hsr = (HttpServletRequest) sr;
        HttpSession session = hsr.getSession(true);	

		session.setAttribute("Controller.User", user);
	}

	private AdapterInterface getAdapter(WebServiceContext wsContext) throws Exception {
		User activeUser = getUser(wsContext);
		if(activeUser == null)
			throw new Exception("Cannot find an active user.  Please login.");
		
		return activeUser.login();
	}
		
	private void denodeHashMaps(AdapterInterface adapter_interface, String TABLE_NAME, Row row) throws Exception {
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

	// User ////////////////////////////////////////////////////////////////////
	@WebMethod(operationName = "user_initialize")
	public User user_initialize(@WebParam(name = "Entity") User entity) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, entity);
		entity.initialize();
		return entity;
	}
	
	@WebMethod(operationName = "user_loadByEmailAddress")
	public User user_loadByEmailAddress(@WebParam(name = "EmailAddress") String EmailAddress) throws Exception {		
		return User.loadByEmailAddress(getAdapter(wsContext), User.class, EmailAddress);
	}

	@WebMethod(operationName = "user_loadByGuid")
	public User user_loadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return User.loadByGuid(getAdapter(wsContext), User.class, Guid);
	}

	@WebMethod(operationName = "user_loadByDisplayName")
	public User user_loadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return User.loadByDisplayName(getAdapter(wsContext), User.class, DisplayName);
	}

	@WebMethod(operationName = "user_loadSearch")
	public java.util.List<User> user_loadSearch(@WebParam(name = "DisplayName") String DisplayName, @WebParam(name = "IsAllowed") boolean IsAllowed) throws Exception {
		return User.loadSearch(getAdapter(wsContext), User.class, DisplayName, IsAllowed);
	}	
	
	@WebMethod(operationName = "user_loadMemberships")
	public java.util.List<Membership> user_loadMemberships(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadMemberships(getAdapter(wsContext), User.class, false);
	}

	@WebMethod(operationName = "user_loadLogs")
	public java.util.List<Log> user_loadLogs(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadLogs(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "user_loadSettings")
	public java.util.List<Setting> user_loadSettings(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadSettings(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "user_loadEmployee")
	public Employee user_loadEmployee(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User entity = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return entity.loadEmployee(getAdapter(wsContext), Employee.class, false);
	}

	@WebMethod(operationName = "user_save")
	public User user_save(@WebParam(name = "Entity") User entity) throws Exception {
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, entity);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(User.TABLE_NAME, entity);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return User.loadByGuid(getAdapter(wsContext), User.class, entity.getGuid());
	}
	
	@WebMethod(operationName = "user_resetPassword")
	public void user_resetPassword(
			@WebParam(name = "Entity") User entity, 
			@WebParam(name = "Password") String password, 
			@WebParam(name = "Confirm") String confirm
	) throws Exception {
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, entity);
		try {
			getAdapter(wsContext).begin(true);
			entity.resetSqlPassword(getAdapter(wsContext), password, confirm);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
	}

	@WebMethod(operationName = "user_login")
	public String user_login(
			@WebParam(name = "EmailAddress") String EmailAddress, 
			@WebParam(name = "Password") String Password, 
			@WebParam(name = "Database") String Database) throws Exception 
	{
		User attempting = new User();
		attempting.setDatabase(Database);
		attempting.setPassword(Password);
		attempting.setEmailAddress(EmailAddress);
		AdapterInterface logged_in = attempting.login(); 
		setUser(wsContext, attempting);

		return "";		
	}
	
	// Group ///////////////////////////////////////////////////////////////////	
	@WebMethod(operationName = "group_initialize")
	public Group group_initialize(@WebParam(name = "Entity") Group entity) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), Group.TABLE_NAME, entity);
		entity.initialize();
		return entity;
	}
	
	@WebMethod(operationName = "group_loadByEmailAddress")
	public Group group_loadByEmailAddress(@WebParam(name = "EmailAddress") String EmailAddress) throws Exception {		
		return Group.loadByEmailAddress(getAdapter(wsContext), Group.class, EmailAddress);
	}

	@WebMethod(operationName = "group_loadByGuid")
	public Group group_loadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return Group.loadByGuid(getAdapter(wsContext), Group.class, Guid);
	}

	@WebMethod(operationName = "group_loadByDisplayName")
	public Group group_loadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return Group.loadByDisplayName(getAdapter(wsContext), Group.class, DisplayName);
	}

	@WebMethod(operationName = "group_loadSearch")
	public java.util.List<Group> group_loadSearch(@WebParam(name = "DisplayName") String DisplayName, @WebParam(name = "IsAllowed") boolean IsAllowed) throws Exception {
		return Group.loadSearch(getAdapter(wsContext), Group.class, DisplayName, IsAllowed);
	}	

	@WebMethod(operationName = "group_save")
	public Group group_save(@WebParam(name = "Entity") Group entity) throws Exception {
		denodeHashMaps(getAdapter(wsContext), Group.TABLE_NAME, entity);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(Group.TABLE_NAME, entity);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return Group.loadByGuid(getAdapter(wsContext), Group.class, entity.getGuid());
	}
	
	// List ////////////////////////////////////////////////////////////////////
	@WebMethod(operationName = "list_initialize")
	public List list_initialize(@WebParam(name = "Entity") List entity) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), List.TABLE_NAME, entity);
		entity.initialize();
		return entity;
	}
	
	@WebMethod(operationName = "list_loadByGuid")
	public List list_loadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return List.loadByGuid(getAdapter(wsContext), List.class, Guid);
	}

	@WebMethod(operationName = "list_loadByDisplayName")
	public List list_loadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return List.loadByDisplayName(getAdapter(wsContext), List.class, DisplayName);
	}

	@WebMethod(operationName = "list_loadSearch")
	public java.util.List<List> list_loadSearch(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return List.loadSearch(getAdapter(wsContext), List.class, DisplayName);
	}	

	@WebMethod(operationName = "list_save")
	public List list_save(@WebParam(name = "Entity") List entity) throws Exception {
		denodeHashMaps(getAdapter(wsContext), List.TABLE_NAME, entity);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(List.TABLE_NAME, entity);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return List.loadByGuid(getAdapter(wsContext), List.class, entity.getGuid());
	}
	
	// List Filter /////////////////////////////////////////////////////////////
	@WebMethod(operationName = "listFilter_initialize")
	public ListFilter listFilter_initialize(@WebParam(name = "Entity") ListFilter entity) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), List.TABLE_NAME, entity);
		entity.initialize();
		return entity;
	}
	
	@WebMethod(operationName = "listFilter_loadByGuid")
	public ListFilter listFilter_loadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return ListFilter.loadByGuid(getAdapter(wsContext), List.class, Guid);
	}

	@WebMethod(operationName = "listFilter_save")
	public ListFilter listFilter_save(@WebParam(name = "Entity") ListFilter entity) throws Exception {
		denodeHashMaps(getAdapter(wsContext), List.TABLE_NAME, entity);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(List.TABLE_NAME, entity);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return ListFilter.loadByGuid(getAdapter(wsContext), List.class, entity.getGuid());
	}	
	
}
