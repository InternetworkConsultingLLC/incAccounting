/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PaymentApplicationsInterface {
	
	boolean setPaymentsGuid(java.lang.String value) throws Exception;
	java.lang.String getPaymentsGuid();
	
	boolean setDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDocumentsGuid();
	
	boolean setAmount(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getAmount();
	
	
	
	<T extends PaymentsRow> T loadPayment(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentsRow> T loadDocument(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
