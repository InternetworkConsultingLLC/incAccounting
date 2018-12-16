package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.SalesTaxesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class SalesTax extends SalesTaxesRow {
	public static String GROUP_EXEMPT_GUID = "502fd04bc5da462f98d013dfa50d808e";
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsGroup(false);
	}

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(SalesTax.class, "SalesTax.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	private List<SalesTaxMembershipOption> lstMembershipOptions;
	public List<SalesTaxMembershipOption> loadMembershipOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(adapter.getSession() == null)
			return new LinkedList<>();
		
		if(lstMembershipOptions != null && !force)
			return lstMembershipOptions;

		Statement stmt = new Statement(adapter.getSession().readJar(SalesTax.class, "SalesTax.loadMembershipOptions.sql"));	
		stmt.getParameters().put("{Group GUID}", this.getGuid());
		lstMembershipOptions = adapter.load(SalesTaxMembershipOption.class, stmt, true);
		
		return lstMembershipOptions;
	}
	public List<SalesTaxMembership> loadMembershipsFromOptions(AdapterInterface adapter) throws Exception {
		List<SalesTaxMembership> lstMembers = new LinkedList<SalesTaxMembership>();
		
		List<SalesTaxMembershipOption> lstOptions = loadMembershipOptions(adapter, false);
		for(SalesTaxMembershipOption option: lstOptions) {
			// exists		checked		action
			// T			T			none -> add
			// T			F			delete -> add
			// F			T			create -> add
			// F			F			none -> nothing
			
			SalesTaxMembership stm = null;
			try { stm = SalesTaxMembership.loadByParentAndChild(adapter, getGuid(), option.getGuid()); }
			catch(Exception ex) { }
			
			if(stm != null && option.getIsIncluded())
				lstMembers.add(stm);
			else if(stm != null && !option.getIsIncluded()) {
				stm.setIsDeleted(true);
				lstMembers.add(stm);
			} else if(stm == null && option.getIsIncluded()) {
				stm = new SalesTaxMembership();
				stm.initialize();
				stm.setParentSalesTaxesGuid(getGuid());
				stm.setChildSalesTaxesGuid(option.getGuid());
				lstMembers.add(stm);				
			}				
		}
		
		return lstMembers;
	}
	
	public void calculateTaxRate(AdapterInterface adapter) throws Exception {
		if(!getIsGroup())
			return;
		
		BigDecimal dRate = new BigDecimal(0);

		List<SalesTaxMembershipOption> lstOptions = loadMembershipOptions(adapter, false);
		for(SalesTaxMembershipOption option: lstOptions)
			if(option.getIsIncluded())
				dRate = dRate.add(option.getTaxRate());
		
		setTaxRate(dRate);
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getIsGroup() && getAccountsGuid() == null && getTaxRate().compareTo(BigDecimal.valueOf(0)) != 0)
			throw new Exception("A sales tax group must have an account!");
		
		calculateTaxRate(adapter);
	}
	
	
}
