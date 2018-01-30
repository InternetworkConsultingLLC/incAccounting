package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.JobsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Job extends JobsRow {

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);		
	}

	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(Job.class, "Job.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		Job parent;

		// construct account number in the form of:
		// parent.segment +  "-" + ... +  "-" parent.segment +  "-" + this.segment
		String sSeperator = Account.getSeperator(adapter);
		
		// prime the loop
		if(this.getParentJobsGuid() == null)
			parent = null;
		else
			parent = this.loadParentJob(adapter, Job.class, false);
		
		// loop to root
		String sNumber = getSegment();
		String sName = getName();
		while(parent != null) {
			sNumber = parent.getSegment() + sSeperator + sNumber;
			sName = parent.getName() + " - " + sName;
			if(parent.getParentJobsGuid() == null)
				parent = null;
			else
				parent = parent.loadParentJob(adapter, Job.class, false);
		}
		
		// set computed number
		this.setNumber(sNumber);
		this.setNestedName(sName);
	}
}
