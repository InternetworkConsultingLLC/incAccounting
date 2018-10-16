package net.internetworkconsulting.accounting.mvc;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.internetworkconsulting.accounting.entities.Setup;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SetupController extends Controller {
	private HashMap<String, Node> hmValues;
	public SetupController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	List<SetupScriptsController> lstScripts = new LinkedList<>();

	public Setup newModel() { return new Setup(); }
	public History createHistory() throws Exception { 
		return new History("Setup", getRequest(), getUser()); 
	}
	
	public void createControls(Template document, Object model) throws Exception {
		hmValues = loadConfig();				

		setDocument(new Template(readTemplate("~/templates/Setup.html"), new HtmlSyntax()));

		boolean bConfigurable = (boolean) Statement.parseStringToValue(boolean.class, hmValues.get("configEditable").getTextContent());
		if(!bConfigurable)
			throw new Exception("You must change the 'web.xml' so that 'configEditable' is 'true'.");
		
		Setup objModel = (Setup) model;
		if(!getIsPostback()) {
			objModel = newModel();

			objModel.setSqlUser(hmValues.get("dbUser").getTextContent());		
			objModel.setSqlServer(hmValues.get("dbServer").getTextContent());
			objModel.setPassword(hmValues.get("dbPassword").getTextContent());
		}
		setModel(objModel);			
		
		TextTag txtSqlUser = new TextTag(this, Setup.SQL_USER);
		txtSqlUser.bind(getModel(), Setup.SQL_USER);

		TextTag txtPassword = new TextTag(this, Setup.PASSWORD);
		txtPassword.bind(getModel(), Setup.PASSWORD);

		TextTag txtSqlServer = new TextTag(this, Setup.SQL_SERVER);
		txtSqlServer.bind(getModel(), Setup.SQL_SERVER);

		TextTag txtDatabase = new TextTag(this, Setup.DATABASE);
		txtDatabase.bind(getModel(), Setup.DATABASE);

		TextTag txtAdministratorPassword = new TextTag(this, Setup.ADMINISTRATOR_PASSWORD);
		txtAdministratorPassword.bind(getModel(), Setup.ADMINISTRATOR_PASSWORD);
		
		List<String> lstFiles = objModel.getScripts();
		for(String file: lstFiles) {
			SetupScriptsController ssc = new SetupScriptsController(this, "Script");
			ssc.setModel(file);
			ssc.setIsDocumentBlock(true);
			lstScripts.add(ssc);
		}

		ButtonTag btnTestConnection = new ButtonTag(this, "Test Connection");
		btnTestConnection.setValue("Test Connection");
		btnTestConnection.addOnClickEvent(new Event() { public void handle() throws Exception { btnTestConnection_OnClick(); } });

		ButtonTag btnDropDatabase = new ButtonTag(this, "Drop Database");
		btnDropDatabase.setValue("Drop Database");
		btnDropDatabase.addOnClickEvent(new Event() { public void handle() throws Exception { btnDropDatabase_OnClick(); } });

		ButtonTag btnCreateDatabase = new ButtonTag(this, "Create Database");
		btnCreateDatabase.setValue("Create Database");
		btnCreateDatabase.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateDatabase_OnClick(); } });

		ButtonTag btnCreateAdministrator = new ButtonTag(this, "Create Administrator");
		btnCreateAdministrator.setValue("Create Administrator");	
		btnCreateAdministrator.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateAdministrator_OnClick(); } });
	}
	private void btnTestConnection_OnClick() throws Exception {
		String msg = "";
		
		Setup model = (Setup) getModel();
		model.testConnection();
		msg = msg + "  Your connection was successful!  ";

		hmValues.get("dbServer").setTextContent(model.getSqlServer());
		hmValues.get("dbUser").setTextContent(model.getSqlUser());
		hmValues.get("dbPassword").setTextContent(model.getPassword());
		saveConfig();		
		msg = msg + "  System configuration has been updated - restart the application for the changesto take effect.  ";

		addError("Connection", msg);
	}
	private void btnDropDatabase_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.dropDatabase();
		addError("Droped Database", "Your database has been deleted!");
	}
	private void btnCreateDatabase_OnClick() throws Exception {
		List<String> lstFiles = new LinkedList<>();
		for(SetupScriptsController ssc : lstScripts) {
			if(ssc.getCheckTag().getIsChecked())
				lstFiles.add((String) ssc.getModel());
		}
		
		Setup model = (Setup) getModel();
		model.createDatabase(lstFiles);
		addError("Created Database", "You database has been created.");
	}
	private void btnCreateAdministrator_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.createAdministrator();
		addError("Administrator Created", "The administrator account has been removed and recreated.");
	}
	
	private Document doc = null;
	private HashMap<String, Node> loadConfig() throws Exception {
		String sFile = ("~/WEB-INF/web.xml").replace("~/", getContext().getRealPath("/"));		
		File fXmlFile = new File(sFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(fXmlFile);
		
		NodeList nodes = doc.getElementsByTagName("env-entry");
		HashMap<String, Node> hmValues = new HashMap<>();
		for(int entryCnt = 0; entryCnt < nodes.getLength(); entryCnt++) {
			Node currEntry = nodes.item(entryCnt);
			NodeList children = currEntry.getChildNodes();
			
			String sChildName = "";
			Node nChildValue = null;
			for(int childCnt = 0; childCnt < children.getLength(); childCnt++) {
				Node currChild = children.item(childCnt);
				if(("env-entry-name").equals(currChild.getNodeName()))
					sChildName = currChild.getTextContent();
				else if(("env-entry-value").equals(currChild.getNodeName()))
					nChildValue = currChild;
			}
			
			hmValues.put(sChildName, nChildValue);			
		}
		return hmValues;
	}
	private void saveConfig() throws Exception {
		DOMSource domSource = new DOMSource(doc);
		
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);

		String sFile = ("~/WEB-INF/web.xml").replace("~/", getContext().getRealPath("/"));			
		PrintWriter pw = new PrintWriter(sFile);
		pw.write(writer.toString());
		pw.close();
	}
}
