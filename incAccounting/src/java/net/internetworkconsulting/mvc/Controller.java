package net.internetworkconsulting.mvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.Helper;
import static net.internetworkconsulting.mvc.TemplateController.serialize;
import net.internetworkconsulting.template.Template;

public abstract class Controller implements ControllerInterface {
	public static String HIDDEN_CONTROL = "HiddenControl";
	public static String HIDDEN_EVENT = "HiddenEvent";
	public static String HIDDEN_MODEL = "HiddenModel";

	public Controller(ControllerInterface controller, String document_keyword) {
		myController = controller;
		setDocumentKeyword(document_keyword);

		setRequest(controller.getRequest());
		setResponse(controller.getResponse());
		setSession(controller.getSession());

		setProgramPath(controller.getProgramPath());
		setRootUrl(controller.getRootUrl());
		setSqlDatabase(controller.getSqlDatabase());
		setSqlServer(controller.getSqlServer());

		setIsSetupAllowed(controller.getIsSetupAllowed());
		isPostback = controller.getIsPostback();

		controller.getControls().add(this);
	}
	public Controller(HttpServletRequest request, HttpServletResponse response, boolean is_postback) {
		isPostback = is_postback;

		setRequest(request);
		setResponse(response);
		setSession(request.getSession());

		setSqlDatabase(request.getServletContext().getInitParameter("Database"));
		setSqlServer(request.getServletContext().getInitParameter("SQL Server"));
		setIsSetupAllowed(request.getServletContext().getInitParameter("Setup Allowed").toLowerCase().charAt(0) == 't');

		setProgramPath(request.getServletContext().getInitParameter("Program Path"));

		if(getUser() == null)
			setUser(new User());

		getUser().setProgramPath(getProgramPath());

		String url = getRequest().getScheme() + "://" + getRequest().getServerName();
		if(getRequest().getServerPort() != 80)
			url += ":" + getRequest().getServerPort();
		url += getRequest().getContextPath() + "/";
		setRootUrl(url);
	}

	public List<History> getHistory() {
		String sLen =  getUser().getSetting(History.SETTING_LENGTH);
		int iLen = 3;
		try { iLen = Integer.parseInt(sLen); } 
		catch(Exception ex) {}
		
		List<History> lstHistory = (List<History>) getSession().getAttribute("Controller.History");
		if(lstHistory == null)
			lstHistory = new LinkedList<>();
		
		while(lstHistory.size() > iLen)
			lstHistory.remove(0);

		setHistory(lstHistory);
		return lstHistory;
	}
	public void setHistory(List<History> value) { getSession().setAttribute("Controller.History", value); }
	public void addHistory(History value) { 
		List<History> lstHistory = getHistory();
		if(lstHistory.size() < 1) {
			getHistory().add(value); 
			return;
		}
			
		History last = getHistory().get(getHistory().size() - 1);
		if(!last.getUrl().equals(value.getUrl()) && !last.getName().equals(value.getName()))
			getHistory().add(value); 		
	}
	
	private ControllerInterface myController;
	public ControllerInterface getController() {
		return myController;
	}
	public void setController(ControllerInterface value) {
		myController = value;
	}

	private String sSqlDatabase;
	public String getSqlDatabase() {
		return sSqlDatabase;
	}
	public void setSqlDatabase(String value) {
		sSqlDatabase = value;
	}

	private String sSqlServer;
	public String getSqlServer() {
		return sSqlServer;
	}
	public void setSqlServer(String value) {
		sSqlServer = value;
	}

	private String sRootUrl;
	public String getRootUrl() {
		return sRootUrl;
	}
	public void setRootUrl(String value) {
		sRootUrl = value;
	}

	public User getUser() {
		return (User) getSession().getAttribute("Controller.User");
	}
	public void setUser(User value) {
		getSession().setAttribute("Controller.User", value);
	}

	private Object myModel;
	public Object getModel() {
		return myModel;
	}
	public void setModel(Object value) {
		myModel = value;
	}

	private HttpServletRequest myRequest;
	public HttpServletRequest getRequest() {
		return myRequest;
	}
	public void setRequest(HttpServletRequest value) {
		myRequest = value;
	}

	private HttpServletResponse myResponse;
	public HttpServletResponse getResponse() {
		return myResponse;
	}
	public void setResponse(HttpServletResponse value) {
		myResponse = value;
	}

	private HttpSession mySession;
	public HttpSession getSession() {
		return mySession;
	}
	public void setSession(HttpSession value) {
		mySession = value;
	}

	private String sProgramPath;
	public String getProgramPath() {
		return sProgramPath;
	}
	public void setProgramPath(String value) {
		sProgramPath = value;
	}

