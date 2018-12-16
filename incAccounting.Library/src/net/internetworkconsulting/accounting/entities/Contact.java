package net.internetworkconsulting.accounting.entities;

import java.util.Date;
import java.time.Instant;
import java.util.List;
import net.internetworkconsulting.accounting.data.ContactsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Contact extends ContactsRow {
	public static String OUR_COMPANY_GUID = "fabca02484aa46eaa24c939c64779a2d";

	private Object lstChildrenContactsChildren = null;
	public <T extends ContactsRow> List<T> loadChildrenContacts(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenContactsChildren == null || force) {
			Statement stmt = new Statement("SELECT * FROM \"Contacts\" WHERE \"Parent Contacts GUID\"={PRIMARYKEY} ORDER BY \"Display Name\"");
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenContactsChildren = adapter.load(model, stmt, true);
		}
		return (List<T>) lstChildrenContactsChildren;
	}

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Contact.class, "Contact.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public List<Option> loadChildOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Contact.class, "Contact.loadChildOptions.sql"));		
		stmt.getParameters().put("{GUID}", this.getGuid());
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setContactSince(new Date(Instant.now().toEpochMilli()));
		this.setIsAllowed(true);
	}
	public void initialize(Contact parent) throws Exception {
		this.initialize();
		this.setParentContactsGuid(parent.getGuid());
		this.setCity(parent.getCity());
		this.setCountry(parent.getCountry());
		this.setLine1(parent.getLine1());
		this.setLine2(parent.getLine2());
		this.setPostalCode(parent.getPostalCode());
		this.setState(parent.getState());
		this.setTaxGroupGuid(parent.getTaxGroupGuid());
	}
}
