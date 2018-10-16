package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import net.internetworkconsulting.accounting.data.PayrollFieldValuesRow;
import net.internetworkconsulting.data.AdapterInterface;

public class PayrollFieldValue extends PayrollFieldValuesRow {
	public void initialize() throws Exception { throw new Exception("You must provide a payroll check to initialize!"); }
	public void initialize(PayrollCheck check) throws Exception {
		this.setGuid(User.newGuid());
		this.setPayrollCheck(check);
		this.setPayrollChecksGuid(check.getGuid());
		this.setQuantity(BigDecimal.ONE);
		this.setRate(BigDecimal.ZERO);
		this.setTotal(BigDecimal.ZERO);
	}	

	private PayrollCheck objPayrollCheck = null;
	public void setPayrollCheck(PayrollCheck value) {
		objPayrollCheck = value;
	}
	
	private boolean bIsGrossExpense = false;
	public void setIsGrossExpense(boolean value) { bIsGrossExpense = value; }
	public boolean getIsGrossExpense(AdapterInterface adapter) throws Exception {
		if(this.getPayrollFieldsGuid() == null)
			return bIsGrossExpense;
		
		PayrollField pf = this.loadPayrollField(adapter, PayrollField.class, false);
		return pf.getPayrollFieldTypesGuid() != null && pf.getPayrollFieldTypesGuid().equals(PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);
	}
	
	public void calculate(AdapterInterface adapter) throws Exception {
		if(getRate() == null || getQuantity() == null)
			setTotal(BigDecimal.ZERO);
		
		BigDecimal dValue = Document.round(adapter, getQuantity().multiply(getRate()), Document.SETTING_MONEY_DECIMALS);
		setTotal(dValue);
	}
}
