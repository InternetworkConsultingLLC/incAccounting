package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ItemPostingsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setIncreasingDocumentLinesGuid(java.lang.String value) throws Exception;
	java.lang.String getIncreasingDocumentLinesGuid();
	
	boolean setDecreasingDocumentLinesGuid(java.lang.String value) throws Exception;
	java.lang.String getDecreasingDocumentLinesGuid();
	
	boolean setDecreaseQuantity(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getDecreaseQuantity();
	
	
	
	<T extends DocumentLinesRow> T loadIncreasingDocumentLine(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DocumentLinesRow> T loadDecreasingDocumentLine(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
