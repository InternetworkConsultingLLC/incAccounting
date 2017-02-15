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
import java.sql.Date;

public class PaymentApplicationSelection extends PaymentApplication {
	public static String IS_CREDIT_MEMO = "Is Credit Memo";
	public void getIsCreditMemo(boolean value) throws Exception { 
		set(IS_CREDIT_MEMO, value); 
	}
	public boolean getIsCreditMemo() {
		return (boolean) get(IS_CREDIT_MEMO);
	}
	
	public static String NAME = "Name";
	public void setName(String value) throws Exception { set(NAME, value); }
	public String getName() { return (String) get(NAME); }
	
	public static String DATE = "Date";
	public void setDate(Date value) throws Exception { set(DATE, value); }
	public Date getDate() { return (Date) get(DATE); }
	
	public static String REFERENCE_NUMBER = "Reference Number";
	public void setReferenceNumber(String value) throws Exception { set(REFERENCE_NUMBER, value); }
	public String getReferenceNumber() { return (String) get(REFERENCE_NUMBER); }
	
	public static String CUSTOMER_NUMBER = "Customer Number";
	public void getCustomerNumber(String value) throws Exception { set(CUSTOMER_NUMBER, value); }
	public String getCustomerNumber() { return (String) get(CUSTOMER_NUMBER); }
	
	public static String BALANCE = "Balance";
	public void setBalance(BigDecimal value) throws Exception { set(BALANCE, value); }
	public BigDecimal getBalance() { return (BigDecimal) get(BALANCE); }
}
