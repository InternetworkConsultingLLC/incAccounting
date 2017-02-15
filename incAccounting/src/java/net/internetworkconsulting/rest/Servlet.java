package net.internetworkconsulting.rest;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.Helper;

public class Servlet extends HttpServlet {
	private HttpSession session;
	public User getUser() {
		return (User) session.getAttribute("Controller.User");
	}
	public void setUser(User value) {
		session.setAttribute("Controller.User", value);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		session = req.getSession(true);

		Response response = new Response();
				
		try {
			String sRequest = Helper.InputStreamToString(req.getInputStream());
			Object obj = fromXml(sRequest);
			if(obj.getClass().isAssignableFrom(Request.class)) {
				Request request = (Request) obj;
				response = request.execute(getUser());
				
				// is the request was a login on a user object and an adapter was returned,
				// set the user as logged in
				if(
					request.getFunction().equals("login") 
					&& net.internetworkconsulting.data.AdapterInterface.class.isAssignableFrom(response.getValue().getClass())
					&& net.internetworkconsulting.bootstrap.entities.User.class.isAssignableFrom(response.getModel().getClass())
				) {
					setUser((User) response.getModel());
					response.setValue("Logged in!");
				}
				
			} else
				throw new Exception("Could not locate Request object!");

			resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Content-Type", "text/xml");
			resp.setDateHeader("Expires", -1);
			resp.getWriter().write(toXml(response));
		}
		catch(Exception ex) {
			Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
			resp.getWriter().write("<error>" + ex.toString() + "</error>");
		}
		
	}
	
	public static String toXml(Object value) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(value);
		encoder.close();
		return baos.toString("UTF-8");
	}
	public static Object fromXml(String xml) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		XMLDecoder decoder = new XMLDecoder(bais);
		Object ret = decoder.readObject();
		decoder.close();
		return ret;
	}
}
