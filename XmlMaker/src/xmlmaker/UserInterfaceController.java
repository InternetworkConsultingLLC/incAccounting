package xmlmaker;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.rest.Request;
import net.internetworkconsulting.rest.Servlet;

public class UserInterfaceController implements Initializable {
	public TextArea txtXml;
	public Button btnReverse;
	
	public void initialize(URL url, ResourceBundle rb) {
		try {
			net.internetworkconsulting.bootstrap.rest.Servlet servlet = new net.internetworkconsulting.bootstrap.rest.Servlet();
			
			net.internetworkconsulting.rest.Request request = new Request();
			request.setFunction("login");
			//request.setContext(net.internetworkconsulting.bootstrap.entities.Setting.class.getName());
			
			LinkedList<Object> lstParams = new LinkedList<>();
			lstParams.add(true);
			request.setParameters(lstParams.toArray());
			
			User user = new User();
			user.setSqlUser("administrator");
			user.setPassword("Welcome123");
			user.setSqlServer("localhost");
			user.setDatabase("ibs");
			request.setModel(user);
			
			
			try {
				txtXml.setText(Servlet.toXml(request).replace("\"", "\\\"").replace(">\n", ">").replace(">\r", ">"));
			}
			catch(Exception ex) {
				Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			btnReverse.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() { public void handle(MouseEvent event) { btnReverse_OnClicked(); } });
		}
		catch(Exception ex) {
			Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void btnReverse_OnClicked() {
//		try {
//			Object obj = net.internetworkconsulting.xml.Serializer.fromXml(txtXml.getText());
//		}
//		catch(Exception ex) {
//			Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}
}
