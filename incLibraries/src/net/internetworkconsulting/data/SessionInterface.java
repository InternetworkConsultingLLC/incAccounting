package net.internetworkconsulting.data;

public interface SessionInterface {
	AdapterInterface getAdapter();

	String getVersion();	

	String getSetting(String key);
	void setSetting(String key, String value);

	void logEvent(String message, String code_guid);
	void logExcpetion(Exception ex, String code_guid);
	void logSql(String sql, String code_guid);
	
	void canCreate(String securable_guid) throws Exception;
	void canDelete(String securable_guid) throws Exception;
	void canRead(String securable_guid) throws Exception;
	void canUpdate(String securable_guid) throws Exception;	

	String readJar(Class jar, String filename) throws Exception;
}
