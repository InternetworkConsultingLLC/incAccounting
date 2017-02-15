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

import java.util.List;
import net.internetworkconsulting.accounting.data.PaymentApplicationsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PaymentApplication extends PaymentApplicationsRow{
	static PaymentApplication loadByDocumentAndPayment(AdapterInterface adapter, String documentsGuid, String paymentsGuid) throws Exception {
		String sql = "SELECT * FROM \"TABLE\" WHERE \"COL1\"={Documents GUID} AND \"COL2\"={Payments GUID}" ;
		sql = sql.replace("TABLE", PaymentApplication.TABLE_NAME);
		sql = sql.replace("COL1", PaymentApplication.DOCUMENTS_GUID);
		sql = sql.replace("COL2", PaymentApplication.PAYMENTS_GUID);
		
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{Documents GUID}", documentsGuid);
		stmt.getParameters().put("{Payments GUID}", paymentsGuid);
		
		List<PaymentApplication> lst = adapter.load(PaymentApplication.class, stmt);
		if(lst.size() != 1)
			throw new Exception("Could not locate a unique payment application by document and payments GUID!");
		
		return lst.get(0);
	}
	
}
