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
import net.internetworkconsulting.accounting.data.ConversionsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Conversion extends ConversionsRow {
	public static Conversion loadByLeftAndRight(AdapterInterface adapter, String left_guid, String right_guid) throws Exception {
		String sql = "SELECT * FROM \"Conversions\" WHERE \"" + Conversion.LEFT_UNIT_MEASURES_GUID + "\"={LEFT} AND \"" +  Conversion.RIGHT_UNIT_MEASURES_GUID + "\"={RIGHT} ";
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{LEFT}", left_guid);
		stmt.getParameters().put("{RIGHT}", right_guid);

		List<Conversion> lst = adapter.load(Conversion.class, stmt, true);
		if(lst.size() != 1)
			throw new Exception("Could not locate unique Conversion by left and right unit measures GUID!");

		return lst.get(0);		
	}
	
	public static String SETTING_QUANITY_DECIMALS = "Conversions - Decimals";

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {		
		Conversion convReversed;
		try {
			convReversed = Conversion.loadByLeftAndRight(adapter, getRightUnitMeasuresGuid(), getLeftUnitMeasuresGuid());
		} catch(Exception ex) {
			convReversed = new Conversion();
			convReversed.initialize();
		}
		convReversed.setLeftQuantity(getRightQuantity());
		convReversed.setLeftUnitMeasuresGuid(getRightUnitMeasuresGuid());
		convReversed.setRightQuantity(getLeftQuantity());
		convReversed.setRightUnitMeasuresGuid(getLeftUnitMeasuresGuid());
		
		adapter.save(Conversion.TABLE_NAME, convReversed, false);
	}
}
