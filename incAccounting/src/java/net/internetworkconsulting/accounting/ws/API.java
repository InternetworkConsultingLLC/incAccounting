package net.internetworkconsulting.accounting.ws;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import net.internetworkconsulting.accounting.data.LogsRow;
import net.internetworkconsulting.accounting.data.MembershipsRow;
import net.internetworkconsulting.accounting.data.SettingsRow;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.Group;
import net.internetworkconsulting.accounting.entities.Log;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.pervasive.Statement;
import org.w3c.dom.Element;

@WebService(serviceName = "API")
public class API {
	////////////////////////////////////////////////////////////////////////////
	//
	// Global Helpers
	//
	////////////////////////////////////////////////////////////////////////////
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
	
	////////////////////////////////////////////////////////////////////////////
	//
	// User
	//
	////////////////////////////////////////////////////////////////////////////
	@WebMethod(operationName = "userInitialize")
	public User userInitialize(@WebParam(name = "User") User user) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, user);
		user.initialize();
		return user;
	}
	
	@WebMethod(operationName = "userLoadByEmailAddress")
	public User userLoadByEmailAddress(@WebParam(name = "EmailAddress") String EmailAddress) throws Exception {		
		return User.loadByEmailAddress(getAdapter(wsContext), User.class, EmailAddress);
	}

	@WebMethod(operationName = "userLoadByGuid")
	public User userLoadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return User.loadByGuid(getAdapter(wsContext), User.class, Guid);
	}

	@WebMethod(operationName = "userLoadByDisplayName")
	public User userLoadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return User.loadByDisplayName(getAdapter(wsContext), User.class, DisplayName);
	}

	@WebMethod(operationName = "userLoadSearch")
	public List<User> userLoadSearch(@WebParam(name = "DisplayName") String DisplayName, @WebParam(name = "IsAllowed") boolean IsAllowed) throws Exception {
		return User.loadSearch(getAdapter(wsContext), User.class, DisplayName, IsAllowed);
	}	
	
	@WebMethod(operationName = "userLoadMemberships")
	public List<MembershipsRow> userLoadMemberships(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadMemberships(getAdapter(wsContext), User.class, false);
	}

	@WebMethod(operationName = "userLoadLogs")
	public List<LogsRow> userLoadLogs(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadLogs(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "userLoadSettings")
	public List<SettingsRow> userLoadSettings(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadSettings(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "userLoadEmployee")
	public Employee userLoadEmployee(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadEmployee(getAdapter(wsContext), Employee.class, false);
	}

	@WebMethod(operationName = "userSave")
	public User userSave(@WebParam(name = "User") User user) throws Exception {
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, user);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(User.TABLE_NAME, user);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return User.loadByGuid(getAdapter(wsContext), User.class, user.getGuid());
	}
	
	@WebMethod(operationName = "userResetSqlPassword")
	public void userResetSqlPassword(
			@WebParam(name = "User") User user, 
			@WebParam(name = "Password") String password, 
			@WebParam(name = "Confirm") String confirm
	) throws Exception {
		denodeHashMaps(getAdapter(wsContext), User.TABLE_NAME, user);
		try {
			getAdapter(wsContext).begin(true);
			user.resetSqlPassword(getAdapter(wsContext), password, confirm);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
	}

	@WebMethod(operationName = "userLogin")
	public String userLogin(
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

	////////////////////////////////////////////////////////////////////////////
	//
	// Group
	//
	////////////////////////////////////////////////////////////////////////////
	@WebMethod(operationName = "groupInitialize")
	public Group groupInitialize(@WebParam(name = "Group") Group group) throws Exception {		
		denodeHashMaps(getAdapter(wsContext), Group.TABLE_NAME, group);
		group.initialize();
		return group;
	}
	
	@WebMethod(operationName = "groupLoadByEmailAddress")
	public Group groupLoadByEmailAddress(@WebParam(name = "EmailAddress") String EmailAddress) throws Exception {		
		return Group.loadByEmailAddress(getAdapter(wsContext), Group.class, EmailAddress);
	}

	@WebMethod(operationName = "groupLoadByGuid")
	public Group groupLoadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return Group.loadByGuid(getAdapter(wsContext), Group.class, Guid);
	}

	@WebMethod(operationName = "groupLoadByDisplayName")
	public Group groupLoadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return Group.loadByDisplayName(getAdapter(wsContext), Group.class, DisplayName);
	}

	@WebMethod(operationName = "groupLoadSearch")
	public List<Group> groupLoadSearch(@WebParam(name = "DisplayName") String DisplayName, @WebParam(name = "IsAllowed") boolean IsAllowed) throws Exception {
		return Group.loadSearch(getAdapter(wsContext), Group.class, DisplayName, IsAllowed);
	}	

	@WebMethod(operationName = "groupSave")
	public Group groupSave(@WebParam(name = "Group") Group group) throws Exception {
		denodeHashMaps(getAdapter(wsContext), Group.TABLE_NAME, group);
		try {
			getAdapter(wsContext).begin(true);
			getAdapter(wsContext).save(Group.TABLE_NAME, group);
			getAdapter(wsContext).commit(true);
		}
		catch(Exception ex) {
			getAdapter(wsContext).rollback(true);
			throw ex;
		}
		return Group.loadByGuid(getAdapter(wsContext), Group.class, group.getGuid());
	}
	
}
