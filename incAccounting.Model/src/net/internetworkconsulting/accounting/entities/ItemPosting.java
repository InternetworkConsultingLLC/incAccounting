package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.accounting.data.ItemPostingsRow;
import net.internetworkconsulting.bootstrap.entities.User;

public class ItemPosting extends ItemPostingsRow {
	public void initialize() throws Exception { 
		setGuid(User.newGuid());
	}
}
