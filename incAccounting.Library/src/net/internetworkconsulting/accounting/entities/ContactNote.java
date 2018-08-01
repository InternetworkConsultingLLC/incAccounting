package net.internetworkconsulting.accounting.entities;

import java.util.Date;
import java.time.Instant;
import net.internetworkconsulting.accounting.data.ContactNotesRow;

public class ContactNote extends ContactNotesRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setDated(new Date(Instant.now().toEpochMilli()));
	}
	
}
