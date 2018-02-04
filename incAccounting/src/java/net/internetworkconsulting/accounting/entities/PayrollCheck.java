package net.internetworkconsulting.accounting.entities;

import com.ibm.icu.text.RuleBasedNumberFormat;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import net.internetworkconsulting.accounting.data.PayrollChecksRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class PayrollCheck extends PayrollChecksRow {
	public static int DURATION_WEEKLY = 1;
	public static int DURATION_BIWEEKLY = 2;
	public static int DURATION_SEMIMONTHLY = 3;
	public static int DURATION_MONTHLY = 4;
	public static int DURATION_QUATERLY = 5;
	public static int DURATION_SEMIANNUALLY = 6;
	public static int DURATION_ANNUALLY = 7;
	public static int DURATION_DIALY = 8;
	
	public static String TRANSACTION_TYPE_GUID = "14a778aeb6e04b7fa1dd31e4445e9b18";
	private boolean bSkipPostedCheck = false;

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setDate(new Date(java.time.Instant.now().toEpochMilli()));
		this.setEnding(new Date(java.time.Instant.now().toEpochMilli()));
	}
	
	public static List<Option> getDurationOptions() throws Exception {
		LinkedList<Option> lstOptions = new LinkedList<>();
		lstOptions.add(new Option("", ""));
		lstOptions.add(new Option("Weekly", "1"));
		lstOptions.add(new Option("Biweekly", "2"));
		lstOptions.add(new Option("Semimonthly", "3"));
		lstOptions.add(new Option("Monthly", "4"));
		lstOptions.add(new Option("Quaterly", "5"));
		lstOptions.add(new Option("Semianually", "6"));
		lstOptions.add(new Option("Annually", "7"));
		lstOptions.add(new Option("Daily", "8"));
		return lstOptions;
	}
	
	public String getPaycheckAmountAsText() {
		return net.internetworkconsulting.data.Helper.numberToText("EN", "US", "dollars", getPaycheckAmount());
	}

	public static PayrollCheck loadTemplate(AdapterInterface adapter, String employees_guid) throws Exception {
		Statement stmt = new Statement(User.readJarFile(PayrollCheck.class, "PayrollCheck.loadTemplate.sql"));
		stmt.getParameters().put("{Employees GUID}", employees_guid);
		List<PayrollCheck> lstChecks = adapter.load(PayrollCheck.class, stmt, true);
		
		if(lstChecks.isEmpty())
			throw new Exception("Could not locate a template for employee GUID: " + employees_guid);
		
		return lstChecks.get(0);
	}
	public static List<PayrollCheck> loadByPosted(AdapterInterface adapter, boolean is_posted) throws Exception {
		String sql = User.readJarFile(PayrollCheck.class, "PayrollCheck.loadByPosted.sql");
		if(is_posted)
			sql = sql.replace("{Nullable}", "NOT NULL");
		else
			sql = sql.replace("{Nullable}", "NULL");

		Statement stmt = new Statement(sql);
		List<PayrollCheck> lstValues = adapter.load(PayrollCheck.class, stmt, true);
		
		return lstValues;
	}	
	
	public List<PayrollFieldValue> loadValues(AdapterInterface adapter, String type_guid) throws Exception {
		Statement stmt = new Statement(User.readJarFile(PayrollCheck.class, "PayrollCheck.loadValues.sql"));
		stmt.getParameters().put("{Payroll Checks GUID}", this.getGuid());
		stmt.getParameters().put("{Payroll Field Types GUID}", type_guid);
		List<PayrollFieldValue> lstValues = adapter.load(PayrollFieldValue.class, stmt, true);
		for(PayrollFieldValue pfv: lstValues)
			pfv.setPayrollCheck(this);
		
		return lstValues;
	}
	private List<PayrollFieldValue> lstGrossExpenseValues = null;
	public List<PayrollFieldValue> loadGrossExepnseValues(AdapterInterface adapter, boolean force) throws Exception {
		if(lstGrossExpenseValues == null || force)
			lstGrossExpenseValues = loadValues(adapter, PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);
		return lstGrossExpenseValues;
	}
	private List<PayrollFieldValue> lstEmployeePaidValues = null;
	public List<PayrollFieldValue> loadEmployeePaidValues(AdapterInterface adapter, boolean force) throws Exception {
		if(lstEmployeePaidValues == null || force)
			lstEmployeePaidValues = loadValues(adapter, PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID);
		return lstEmployeePaidValues;
	}
	private List<PayrollFieldValue> lstCompanyPaidValues = null;
	public List<PayrollFieldValue> loadCompanyPaidValues(AdapterInterface adapter, boolean force) throws Exception {
		if(lstCompanyPaidValues == null || force)
			lstCompanyPaidValues = loadValues(adapter, PayrollFieldType.TYPE_COMPANY_PAID_GUID);
		return lstCompanyPaidValues;
	}
	
	private Employee objEmployee = null;
	public void handleEmployeeGuid(AdapterInterface adapter) throws Exception {
		if(objEmployee != null && objEmployee.getGuid().compareTo(this.getEmployeesGuid()) == 0)
			return;
		
		objEmployee = Employee.loadByGuid(adapter, Employee.class, getEmployeesGuid());
		Contact objContact = objEmployee.loadContact(adapter, Contact.class, false);
		
		this.setEmployeesGuid(objEmployee.getGuid());
		this.setPayToTheOrderOf(objEmployee.getFirstName() + " " + objEmployee.getLastName());
		this.setLine1(objContact.getLine1());
		this.setLine2(objContact.getLine2());
		this.setCity(objContact.getCity());
		this.setState(objContact.getState());
		this.setPostalCode(objContact.getPostalCode());
		this.setCountry(objContact.getCountry());
	}
	
	public void calculate(AdapterInterface adapter) throws Exception {
		BigDecimal dCompensation = BigDecimal.ZERO;
		BigDecimal dAdjustedGross = BigDecimal.ZERO;
		BigDecimal dCompanyPaid = BigDecimal.ZERO;
		BigDecimal dEmployeePaid = BigDecimal.ZERO;
		
		// gross compensation
		for(PayrollFieldValue pfv: loadGrossExepnseValues(adapter, false)) {
			pfv.calculate(adapter);
			if(pfv.getTotal() != null) {
				if(pfv.getTotal().compareTo(BigDecimal.ZERO) > 0)
					dCompensation = dCompensation.add(pfv.getTotal());
				dAdjustedGross = dAdjustedGross.add(pfv.getTotal());
			}
		}
		
		// company paid
		for(PayrollFieldValue pfv: loadCompanyPaidValues(adapter, false)) {
			pfv.calculate(adapter);
			if(pfv.getTotal() != null)
				dCompanyPaid = dCompanyPaid.add(pfv.getTotal());
		}
		
		// employee paid
		for(PayrollFieldValue pfv: loadEmployeePaidValues(adapter, false)) {
			pfv.calculate(adapter);
			if(pfv.getTotal() != null)
				dEmployeePaid = dEmployeePaid.add(pfv.getTotal());	
		}
		
		this.setAdjustedGross(dAdjustedGross);
		this.setCompensation(dCompensation);
		this.setCompanyPaid(dCompanyPaid);
		this.setEmployeePaid(dEmployeePaid);
		
		this.setPaycheckAmount(dAdjustedGross.subtract(dEmployeePaid));
		this.setTotalCosts(dCompensation.add(dCompanyPaid));
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getPostedTransactionsGuid() != null && !bSkipPostedCheck)
			throw new Exception("You cannot change a payroll check that has been posted!");
	}
	
	public void post(AdapterInterface adapter) throws Exception {
		// Payroll Check Guid -> Transaction Guid
		// Payroll Check Line Guid -> Transaction Line Guid
		
		if(getAccountsGuid() == null)
			throw new Exception("The payroll check does not have an account to post to!");
		
		Transaction objTran = new Transaction();
		objTran.initialize();
		objTran.setDate(getDate());
		objTran.setTransactionTypesGuid(PayrollCheck.TRANSACTION_TYPE_GUID);
		objTran.setGuid(this.getGuid());
		objTran.setReferenceNumber(getNumber());
	
		List<TransactionLine> lstTranLines = createPostLines(adapter, objTran);
		
		this.setPostedTransactionsGuid(objTran.getGuid());
		
		try {
			adapter.begin(false);
			
			objTran.setSkipDocumentCheck(true);
			adapter.save(Transaction.TABLE_NAME, objTran);
			adapter.save(TransactionLine.TABLE_NAME, lstTranLines);
			objTran.setSkipDocumentCheck(false);

			this.bSkipPostedCheck = true;
			adapter.save(PayrollCheck.TABLE_NAME, this, false);
			this.bSkipPostedCheck = false;
			
			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	public void unpost(AdapterInterface adapter) throws Exception {
		Transaction objTran = loadTransaction(adapter, Transaction.class, false);
		List<TransactionLine> objLines = objTran.loadTransactionLines(adapter, TransactionLine.class, false);
		
		objTran.setIsDeleted(true);
		for(TransactionLine line: objLines)
			line.setIsDeleted(true);
		
		this.setPostedTransactionsGuid(null);

		try {
			adapter.begin(false);

			this.bSkipPostedCheck = true;
			adapter.save(PayrollCheck.TABLE_NAME, this, false);
			this.bSkipPostedCheck = false;

			objTran.setSkipDocumentCheck(true);
			adapter.save(TransactionLine.TABLE_NAME, objLines);
			adapter.save(Transaction.TABLE_NAME, objTran);
			objTran.setSkipDocumentCheck(false);

			adapter.commit(false);
		}
		catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	
	private List<TransactionLine> createPostLines(AdapterInterface adapter, Transaction objTran) throws Exception {
		List<PayrollFieldValue> lstValues = new LinkedList<>();
		lstValues.addAll(this.loadCompanyPaidValues(adapter, false));
		lstValues.addAll(this.loadEmployeePaidValues(adapter, false));
		lstValues.addAll(this.loadGrossExepnseValues(adapter, false));
		
		List<TransactionLine> lstLines = objTran.loadTransactionLines(adapter, TransactionLine.class, false);
		for(PayrollFieldValue pfv: lstValues) {
			PayrollField pf = pfv.loadPayrollField(adapter, PayrollField.class, false);
			PayrollFieldType pft = pf.loadPayrollFieldType(adapter, PayrollFieldType.class, false);
			
			if(pft.getCreditRequired())
				lstLines.add(createLine(adapter, objTran, User.newGuid(), pf.getName(), pfv.getTotal().multiply(BigDecimal.valueOf(-1)), pf.getCreditAccountsGuid(), lstLines.size()));
			if(pft.getDebitRequired())
				lstLines.add(createLine(adapter, objTran, User.newGuid(), pf.getName(), pfv.getTotal().multiply(BigDecimal.valueOf(1)), pf.getDebitAccountsGuid(), lstLines.size()));
		}
		
		lstLines.add(createLine(adapter, objTran, User.newGuid(), "Payroll Check " + this.getNumber(), this.getPaycheckAmount().multiply(BigDecimal.valueOf(-1)), this.getAccountsGuid(), lstLines.size()));

		return lstLines;
	}
	private TransactionLine createLine(AdapterInterface adapter, Transaction objTran, String guid, String description, BigDecimal debit_amount, String account_guid, int order) throws Exception {
		TransactionLine line = new TransactionLine();
		line.initialize(objTran, adapter);
		line.setAccountsGuid(account_guid);
		line.setDebit(debit_amount);
		line.setDescription(description);
		line.setGuid(guid);
		line.setSortOrder(order);
		return line;
	}
	public void handleAutoNumber(AdapterInterface adapter) throws Exception {
		adapter.begin(false);
		try {
			Account bizAccount = this.loadAccountsGuid(adapter, Account.class, false);
			String sMyNumber = bizAccount.getLastNumber();
			boolean isAvailable = false;
			do {
				sMyNumber = net.internetworkconsulting.data.Helper.Increment(sMyNumber);
				isAvailable = Payment.isNumberAvaiable(adapter, PaymentType.PURCHASE_PAYMENT_GUID, sMyNumber, getAccountsGuid());
				isAvailable = isAvailable &&  PayrollCheck.isNumberAvailable(adapter, sMyNumber, getAccountsGuid());
				isAvailable = isAvailable && Transaction.isNumberAvailable(adapter, sMyNumber, getAccountsGuid());
			} while(!isAvailable);
			
			this.setNumber(sMyNumber);
			bizAccount.setLastNumber(sMyNumber);
			
			adapter.save(Account.TABLE_NAME, bizAccount);
			adapter.save(PayrollCheck.TABLE_NAME, this);			
			adapter.commit(false);
		} catch(Exception ex) {
			adapter.rollback(false);
			throw ex;
		}
	}
	
	
	public static boolean isNumberAvailable(AdapterInterface adapter, String number, String accounts_guid) throws Exception {
		String sql = "SELECT * FROM  \"%s\" WHERE \"%s\"={AccountsGuid} AND \"%s\"={Number}";
		sql = String.format(sql, PayrollCheck.TABLE_NAME, PayrollCheck.ACCOUNTS_GUID, PayrollCheck.NUMBER);
		
		Statement stmt = new Statement(sql);
		stmt.getParameters().put("{AccountsGuid}", accounts_guid);
		stmt.getParameters().put("{Number}", number);
		
		List<PayrollCheck> lst = adapter.load(PayrollCheck.class, stmt, true);
		return lst.isEmpty();
	}
}
