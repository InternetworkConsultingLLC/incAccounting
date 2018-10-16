package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Calendar;
import net.internetworkconsulting.accounting.data.TimeEntriesRow;

public class TimeEntry extends TimeEntriesRow {
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setStarted(new Date(Calendar.getInstance().getTimeInMillis()));
		this.setEntryTypesGuid(TimeEntryType.INCOMPLETE);
	}
	public BigDecimal getHours() {
		BigDecimal bd = new BigDecimal(this.getEnded().getTime() - this.getStarted().getTime());
		bd = bd.divide(new BigDecimal(3600000), 2, RoundingMode.HALF_UP);
		return bd;
	}
}
