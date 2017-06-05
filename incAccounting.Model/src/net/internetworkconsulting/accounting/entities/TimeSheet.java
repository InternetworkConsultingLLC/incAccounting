package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.TimeEntriesRow;
import net.internetworkconsulting.accounting.data.TimeSheetsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class TimeSheet extends TimeSheetsRow {
	private static List<Option> lstOptions = null;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readJar(Document.class, "TimeSheet.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		//this.setPeriodEnding(new Date(Calendar.getInstance().getTimeInMillis()));
	}

	private List lstTimeEntries;
	public <T extends TimeEntriesRow> List<T> loadTimeEntries(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstTimeEntries == null || force) {
			String sql = adapter.getSession().readJar(TimeSheet.class, "TimeSheet.loadTimeEntries.sql");
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("@EmployeesGUID", this.getEmployeesGuid());
			stmt.getParameters().put("@TimeSheetsGUID", this.getGuid());
			stmt.getParameters().put("@Starting", this.getPeriodStarting());
			stmt.getParameters().put("@Ending", this.getPeriodEnding());
			
			lstTimeEntries = adapter.load(model, stmt, true);
		}

		return (List<T>) lstTimeEntries;
	}
	public void clearTimEntries() { lstTimeEntries = null; }
	
	public void calculate(List<TimeEntry> lstEntries) throws Exception {
		List<TimeEntry> lstSpans = new LinkedList<>();
		for(TimeEntry te : lstEntries)
			if(te.getTimeSheetsGuid() != null && te.getTimeSheetsGuid().equals(this.getGuid()))
				lstSpans.add(te);
		
		int iCount = -1;
		while(iCount != lstSpans.size()) {
			iCount = lstSpans.size();
			lstSpans = combineTimeEntries(lstSpans);
		}
			
		BigDecimal dTotals = BigDecimal.ZERO;
		for(TimeEntry te : lstSpans)
			dTotals = dTotals.add(BigDecimal.valueOf(te.getEnded().getTime() - te.getStarted().getTime()));
		
		dTotals = dTotals.divide(BigDecimal.valueOf(60 * 60 * 1000), 2, RoundingMode.HALF_UP);
		
		this.setTotalHours(dTotals);
	}
	private List<TimeEntry> combineTimeEntries(List<TimeEntry> lstOriginals) throws Exception {
		List<TimeEntry> lstCombined = new LinkedList<>();
		for(TimeEntry teOriginal : lstOriginals) {
			boolean bHandled = false;
			for(TimeEntry teCombined : lstCombined) {
				long lBufferedStarted = teCombined.getStarted().getTime() - (5 * 60 * 1000);
				long lBufferedEnded = teCombined.getEnded().getTime() + (5 * 60 * 1000);
				if(teOriginal.getStarted().getTime() < lBufferedEnded && teOriginal.getEnded().getTime() > lBufferedStarted) {
					if(teOriginal.getStarted().getTime() < teCombined.getStarted().getTime())
						teCombined.setStarted(teOriginal.getStarted());
					if(teOriginal.getEnded().getTime() > teCombined.getEnded().getTime())
						teCombined.setEnded(teOriginal.getEnded());
					bHandled = true;
				}
			}
			if(!bHandled) {
				TimeEntry te = new TimeEntry();
				te.initialize();
				te.setStarted(teOriginal.getStarted());
				te.setEnded(teOriginal.getEnded());
				lstCombined.add(te);
			}
		}
		
		return lstCombined;
	}
}
