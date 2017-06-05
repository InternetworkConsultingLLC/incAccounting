package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.data.SessionInterface;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import net.internetworkconsulting.accounting.data.MembershipsRow;
import net.internetworkconsulting.accounting.data.SettingsRow;
import net.internetworkconsulting.accounting.data.UsersRow;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Helper;
import net.internetworkconsulting.data.mysql.Statement;

public class User extends UsersRow implements SessionInterface {
	public static String ADMINISTRATOR_GUID = "86b41969e95143c090fd93a4819c58a2";
	public static String SETTING_PASSWORD_AGE = "Password Age (Days)";
	public static String SETTING_PASSWORD_COMPLEXITY = "Password Complexity (1-4)";
	public static String SETTING_PASSWORD_LENGTH = "Password Length";
	public static String SETTING_VERSION_NUMBER = "Version Number";

	public static String newGuid() { return UUID.randomUUID().toString().replace("-", ""); }

	public User() { super(); }
	public void initialize() throws Exception { 
		this.setGuid(User.newGuid()); 
		this.setIsAllowed(true);
		this.setPasswordDate(new java.sql.Date(java.time.Instant.EPOCH.toEpochMilli()));
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if (lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(User.class, "User.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	
	private static User loadByEmailAddress(AdapterInterface adapter, String email_address) throws Exception {
		String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={Email} AND \"%s\"=1";
		sql = String.format(sql, User.TABLE_NAME, User.EMAIL_ADDRESS, User.IS_ALLOWED);
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Email}", email_address);
		List<User> lst = adapter.load(User.class, stmt, true);
		
		if(lst.size() != 1)
			return null;
		
		return lst.get(0);
	}

	public static String DATABASE = "Database";
	private String sDatabase;
	public void setDatabase(String value) { 
		sDatabase = value.replace("'", "").replace("\"", "").replace(";", "").replace(" ", "").replace("`", ""); 
	}
	public String getDatabase() { return sDatabase; }

	public static String PASSWORD = "Password";
	private String sPassword;
	public String getPassword() { return sPassword; }
	public void setPassword(java.lang.String value) { sPassword = value; }
	
	public AdapterInterface getAdapter() { return adapter; }
	private AdapterInterface adapter = null;
	private AdapterInterface loggingAdapter = null;
	private boolean isValidated = false;
	public AdapterInterface login() throws Exception {
		if(adapter != null && adapter.getConnection() != null && adapter.getSession() != null)
			return adapter;
		
		return login(new Adapter(), new Adapter());
	}
	public AdapterInterface login(AdapterInterface tempAdapter, AdapterInterface tempLoggingAdapter) throws Exception {
		if(getPassword() == null || getDatabase() == null)
			throw new Exception("Login failure!");

		tempAdapter.execute(new Statement("USE \"" + getDatabase() + "\""), false);
		tempLoggingAdapter.execute(new Statement("USE \"" + getDatabase() + "\""), false);
		
		if(isValidated) {
			loggingAdapter = tempLoggingAdapter;
			logEvent("Logged in!", "21cfe67af5004856b8501c0d89762655");

			tempAdapter.setSession(this);
			adapter = tempAdapter;
			return adapter;
		}

		// load new user to become session
		User row = User.loadByEmailAddress(tempAdapter, getEmailAddress());
		if(row == null)
			throw new Exception("Login failure!");
		String sHash = row.getPasswordHash();
		String arrHash[] = sHash.split(":");
		if(!hashPassword(new Integer(arrHash[0]), Helper.HexToByteArray(arrHash[1]), getPassword()).equalsIgnoreCase(sHash))
			throw new Exception("Login failure!");
		
		this.setOriginals(row.getOriginals());
		this.setChanges(new HashMap<>());

		// load settings
		List<Setting> lstSettings = loadSettings(tempAdapter, Setting.class, false);
		hmSettings = new HashMap();
		for(Setting setting : lstSettings) 
			hmSettings.put(setting.getKey(), setting.getValue());

		// check program vs database version
		checkVersion();

		// load securables
		hmSecurables = new HashMap<>();
		List<Securable> lstSecurables =  Securable.loadAll(tempAdapter, Securable.class);
		for (Securable sec : lstSecurables) 
			hmSecurables.put(sec.getGuid(), sec);

		// load memberships
		List<Membership> tempLstMemberships = this.loadMemberships(tempAdapter, Membership.class, false);
		hmPermissionsBySecurable = new HashMap<>();
		for (Membership mbr : tempLstMemberships) {
			Group grp = mbr.loadGroup(tempAdapter, Group.class, false);
			if (grp.getGuid().equals(Group.ADMINISTRATORS_GUID))
				bIsAdministrator = true;

			List<Permission> lstPerms = grp.loadPermissions(tempAdapter, Permission.class, false);
			for (Permission perm : lstPerms) {
				if(!hmPermissionsBySecurable.containsKey(perm.getSecurablesGuid())) {
					Permission newPerm = new Permission();
					newPerm.setSecurablesGuid(perm.getSecurablesGuid());
					hmPermissionsBySecurable.put(perm.getSecurablesGuid(), newPerm);
				}

				Permission myPerm = hmPermissionsBySecurable.get(perm.getSecurablesGuid());					
				myPerm.setCanCreate((myPerm.getCanCreate() != null && myPerm.getCanCreate()) || perm.getCanCreate());
				myPerm.setCanRead((myPerm.getCanRead() != null && myPerm.getCanRead()) || perm.getCanRead());
				myPerm.setCanUpdate((myPerm.getCanUpdate() != null && myPerm.getCanUpdate()) || perm.getCanUpdate());
				myPerm.setCanDelete((myPerm.getCanDelete() != null && myPerm.getCanDelete()) || perm.getCanDelete());
			}
		}

		// set validated and active session
		isValidated = true;
		tempAdapter.setSession(this);

		loggingAdapter = tempLoggingAdapter;
		logEvent("Logged in!", "28ea22a4879c447f96a29c58ddc5ff94");

		adapter = tempAdapter;
		return adapter;
	}
	
	public AdapterInterface unvalidatedLogin(String server, String user, String password) throws Exception { 
		AdapterInterface adapter = new Adapter(server, "", user, password, true);
		return adapter;
	}

	private List<Membership> lstMemberships = null;
	public <T extends MembershipsRow> List<T> loadMemberships(AdapterInterface adapter, Class biz, boolean force) throws Exception {
		if (lstMemberships == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Memberships\" WHERE \"Users GUID\"={PRIMARYKEY}");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstMemberships = adapter.load(Membership.class, stmt, true);

			Membership mbr = new Membership();
			mbr.setUsersGuid(getGuid());
			mbr.setGroupsGuid(Group.EVERYONE_GUID);

			lstMemberships.add(mbr);
		}
		return (List<T>) lstMemberships;
	}

	public void resetSqlPassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		resetSqlPassword(adapter, password, confirm, true);
	}
	public void changePassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		//SessionInterface session = adapter.getSession();
		resetSqlPassword(adapter, password, confirm, false);
	}
	private void resetSqlPassword(AdapterInterface adapter, String password, String confirm, boolean reset) throws Exception {
		validatePassword(adapter, password, confirm);

		this.setPasswordHash(hashPassword(password));
		if(reset)
			this.setPasswordDate(new java.sql.Date(java.time.Instant.EPOCH.toEpochMilli()));
		else
			this.setPasswordDate(new java.sql.Date((new Date()).getTime()));
		
		SessionInterface si = adapter.getSession();
		if(!reset) {
			User objUser = (User) si;
			if(!this.getGuid().equals(objUser.getGuid()))
				throw new Exception("A user cannot change another users password!");
			
			adapter.setSession(null);
		}
		
		adapter.save(User.TABLE_NAME, this);		

		if(!reset)
			adapter.setSession(si);
	}
	private void validatePassword(AdapterInterface adapter, String password, String confirm) throws Exception {
		if (password == null || confirm == null)
			throw new Exception("Password have not been set!");
		if (password.contains("'"))
			throw new Exception("The user name must not contain an apostrophe (')!");
		if (password.contains("'"))
			throw new Exception("The password must not contain an apostrophe (')!");
		if (!password.equals(confirm))
			throw new Exception("The password and confirmation do not match!");

		int iMinLength = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_LENGTH));
		if (password.length() < iMinLength)
			throw new Exception("The password must be at least " + adapter.getSession().getSetting(User.SETTING_PASSWORD_LENGTH) + " characters in length!");

		int iMinComplpexity = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_COMPLEXITY));
		if (getComplexity(password) < iMinComplpexity)
			throw new Exception("The password must contain " + adapter.getSession().getSetting(User.SETTING_PASSWORD_COMPLEXITY) + " of the following catagories: upper case, lower case, numbers, and symbols!");
	}
	private int getComplexity(String value) {
		boolean bUpper = false;
		boolean bLower = false;
		boolean bNumber = false;
		boolean bOther = false;

		for (int cnt = 0; cnt < value.length(); cnt++) {
			if (Character.isUpperCase(value.toCharArray()[cnt]))
				bUpper = true;
			else if (Character.isLowerCase(value.toCharArray()[cnt]))
				bLower = true;
			else if (Character.isDigit(value.toCharArray()[cnt]))
				bNumber = true;
			else
				bOther = true;
		}

		int cnt = 0;
		if (bUpper)
			cnt++;
		if (bLower)
			cnt++;
		if (bNumber)
			cnt++;
		if (bOther)
			cnt++;

		return cnt;
	}
	public boolean isPasswordExpired(AdapterInterface adapter) throws Exception {
		if (getPasswordDate() == null)
			return true;

		int iMaxDays = Integer.parseInt(adapter.getSession().getSetting(User.SETTING_PASSWORD_AGE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -iMaxDays);
		return getPasswordDate().before(cal.getTime());
	}

	private boolean bIsAdministrator = false;
	private HashMap<String, Securable> hmSecurables;
	private HashMap<String, Permission> hmPermissionsBySecurable;
	public void canCreate(String securable_guid) throws Exception {
		if(getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if(hmPermissionsBySecurable.containsKey(securable_guid) && hmPermissionsBySecurable.get(securable_guid).getCanCreate())
			return;
		
		String sName = "";
		try { sName = Securable.loadByGuid(this.login(), Securable.class, securable_guid).getDisplayName(); }
		catch(Exception ex) { throw new Exception("Could not locate securable by GUID ('" + securable_guid + "') for can create error!", ex); }
		
		throw new Exception("You do not have create permissions to '" + sName + "'!");
	}
	public void canRead(String securable_guid) throws Exception {
		if(getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if(hmPermissionsBySecurable.containsKey(securable_guid) && hmPermissionsBySecurable.get(securable_guid).getCanRead())
			return;
		
		String sName = "";
		try { sName = Securable.loadByGuid(this.login(), Securable.class, securable_guid).getDisplayName(); }
		catch(Exception ex) { throw new Exception("Could not locate securable by GUID ('" + securable_guid + "') for can read error!", ex); }
		
		throw new Exception("You do not have read permissions to '" + sName + "'!");
	}
	public void canUpdate(String securable_guid) throws Exception {
		if(getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if(hmPermissionsBySecurable.containsKey(securable_guid) && hmPermissionsBySecurable.get(securable_guid).getCanUpdate())
			return;
		
		String sName = "";
		try { sName = Securable.loadByGuid(this.login(), Securable.class, securable_guid).getDisplayName(); }
		catch(Exception ex) { throw new Exception("Could not locate securable by GUID ('" + securable_guid + "') for can update error!", ex); }
		
		throw new Exception("You do not have update permissions to '" + sName + "'!");
	}
	public void canDelete(String securable_guid) throws Exception {
		if(getGuid().equals(User.ADMINISTRATOR_GUID) || bIsAdministrator)
			return;
		if(hmPermissionsBySecurable.containsKey(securable_guid) && hmPermissionsBySecurable.get(securable_guid).getCanDelete())
			return;
		
		String sName = "";
		try { sName = Securable.loadByGuid(this.login(), Securable.class, securable_guid).getDisplayName(); }
		catch(Exception ex) { throw new Exception("Could not locate securable by GUID ('" + securable_guid + "') for can delete error!", ex); }
		
		throw new Exception("You do not have delete permissions to '" + sName + "'!");
	}

	public void logEvent(String message, String code_guid) {
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);
			log.setDetails(message);
			log.setLog("Event");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception ex) { }
	}
	public void logExcpetion(Exception ex, String code_guid) {
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);

			String out = ex.getMessage();
			out += "\n\n";
			out += ex.toString();
			out += "\n\n";
			for (StackTraceElement st : ex.getStackTrace())
				out += st.toString() + "\n";

			log.setDetails(out);
			log.setLog("Exception");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception exx) { }
	}
	private static String lastSql = "";
	public void logSql(String sql, String code_guid) {
		if(lastSql.equals(sql))
			return;
		lastSql = sql;
		
		try {
			Log log = new Log();
			log.setGuid(User.newGuid());
			log.setCodeGuid(code_guid);
			log.setDetails(sql);
			log.setLog("SQL");
			log.setOccured(new Timestamp((new Date()).getTime()));
			log.setUsersGuid(getGuid());
			loggingAdapter.save(Log.TABLE_NAME, log);
		} catch (Exception ex) { }
	}

	private String sProgramPath = null;
	public String getProgramPath() { return sProgramPath; }
	public void setProgramPath(String value) { sProgramPath = value; }
	
	private List<Setting> lstSettings = null;
	public <T extends SettingsRow> List<T> loadSettings(AdapterInterface adapter, Class biz, boolean force) throws Exception {
		if (lstSettings == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} OR \"%s\" IS NULL ORDER BY \"%s\"";
			sql = String.format(sql, Setting.TABLE_NAME, Setting.USERS_GUID, Setting.USERS_GUID, Setting.USERS_GUID);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstSettings = adapter.load(biz, stmt, true);
		}
		return (List<T>) lstSettings;
	}
	private HashMap<String, String> hmSettings = new HashMap<>();
	public String getSetting(String key) { 
		if(hmSettings.containsKey(key))
			return hmSettings.get(key);
		else
			return null;
	}
	public void setSetting(String key, String value) { hmSettings.put(key, value); }
	
	public static String VersionNumber = "2017.6.4";
	public String getVersion() { return User.VersionNumber; }
	public void checkVersion() throws Exception {
		if(!hmSettings.containsKey(SETTING_VERSION_NUMBER))
			throw new Exception("Could not locate a version number in the applications settings!");

		String sSetting = hmSettings.get(SETTING_VERSION_NUMBER);
		String sVersion = getVersion();
		
		if(!sSetting.equals(sVersion))
			throw new Exception("The program version number does not match the database version number.");
	}
	
	public String readJar(Class jar, String filename) throws Exception {
		return Helper.InputStreamToString(jar.getResourceAsStream(filename));
	}
	public static String readJarFile(Class jar, String filename) throws Exception {
		return Helper.InputStreamToString(jar.getResourceAsStream(filename));
	}
				
    public static String hashPassword(String password) throws Exception {
        int iterations = 1000;
        byte[] salt = getSalt();
        
		return hashPassword(iterations, salt, password);
    }
    public static String hashPassword(int iterations, byte[] salt, String password) throws Exception {
        char[] chars = password.toCharArray();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + Helper.ByteArrayToHex(salt) + ":" + Helper.ByteArrayToHex(hash);
    }
    private static byte[] getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
