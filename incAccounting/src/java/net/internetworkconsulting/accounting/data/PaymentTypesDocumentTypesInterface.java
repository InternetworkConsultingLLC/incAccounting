package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface PaymentTypesDocumentTypesInterface {
	
	boolean setDocumentTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getDocumentTypesGuid();
	
	boolean setPaymentTypesGuid(java.lang.String value) throws Exception;
	java.lang.String getPaymentTypesGuid();
	
	
	
	<T extends DocumentTypesRow> T loadDocumentTypes(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends PaymentTypesRow> T loadPaymentType(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
