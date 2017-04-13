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
		
		List<PaymentApplication> lst = adapter.load(PaymentApplication.class, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate a unique payment application by document and payments GUID!");
		
		return lst.get(0);
	}
	
}
