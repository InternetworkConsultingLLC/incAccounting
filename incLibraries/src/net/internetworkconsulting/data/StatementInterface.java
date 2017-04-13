package net.internetworkconsulting.data;

import java.util.HashMap;

public interface StatementInterface {
	void setCommand(String value);
	String getCommand();

	void setParameter(HashMap<String, Object> values);
	HashMap<String, Object> getParameters();
		
	String generate(SessionInterface session, boolean log_query) throws Exception;
}
