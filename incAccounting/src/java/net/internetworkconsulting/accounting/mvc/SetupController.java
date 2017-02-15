package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Setup;
import net.internetworkconsulting.mvc.ControllerInterface;

public class SetupController extends net.internetworkconsulting.bootstrap.mvc.SetupController {
	public SetupController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }	
	public Setup newModel() { return new Setup(); }	
 }
