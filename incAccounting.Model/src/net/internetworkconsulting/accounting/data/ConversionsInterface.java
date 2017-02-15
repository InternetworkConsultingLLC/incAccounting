/*
 * incGenerator - Generated Object Model
 * Copyright (C) 2016 Internetwork Consulting LLC
 */
package net.internetworkconsulting.accounting.data;

import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;

public interface ConversionsInterface {
	
	boolean setGuid(java.lang.String value) throws Exception;
	java.lang.String getGuid();
	
	boolean setLeftUnitMeasuresGuid(java.lang.String value) throws Exception;
	java.lang.String getLeftUnitMeasuresGuid();
	
	boolean setLeftQuantity(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getLeftQuantity();
	
	boolean setRightUnitMeasuresGuid(java.lang.String value) throws Exception;
	java.lang.String getRightUnitMeasuresGuid();
	
	boolean setRightQuantity(java.math.BigDecimal value) throws Exception;
	java.math.BigDecimal getRightQuantity();
	
	
	
	<T extends UnitMeasuresRow> T loadLeftUnitMeasure(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
	<T extends UnitMeasuresRow> T loadRightUnitMeasure(AdapterInterface adapter, Class biz, boolean force) throws Exception;
	
}
