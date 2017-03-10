/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
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
	
	public void calculate() throws Exception {
		if(getRate() != null && getQuantity() != null)
			setTotal(getQuantity().multiply(getRate()));
	}
}
