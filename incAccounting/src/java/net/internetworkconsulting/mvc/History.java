package net.internetworkconsulting.mvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.internetworkconsulting.data.SessionInterface;

public class History {
	static String SETTING_LENGTH = "History Length";
	
	public History(String name, HttpServletRequest req, SessionInterface session) throws Exception {
		if(name == null || name.length() < 1)
			throw new Exception("You must supply a history name!");
		
		setName(name);
		String url = req.getRequestURL().toString() + "?" + req.getQueryString();
		
//		String sQuery = "";
//		Map<String, String[]> mapParams = req.getParameterMap();
//		for(String key: mapParams.keySet()) {
//			if(!key.toLowerCase().equals("error"))
//				for(String value: mapParams.get(key))
//					sQuery = sQuery + "&" +  escapeUrl(key) + "=" + escapeUrl(value);
//		}
//		if(sQuery.length() > 0)
//			url = url + "?" + sQuery.substring(1);
		
		setUrl(url);
		
		session.logEvent("History: " + name + "  \n" + url, "c40c2fccc11147ee8a8020fd97ea8d3d");
	}
	
	private String sName;
	public void setName(String value) { sName = value; }
	public String getName() { return sName; }

	private String sUrl;
	public void setUrl(String value) { sUrl = value; }
	public String getUrl() { return sUrl; }
	
	public static String escapeUrl(String input) {
		String ret = "";
		
		for(char c: input.toCharArray()) {
			if(Character.isLetterOrDigit(c))
				ret = ret + c;
			else
				ret = ret + "%" + String.format("%02X", ((int) c));
		}
		
		return ret;
	}
	
	
}
