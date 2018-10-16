package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface DocumentLinesInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setDocumentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDocumentsGuid();
	
	boolean setSortOrder(java.lang.Integer value) throws Exception;
	java.lang.Integer getSortOrder();
	
	boolean setQuantity(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getQuantity();
	
	boolean setItemsGuid(java.lang.String value) throws Exception;
	java.lang.String getItemsGuid();
	
	boolean setUnitMeasuresGuid(java.lang.String value) throws Exception;
	java.lang.String getUnitMeasuresGuid();
	
	boolean setDescription(java.lang.String value) throws Exception;
	java.lang.String getDescription();
	
	boolean setUnitPrice(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getUnitPrice();
	
	boolean setExtension(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getExtension();
	
	boolean setIsTaxed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsTaxed();
	
	boolean setIsBackwardsCalculated(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsBackwardsCalculated();
	
	boolean setJobsGuid(java.lang.String value) throws Exception;
	java.lang.String getJobsGuid();
	
	boolean setDepartmentsGuid(java.lang.String value) throws Exception;
	java.lang.String getDepartmentsGuid();
	
	boolean setAccountsGuid(java.lang.String value) throws Exception;
	java.lang.String getAccountsGuid();
	
	
	<T extends ItemPostingsRow> List<T> loadDecreasingItemPostings(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemPostingsRow> List<T> loadIncreasingItemPostings(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends DocumentsRow> T loadDocument(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ItemsRow> T loadItem(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends UnitMeasuresRow> T loadUnitMeasure(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends JobsRow> T loadJob(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends DepartmentsRow> T loadDepartment(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends AccountsRow> T loadAccount(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
