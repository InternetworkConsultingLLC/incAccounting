/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface JobsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setParentJobsGuid(java.lang.String value) throws Exception;
	java.lang.String getParentJobsGuid();
	
	boolean setSegment(java.lang.String value) throws Exception;
	java.lang.String getSegment();
	
	boolean setNumber(java.lang.String value) throws Exception;
	java.lang.String getNumber();
	
	boolean setName(java.lang.String value) throws Exception;
	java.lang.String getName();
	
	boolean setNestedName(java.lang.String value) throws Exception;
	java.lang.String getNestedName();
	
	boolean setIsAllowed(java.lang.Boolean value) throws Exception;
	java.lang.Boolean getIsAllowed();
	
	boolean setContactsGuid(java.lang.String value) throws Exception;
	java.lang.String getContactsGuid();
	
	
	<T extends DocumentLinesRow> List<T> loadDocumentLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends JobsRow> List<T> loadChildren(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends TransactionLinesRow> List<T> loadTransactionLines(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	
	<T extends JobsRow> T loadParentJob(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends ContactsRow> T loadContact(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
