package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.PaymentTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PaymentType extends PaymentTypesRow { 
	public static String SALES_PAYMENT_GUID = "2c12d6167d654604be3f533c38f1ad1e";
	public static String PURCHASE_PAYMENT_GUID = "a714a873202f4f12bb29a42ed8ed9b5c";
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(PaymentType.class, "PaymentType.loadOptions.sql"));
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public void setTransactionType(TransactionType value) {
		this.rTransactionTypeParent = value;
	}
	@Override
	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getRowState() == RowState.Delete)
			throw new Exception("You may not delete payment types!");
	}
	
	
}
