package net.internetworkconsulting.accounting.ws;

import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceContext;
import net.internetworkconsulting.accounting.data.LogsRow;
import net.internetworkconsulting.accounting.data.MembershipsRow;
import net.internetworkconsulting.accounting.data.SettingsRow;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.Log;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;

@WebService(serviceName = "User")
public class UserService extends BaseService {
	@Resource
	protected WebServiceContext wsContext;


	@WebMethod(operationName = "loadByEmailAddress")
	public User loadByEmailAddress(@WebParam(name = "EmailAddress") String EmailAddress) throws Exception {		
		return User.loadByEmailAddress(getAdapter(wsContext), User.class, EmailAddress);
	}

	@WebMethod(operationName = "loadByGuid")
	public User loadByGuid(@WebParam(name = "Guid") String Guid) throws Exception {
		return User.loadByGuid(getAdapter(wsContext), User.class, Guid);
	}

	@WebMethod(operationName = "loadByDisplayName")
	public User loadByDisplayName(@WebParam(name = "DisplayName") String DisplayName) throws Exception {
		return User.loadByDisplayName(getAdapter(wsContext), User.class, DisplayName);
	}

	@WebMethod(operationName = "loadSearch")
	public List<User> loadSearch(@WebParam(name = "DisplayName") String DisplayName, @WebParam(name = "Status") String Status) throws Exception {
		return User.loadSearch(getAdapter(wsContext), User.class, DisplayName, Status);
	}	
	
	@WebMethod(operationName = "loadMemberships")
	public List<MembershipsRow> loadMemberships(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadMemberships(getAdapter(wsContext), User.class, false);
	}

	@WebMethod(operationName = "loadLogs")
	public List<LogsRow> loadLogs(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadLogs(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "loadSettings")
	public List<SettingsRow> loadSettings(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadSettings(getAdapter(wsContext), Log.class, false);
	}

	@WebMethod(operationName = "loadEmployee")
	public Employee loadEmployee(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		return row.loadEmployee(getAdapter(wsContext), Employee.class, false);
	}

	@WebMethod(operationName = "rowSave")
	public void rowSave(@WebParam(name = "row") User row) throws Exception {
		getAdapter(wsContext).save(User.TABLE_NAME, row);
	}

	@WebMethod(operationName = "rowDelete")
	public void rowDelete(@WebParam(name = "UsersGuid") String UsersGuid) throws Exception {
		User row = User.loadByGuid(getAdapter(wsContext), User.class, UsersGuid);
		row.setIsDeleted(true);
		getAdapter(wsContext).save(User.TABLE_NAME, row);		
	}

	@WebMethod(operationName = "login")
	public String login(
			@WebParam(name = "EmailAddress") String EmailAddress, 
			@WebParam(name = "Password") String Password, 
			@WebParam(name = "Database") String Database) throws Exception 
	{
		User attempting = new User();
		attempting.setDatabase(Database);
		attempting.setPassword(Password);
		attempting.setEmailAddress(EmailAddress);
		try { 
			AdapterInterface logged_in = attempting.login(); 
			setUser(wsContext, attempting);
		}
		catch(Exception ex) { return ex.getMessage(); }
		
		return "";		
	}
}
