package net.internetworkconsulting.accounting.entities;

import java.sql.Timestamp;
import java.time.Instant;
import net.internetworkconsulting.accounting.data.ContactNotesRow;

public class ContactNote extends ContactNotesRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setDated(new Timestamp(Instant.now().toEpochMilli()));
	}
	
}