	private List<ControllerInterface> myControls = new LinkedList<>();
	public List<ControllerInterface> getControls() {
		return myControls;
	}
	public void setControls(List<ControllerInterface> value) {
		myControls = value;
	}
	protected void removeAllControllers(Class target) {
		HashSet<ControllerInterface> hsRemoves = new HashSet<ControllerInterface>();
		for(ControllerInterface ctrlr: this.getControls())
			if(ctrlr.getClass().getCanonicalName().equals(target.getCanonicalName()))
				hsRemoves.add( ctrlr);
		for(ControllerInterface ctrlr: hsRemoves)
			this.getControls().remove(ctrlr);
	}

	private Template myDocument;
	public Template getDocument() {
		return myDocument;
	}
	public void setDocument(Template value) {
		myDocument = value;
	}

	private String sDocumentKeyword;
	public String getDocumentKeyword() {
		return sDocumentKeyword;
	}
	public void setDocumentKeyword(String value) {
		sDocumentKeyword = value;
	}

	private boolean isDocumentBlock;
	public boolean getIsDocumentBlock() {
		return isDocumentBlock;
	}
	public void setIsDocumentBlock(boolean value) {
		isDocumentBlock = value;
	}

	private boolean isSetupAllowed = false;
	public boolean getIsSetupAllowed() {
		return isSetupAllowed;
	}
	public void setIsSetupAllowed(boolean value) {
		isSetupAllowed = value;
	}

	public HashMap<String, String> hmErrors = new HashMap<>();
	public HashMap<String, String> getErrors() { return hmErrors; }
	public void setErrors(HashMap<String, String> value) { hmErrors = value; }
	public void addError(String title, String message) {
		hmErrors.put(title, message);
	}
	
	public String sHeader = "";
	public String getHeader() { return sHeader; }
	public void setHeader(String value) { sHeader = value; }
	public void addHeader(String value) {
		setHeader(getHeader() + "\n" + value);
	}
	
	private boolean isPostback;
	public boolean getIsPostback() {
		return isPostback;
	}

	public void execute() {
		try {
			doDeserializeModel();
			doEnforceSecurity();
			doBeforeUpdate(this);
			doUpdateControls(this);
			doBeforeHandle(this);
			doHandleEvents(this);
			doBeforePopulate(this);
			doPopulateDocument(this);
			doOutput();
		} catch(Exception ex) {
			if(ex.getMessage() != null && ex.getMessage().length() > 1 && ex.getMessage().equals("redirected"))
				return;

			Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			try {
				getResponse().setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				getResponse().setHeader("Pragma", "no-cache");
				getResponse().setHeader("Content-Type", "text/html");
				getResponse().setDateHeader("Expires", -1);

				String out = "<h1>Unhandled Exception</h1>";

				getUser().setDatabase(getSqlDatabase());
				if(ex.getMessage() != null)
					out += "<p style=\"color: red;\">" + getUser().desalinate(ex.getMessage()).replace("\n", "<br />") + "</p>";

				out += "<p>An unhandled error occured while processing your request. ";
				out += "Please retry your request again, and if the problem persists, please provide the following information to support.</p>";
				out += "<h2>Exception</h2>";
				out += "<p>" + getUser().desalinate(ex.toString()).replace("\n", "<br />") + "</p>";
				out += "<h2>Stack Trace</h2>";
				out += "<ul>";
				for(StackTraceElement st: ex.getStackTrace())
					out += "<li>" + st.toString() + "</li>";
				out += "</ul>";
				getResponse().getWriter().write(out.replace("~/", getRootUrl()));
			} catch(Exception exx) { }
		}
	}

	// the following is the controllers life cycle
	// these functions should be called in this order depending on if the request is a postback or not
	// look at "ececute" for more details
	public void beforeUpdate() throws Exception { }
	public void updateControls() throws Exception { } // all - used by Tags
	public void beforeHandle() throws Exception { }
	public void handleEvents() throws Exception { } // post - used by Tags
	public void beforePopulate() throws Exception { }
	public void populateDocument() throws Exception { // all - generic for non-tags
		if(getController() == null)
			return;

		if(getDocument() == null)
			throw new Exception("You must declare a document in '" + this.getClass().getName() + "', or inherit the parent controlers document!");

		if(!getIsDocumentBlock())
			getController().getDocument().set(getDocumentKeyword(), getDocument().generate());
		else {
			getDocument().touch(getDocumentKeyword());
			getDocument().parse(getDocumentKeyword());
		}
	}

