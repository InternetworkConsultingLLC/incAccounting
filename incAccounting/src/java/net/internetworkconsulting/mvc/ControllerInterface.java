package net.internetworkconsulting.mvc;

import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.template.Template;

public interface ControllerInterface {
	ControllerInterface getController();
	void setController(ControllerInterface value);
	boolean getEnforceSecurity();

	List<History> getHistory();
	void setHistory(List<History> value);
	void addHistory(History value);
	
	User getUser();
	void setUser(User value);
	
	Object getModel();
	void setModel(Object value);
	
	HttpServletRequest getRequest();
	void setRequest(HttpServletRequest value);
	
	HttpServletResponse getResponse();
	void setResponse(HttpServletResponse value);

	ServletContext getContext();
	void setContext(ServletContext value);
	
	String getRootUrl();
	void setRootUrl(String value);
	
	HttpSession getSession();
	void setSession(HttpSession value);
	
	List<ControllerInterface> getControls();
	void setControls(List<ControllerInterface> value);
	
	Template getDocument();
	void setDocument(Template value);
	
	String getDocumentKeyword();
	void setDocumentKeyword(String value);
	
	boolean getIsDocumentBlock();
	void setIsDocumentBlock(boolean value);

	boolean getIsPostback();	
	
	HashMap<String, String> getErrors();
	void setErrors(HashMap<String, String> value);
	void addError(String title, String message);

	String getHeader();
	void setHeader(String value);
	
	// The following is the controllers life
	// cycle.  These functions should be called
	// in this order depending on if the request
	// is a postback or not.
	
	// only for top level controller
	void execute();
	
	// all: top level and sub-controllers
	void createControls(Template document, Object model) throws Exception; // get
	History createHistory() throws Exception; 
	void beforeUpdate() throws Exception; // post
	void updateControls() throws Exception; // post
	void beforeHandle() throws Exception; // post
	void handleEvents() throws Exception; // post
	void beforePopulate() throws Exception;
	void populateDocument() throws Exception;
	
	// helpers
	String readTemplate(String file_name) throws Exception;
	void redirect(String url) throws Exception;
}