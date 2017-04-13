package net.internetworkconsulting.generator.MySQL;

import java.util.LinkedList;
import java.util.List;

public class Family {
	public String Name;
	public List<Family> Children = new LinkedList<Family>();
}