	// helpers
	public String read(String file_name) throws Exception {
		if(file_name.contains(".."))
			throw new Exception("Cannot use file names with 'parent folders' (..) in them!");

		// program path is multiple folders, break into array
		String[] sFolders = getProgramPath().split("\\" + File.pathSeparator);

		// search each folder, taking the first file found
		String sTargetFile = "";
		for(String sFolder: sFolders) {
			String sFile = sFolder + java.io.File.separator + file_name;
			// remove duplicate path seperators
			String sNew = sFile.replace(java.io.File.separator + java.io.File.separator, java.io.File.separator);
			while(!sNew.equals(sFile)) {
				sFile = sNew;
				sNew = sFile.replace(java.io.File.separator + java.io.File.separator, java.io.File.separator);
			}
			// check if file exists
			File file = new File(sNew);
			if(file.exists()) {
				sTargetFile = sNew;
				break;
			}
		}

		// check if file was found
		File file = new File(sTargetFile);
		if(!file.exists())
			throw new Exception(
					"Could not locate a file in the programs path!\n"
					+ "File: " + file_name + "\n"
					+ "Path: " + getProgramPath()
			);

		// read the file
		try {
			return Helper.FileToString(sTargetFile);
		} catch(Exception ex) {
			throw new Exception("Could not locate file '" + file_name + "'!", ex);
		}
	}
	public void redirect(String url) throws Exception {
		getResponse().sendRedirect(url.replace("~/", getRootUrl()));
		getResponse().flushBuffer();
		throw new Exception("redirected");
	}

