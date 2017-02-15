package net.internetworkconsulting.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Servlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doAction(req, resp, false); }
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doAction(req, resp, true); }

	protected abstract void doAction(HttpServletRequest req, HttpServletResponse resp, boolean is_postback) throws ServletException, IOException;
}