	// private methods
	protected static String serialize(Object value) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(value);
		return Helper.ByteArrayToHex(baos.toByteArray());
	}
	protected static Object deserialize(String object) throws Exception {
		if(object == null)
			return null;

		byte[] binObject = Helper.HexToByteArray(object);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(binObject));
		Object objRet = ois.readObject();
		ois.close();
		return objRet;
	}
	protected static String getCamelCase(String input) throws Exception {
		if(input.length() <= 1)
			return "";
			//throw new Exception("The requested string is empty!");

		String sInput = input.replace("_", " ");

		char cLast = ' ';
		String sRet = "";
		for(int cnt = 0; cnt < sInput.length(); cnt++) {
			char cCurrent = sInput.charAt(cnt);
			boolean bIsCurrentValid = Character.isUpperCase(cCurrent) || Character.isLowerCase(cCurrent) || Character.isDigit(cCurrent);
			boolean bIsLastValid = Character.isUpperCase(cLast) || Character.isLowerCase(cLast) || Character.isDigit(cLast);
			boolean bClassChanged = Character.isLowerCase(cLast) != Character.isLowerCase(cCurrent)
					|| Character.isUpperCase(cLast) != Character.isUpperCase(cCurrent)
					|| Character.isDigit(cLast) != Character.isDigit(cCurrent)
					|| bIsCurrentValid != bIsLastValid;
			boolean bChangedToLower = Character.isUpperCase(cLast) && Character.isLowerCase(cCurrent);

			if(bIsCurrentValid && bClassChanged && !bChangedToLower)
				sRet += Character.toUpperCase(cCurrent);
			else if(bIsCurrentValid && bClassChanged && bChangedToLower)
				sRet += Character.toLowerCase(cCurrent);
			else if(bIsCurrentValid && !bClassChanged)
				sRet += Character.toLowerCase(cCurrent);

			cLast = sInput.charAt(cnt);
		}

		return sRet;
	}

	// recurisve handling of lifecycle
	// this is done only in the controller which "execute" was called
	// this will process the executed controller, and all of the controller contained in "getControls"
	private boolean bHistoryFound = false;
	protected void doDeserializeModel() throws Exception {
		if(getIsPostback())
			setModel(deserialize(getRequest().getParameter(Controller.HIDDEN_MODEL)));
	}
	protected void doEnforceSecurity() throws Exception {
			boolean bEnforceSecurity = doCreateControls(this, true);
			if(bEnforceSecurity) {
				if(getUser() == null || getUser().getGuid() == null || getUser().getGuid().length() != 32)
					redirect("~/");
				if(getUser().getPassword().toLowerCase().contains("welcome") || getUser().isPasswordExpired(getUser().login()))
					redirect("~/incBootstrap?App=ChangePassword");
				if(!getUser().getIsAllowed())
					throw new Exception("This user is not authorized!");
				getUser().checkVersion();
			}
	}
	protected boolean doCreateControls(ControllerInterface current, boolean enforced) throws Exception {
		boolean bEnforceSecurity = false;
		
		bEnforceSecurity = bEnforceSecurity || current.getEnforceSecurity() || !enforced;
		
		if(current.getController() != null)
			current.createControls(current.getController().getDocument(), current.getController().getModel());
		else
			current.createControls(null, null);
		
		History history = current.createHistory();
		if(history != null && !bHistoryFound) {
			addHistory(history);
			addHeader("<title>" + history.getName() + "</title>");
		}

		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			boolean bRet = doCreateControls(ci, true);
			bEnforceSecurity = bEnforceSecurity || bRet;
		}
		
		return bEnforceSecurity;
	}
	protected void doBeforeUpdate(ControllerInterface current) throws Exception {
		if(!getIsPostback())
			return;

		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doBeforeUpdate(ci);
		}

		current.beforeUpdate();
	}
	protected void doUpdateControls(ControllerInterface current) throws Exception {
		if(!getIsPostback())
			return;
		
		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doUpdateControls(ci);
		}

		current.updateControls();
	}
	protected void doBeforeHandle(ControllerInterface current) throws Exception {
		if(!getIsPostback())
			return;

		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doBeforeHandle(ci);
		}

		current.beforeHandle();
	}
	protected void doHandleEvents(ControllerInterface current) throws Exception {
		if(!getIsPostback())
			return;

		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doHandleEvents(ci);
		}

		current.handleEvents();
	}
	protected void doBeforePopulate(ControllerInterface current) throws Exception {
		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doBeforePopulate(ci);
		}

		current.beforePopulate();
	}
	protected void doPopulateDocument(ControllerInterface current) throws Exception {
		HashMap<String, String> hmReturn = new HashMap<>();
		
		for(int cnt = 0; cnt < current.getControls().size(); cnt++) {
			ControllerInterface ci = current.getControls().get(cnt);
			doPopulateDocument(ci);
		}

		if(current != this) {
			getErrors().putAll(current.getErrors());
			setHeader(getHeader() + current.getHeader());
		}
		
		current.populateDocument();
	}
	protected void doOutput() throws Exception {
		getUser().setDatabase(getSqlDatabase());
		String sMessage = getRequest().getParameter("Error");
		if(sMessage != null)
			addError("Notification", sMessage);
		sMessage = "";
		for(String key: getErrors().keySet()) {
			sMessage += "<p class=\"error\"><b>" + getUser().desalinate(key) + "</b><br/>" + getUser().desalinate(getErrors().get(key)) + "</p>";
		}
		getDocument().set("Error", sMessage);
		
		String sHiddenModel = "<input type=\"hidden\" name=\"HiddenModel\" value=\"%VALUE%\" />";
		sHiddenModel += "<input type=\"hidden\" id=\"HiddenControl\" name=\"HiddenControl\" value=\"\" />";
		sHiddenModel += "<input type=\"hidden\" id=\"HiddenEvent\" name=\"HiddenEvent\" value=\"\" />";
		sHiddenModel = sHiddenModel.replace("%VALUE%", serialize(getModel()));
		
		getDocument().set("Hidden Model", sHiddenModel);
		getDocument().set("Time Stamp", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(java.util.Date.from(Instant.now())));
		
		if(getRequest().getParameter("HiddenControl") != null)
			getDocument().set("OnLoad", "onload=\"setFocusAfter('" +  getCamelCase(getRequest().getParameter("HiddenControl")) + "')\"");
		
		String sApp = getRequest().getParameter("App");
		String sCssFile = "~/css/" + sApp + ".css";
		addHeader("<link rel=\"stylesheet\" href=\"" + sCssFile + "\"/>");
		String sScriptFile = "~/scripts/" + sApp + ".js";
		addHeader("<script src=\"" + sScriptFile + "\" ></script>");
		getDocument().set("Header", getHeader());
		
		if(getUser() != null && getUser().getDisplayName() != null)
			getDocument().set("User", getUser().getDisplayName());
		else
			getDocument().set("User", "");
		
		List<History> lstHistory = getHistory();
		String sHistory = "";
		for(History hist: lstHistory)
			sHistory += "<a href=\"" + hist.getUrl() + "\">" + hist.getName() + "</a> &gt; ";
		if(sHistory.length() > 0)
			sHistory = sHistory.substring(0, sHistory.length() - 6).trim();
		getDocument().set("History", sHistory);
		
		String sOutput = getDocument().generate();
		sOutput = sOutput.replace("~/", getRootUrl());
		
		getResponse().setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		getResponse().setHeader("Pragma", "no-cache");
		getResponse().setHeader("Content-Type", "text/html");
		getResponse().setDateHeader("Expires", -1);
		getResponse().getWriter().write(sOutput);
	}
}
