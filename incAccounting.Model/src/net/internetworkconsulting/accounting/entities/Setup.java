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

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Group;
import net.internetworkconsulting.bootstrap.entities.Permission;
import net.internetworkconsulting.bootstrap.entities.Report;
import net.internetworkconsulting.bootstrap.entities.ReportBlock;
import net.internetworkconsulting.bootstrap.entities.ReportFilter;
import net.internetworkconsulting.bootstrap.entities.Securable;
import net.internetworkconsulting.bootstrap.entities.Setting;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Setup extends net.internetworkconsulting.bootstrap.entities.Setup {
	public void createDatabase() throws Exception {
		super.createDatabase();

		AdapterInterface adapter = connect();

		List<String> lstSql = new LinkedList<String>();

		String[] arrSql = User.readJarFile(Setup.class, "Database.20151111.sql").split("\\;");
		for (String sql : arrSql)
			lstSql.add(sql);

		arrSql = User.readJarFile(Setup.class, "Database.20160204.sql").split("\\;");
		for (String sql : arrSql)
			lstSql.add(sql);

		for (String sql : lstSql) {
			if (sql.trim().length() > 0) {
				Statement stmt = new Statement(sql);
				stmt.setCommand(stmt.getCommand().replace("%DATABASE%", getDatabase()));
				try { adapter.execute(stmt, false); } 
				catch (Exception ex) {
					throw new Exception(ex.getMessage() + "\n\n" + stmt.generate(null, false));
				}
			}
		}

		createAcountTypes(adapter);
		createSettings(adapter);
		createTransactions(adapter);
		createReport(adapter);
		createSecurables(adapter);	
		createUs(adapter);
	}
	private void createAcountTypes(AdapterInterface adapter) throws Exception {
		AccountType accttype = new AccountType();
		accttype.setGuid(AccountType.ASSETS_TYPE_GUID);
		accttype.setGetsClosed(false);
		accttype.setName("Assets");
		adapter.save(AccountType.TABLE_NAME, accttype);
		
		accttype = new AccountType();
		accttype.setGuid(AccountType.LIABILTIES_TYPE_GUID);
		accttype.setGetsClosed(false);
		accttype.setName("Liability");
		adapter.save(AccountType.TABLE_NAME, accttype);

		accttype = new AccountType();
		accttype.setGuid(AccountType.RETAINED_TYPE_GUID);
		accttype.setGetsClosed(false);
		accttype.setName("Retained Earnings");
		adapter.save(AccountType.TABLE_NAME, accttype);

		accttype = new AccountType();
		accttype.setGuid(AccountType.PURPETUAL_EQUITY_TYPE_GUID);
		accttype.setGetsClosed(false);
		accttype.setName("Equity Stays Open");
		adapter.save(AccountType.TABLE_NAME, accttype);

		accttype = new AccountType();
		accttype.setGuid(AccountType.EQUITY_TYPE_GUID);
		accttype.setGetsClosed(true);
		accttype.setName("Equity Gets Closed");
		adapter.save(AccountType.TABLE_NAME, accttype);

		accttype = new AccountType();
		accttype.setGuid(AccountType.REVENUE_TYPE_GUID);
		accttype.setGetsClosed(true);
		accttype.setName("Revenue");
		adapter.save(AccountType.TABLE_NAME, accttype);

		accttype = new AccountType();
		accttype.setGuid(AccountType.EXPENSES_TYPE_GUID);
		accttype.setGetsClosed(true);
		accttype.setName("Expenses");
		adapter.save(AccountType.TABLE_NAME, accttype);
	}
	private void createSettings(AdapterInterface adapter) throws Exception {
		Setting setting = new Setting();
		setting.initialize();
		setting.setKey(Account.SETTING_SEPERATOR_ENABLED);
		setting.setValue("T");
		setting.setType(Setting.TYPE_BOOLEAN);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_POST_ON_SAVE);
		setting.setValue("T");
		setting.setType(Setting.TYPE_BOOLEAN);
		adapter.save(Setting.TABLE_NAME, setting);
		
//		setting = new Setting();
//		setting.initialize();
//		setting.setKey(Item.SETTING_DESCRIPTION_ROWS);
//		setting.setValue("5");
//		setting.setType(Setting.TYPE_NUMBER);
//		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(TransactionLine.SETTING_DEBIT_DECIMALS);
		setting.setValue("2");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Account.SETTING_SEPERATOR_SYMBOL);
		setting.setValue("-");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		//
		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_PURCHASE_QUOTE_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_PURCHASE_ORDER_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);
		
		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_PURCHASE_INVOICE_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_PURCHASE_CREDIT_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);		
		//
		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_SALES_QUOTE_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_SALES_ORDER_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);
		
		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_SALES_INVOICE_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_LAST_SALES_CREDIT_NUMBER);
		setting.setValue("1000");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);		
		//

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_TERMS);
		setting.setValue("COD");
		setting.setType(Setting.TYPE_STRING);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_DUE_DAYS);
		setting.setValue("0");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);

//		setting = new Setting();
//		setting.initialize();
//		setting.setKey(DocumentLine.SETTING_TAXABLE);
//		setting.setValue("T");
//		setting.setType(Setting.TYPE_BOOLEAN);
//		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_MONEY_DECIMALS);
		setting.setValue("2");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_RATE_DECIMALS);
		setting.setValue("4");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);
		
		setting = new Setting();
		setting.initialize();
		setting.setKey(Document.SETTING_QUANITY_DECIMALS);
		setting.setValue("2");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);

		setting = new Setting();
		setting.initialize();
		setting.setKey(Conversion.SETTING_QUANITY_DECIMALS);
		setting.setValue("8");
		setting.setType(Setting.TYPE_NUMBER);
		adapter.save(Setting.TABLE_NAME, setting);
	}
	private void createTransactions(AdapterInterface adapter) throws Exception {
		TransactionType trantype = new TransactionType();
		PaymentType paytype;
		PaymentTypesDocumentType ptdt;
		Securable sec = null;
		DocumentType doctype = null;
		
		// transaction type - base transaction / journal entry
		trantype.setGuid(TransactionType.TRANSACTION_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Transaction");
		trantype.setListUrl("/incAccounting?App=TransactionList");
		trantype.setEditUrl("/incAccounting?App=TransactionEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);

		// transaction type - payroll check
		trantype.setGuid(PayrollCheck.TRANSACTION_TYPE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Payroll Check");
		trantype.setListUrl("/incAccounting?App=PayrollCheckList");
		trantype.setEditUrl("/incAccounting?App=PayrollCheckEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		// sales quotes
		sec = new Securable();
		sec.setGuid(DocumentType.SALES_QUOTE_GUID);
		sec.setDisplayName("Document - Sales Quote");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.SALES_QUOTE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Sales Quote");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.SALES_QUOTE_GUID);
		doctype.setIsSalesRelated(true);
		doctype.setIsPosting(false);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);
		
		// sales orders
		sec = new Securable();
		sec.setGuid(DocumentType.SALES_ORDER_GUID);
		sec.setDisplayName("Document - Sales Order");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.SALES_ORDER_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Sales Order");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.SALES_ORDER_GUID);
		doctype.setIsSalesRelated(true);
		doctype.setIsPosting(false);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// sales invoices
		sec = new Securable();
		sec.setGuid(DocumentType.SALES_INVOICE_GUID);
		sec.setDisplayName("Document - Sales Invoice");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.SALES_INVOICE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Sales Invoice");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.SALES_INVOICE_GUID);
		doctype.setIsSalesRelated(true);
		doctype.setIsPosting(true);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// sales credits
		sec = new Securable();
		sec.setGuid(DocumentType.SALES_CREDIT_GUID);
		sec.setDisplayName("Document - Sales Credit");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.SALES_CREDIT_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Sales Credit");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.SALES_CREDIT_GUID);
		doctype.setIsSalesRelated(true);
		doctype.setIsPosting(true);
		doctype.setIsCreditMemo(true);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// purchase quotes
		sec = new Securable();
		sec.setGuid(DocumentType.PURCHASE_QUOTE_GUID);
		sec.setDisplayName("Document - Purchase Quote");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.PURCHASE_QUOTE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Purchase Quote");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.PURCHASE_QUOTE_GUID);
		doctype.setIsSalesRelated(false);
		doctype.setIsPosting(false);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);
		
		// purchase orders
		sec = new Securable();
		sec.setGuid(DocumentType.PURCHASE_ORDER_GUID);
		sec.setDisplayName("Document - Purchase Order");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.PURCHASE_ORDER_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Purchase Order");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.PURCHASE_ORDER_GUID);
		doctype.setIsSalesRelated(false);
		doctype.setIsPosting(false);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// purchase invoices
		sec = new Securable();
		sec.setGuid(DocumentType.PURCHASE_INVOICE_GUID);
		sec.setDisplayName("Document - Purchase Invoice");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.PURCHASE_INVOICE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Purchase Invoice");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.PURCHASE_INVOICE_GUID);
		doctype.setIsSalesRelated(false);
		doctype.setIsPosting(true);
		doctype.setIsCreditMemo(false);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// purchase credits
		sec = new Securable();
		sec.setGuid(DocumentType.PURCHASE_CREDIT_GUID);
		sec.setDisplayName("Document - Purchase Credit");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(DocumentType.PURCHASE_CREDIT_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Purchase Credit");
		trantype.setListUrl("/incAccounting?App=DocumentList");
		trantype.setEditUrl("/incAccounting?App=DocumentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		doctype = new DocumentType();
		doctype.setGuid(DocumentType.PURCHASE_CREDIT_GUID);
		doctype.setIsSalesRelated(false);
		doctype.setIsPosting(true);
		doctype.setIsCreditMemo(true);
		adapter.save(DocumentType.TABLE_NAME, doctype);

		// sales payments
		sec = new Securable();
		sec.setGuid(PaymentType.SALES_PAYMENT_GUID);
		sec.setDisplayName("Payment - Sales");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(PaymentType.SALES_PAYMENT_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Sales Payment");
		trantype.setListUrl("/incAccounting?App=PaymentList");
		trantype.setEditUrl("/incAccounting?App=PaymentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
	
		paytype = new PaymentType();
		paytype.setGuid(PaymentType.SALES_PAYMENT_GUID);
		paytype.setIsSalesRelated(true);
		adapter.save(PaymentType.TABLE_NAME, paytype);
		
		ptdt = new PaymentTypesDocumentType();
		ptdt.setPaymentTypesGuid(PaymentType.SALES_PAYMENT_GUID);
		ptdt.setDocumentTypesGuid(DocumentType.SALES_INVOICE_GUID);
		adapter.save(PaymentTypesDocumentType.TABLE_NAME, ptdt);

		ptdt = new PaymentTypesDocumentType();
		ptdt.setPaymentTypesGuid(PaymentType.SALES_PAYMENT_GUID);
		ptdt.setDocumentTypesGuid(DocumentType.SALES_CREDIT_GUID);
		adapter.save(PaymentTypesDocumentType.TABLE_NAME, ptdt);

		// purchase payments
		sec = new Securable();
		sec.setGuid(PaymentType.PURCHASE_PAYMENT_GUID);
		sec.setDisplayName("Payment - Purchase");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(PaymentType.PURCHASE_PAYMENT_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Purchase Payment");
		trantype.setListUrl("/incAccounting?App=PaymentList");
		trantype.setEditUrl("/incAccounting?App=PaymentEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
		paytype = new PaymentType();
		paytype.setGuid(PaymentType.PURCHASE_PAYMENT_GUID);
		paytype.setIsSalesRelated(false);
		adapter.save(PaymentType.TABLE_NAME, paytype);
		
		ptdt = new PaymentTypesDocumentType();
		ptdt.setPaymentTypesGuid(PaymentType.PURCHASE_PAYMENT_GUID);
		ptdt.setDocumentTypesGuid(DocumentType.PURCHASE_INVOICE_GUID);
		adapter.save(PaymentTypesDocumentType.TABLE_NAME, ptdt);

		ptdt = new PaymentTypesDocumentType();
		ptdt.setPaymentTypesGuid(PaymentType.PURCHASE_PAYMENT_GUID);
		ptdt.setDocumentTypesGuid(DocumentType.PURCHASE_CREDIT_GUID);
		adapter.save(PaymentTypesDocumentType.TABLE_NAME, ptdt);
		
		// deposit
		sec = new Securable();
		sec.setGuid(Deposit.TRANSACTION_TYPE_GUID);
		sec.setDisplayName("Bank Deposit");
		adapter.save(Securable.TABLE_NAME, sec);
		
		trantype = new TransactionType();
		trantype.setGuid(Deposit.TRANSACTION_TYPE_GUID);
		trantype.setIsAllowed(true);
		trantype.setName("Bank Deposit");
		trantype.setListUrl("/incAccounting?App=DepositList");
		trantype.setEditUrl("/incAccounting?App=DepositEdit");
		adapter.save(TransactionType.TABLE_NAME, trantype);
		
	}
	private void createReport(AdapterInterface adapter) throws Exception {
		Report report = new Report();
		report.initialize();
		report.setGuid("6d4ea7d4624e48509e915e019ca1f7a9");
		report.setDisplayName("Chart of Accounts with Balances");
		report.setHtmlTemplate(
			"<!-- STOCK REPORT -->" +
			"<table>\n" +
			"	<tr>\n" +
			"		<td>Number</td>\n" +
			"        <td>Name</td>\n" +
			"        <td>Type</td>\n" +
			"        <td>Balance</td>\n" +
			"        <td>Actions</td>\n" +
			"	</tr>\n" +
			"    <!-- BEGIN Account -->\n" +
			"	<tr>\n" +
			"		<td>{Number}</td>\n" +
			"        <td>{Name}</td>\n" +
			"        <td>{Account Types Name}</td>\n" +
			"        <td>{Balance}</td>\n" +
			"        <td>\n" +
			"			<a href=\"http://localhost:8080/incAccounting/AccountEdit?GUID={GUID}\">Edit</a>\n" +
			"		</td>\n" +
			"	</tr>\n" +
			"    <!-- END Account -->\n" +
			"</table>"
		);
		adapter.save(Report.TABLE_NAME, report);
		
		ReportFilter filter = new ReportFilter();
		filter.initialize();
		filter.setPrompt("As Of Date");
		filter.setDataType(ReportFilter.DT_DATE);
		filter.setQuery("SELECT NOW()");
		filter.setReportsGuid(report.getGuid());
		adapter.save(ReportFilter.TABLE_NAME, filter);
		
		ReportBlock block = new ReportBlock();
		block.initialize();
		block.setReportsGuid(report.getGuid());
		block.setName("Account");
		block.setPriority(0);
		block.setSqlQuery(
			"SELECT\n" +
			"	\"Accounts\".\"GUID\",\n" +
			"	\"Accounts\".\"Number\",\n" +
			"	\"Accounts\".\"Name\",\n" +
			"	\"Account Types\".\"Name\" AS \"Account Types Name\",\n" +
			"	CAST((\n" +
			"		SELECT SUM(\"Transaction Lines\".\"Debit\") FROM \"Transaction Lines\"\n" +
			"	) AS DECIMAL(64,2)) AS \"Balance\"\n" +
			"FROM \n" +
			"	\"Accounts\"\n" +
			"	JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\n" +
			"ORDER BY \"Number\""
		);
		adapter.save(ReportBlock.TABLE_NAME, block);
	}
	private void createSecurables(AdapterInterface adapter) throws Exception {
		Securable sec;
		Permission perm;
		
		sec = new Securable();
		sec.setDisplayName("Table - " + AccountType.TABLE_NAME);
		sec.setGuid((new AccountType()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Account.TABLE_NAME);
		sec.setGuid((new Account()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + ContactNote.TABLE_NAME);
		sec.setGuid((new ContactNote()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + ContactType.TABLE_NAME);
		sec.setGuid((new ContactType()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Contact.TABLE_NAME);
		sec.setGuid((new Contact()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Conversion.TABLE_NAME);
		sec.setGuid((new Conversion()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Department.TABLE_NAME);
		sec.setGuid((new Department()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + DocumentLine.TABLE_NAME);
		sec.setGuid((new DocumentLine()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + DocumentType.TABLE_NAME);
		sec.setGuid((new DocumentType()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Document.TABLE_NAME);
		sec.setGuid((new Document()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Item.TABLE_NAME);
		sec.setGuid((new Item()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Job.TABLE_NAME);
		sec.setGuid((new Job()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Reconciliation.TABLE_NAME);
		sec.setGuid((new Reconciliation()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + SalesTaxMembership.TABLE_NAME);
		sec.setGuid((new SalesTaxMembership()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + TransactionLine.TABLE_NAME);
		sec.setGuid((new TransactionLine()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + TransactionType.TABLE_NAME);
		sec.setGuid((new TransactionType()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + Transaction.TABLE_NAME);
		sec.setGuid((new Transaction()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);

		sec = new Securable();
		sec.setDisplayName("Table - " + UnitMeasure.TABLE_NAME);
		sec.setGuid((new UnitMeasure()).getSqlSecurableGuid());
		adapter.save(Securable.TABLE_NAME, sec);
		
		perm = new Permission();
		perm.setGroupsGuid(Group.EVERYONE_GUID);
		perm.setSecurablesGuid(sec.getGuid());
		perm.setCanCreate(false);
		perm.setCanRead(true);
		perm.setCanUpdate(false);
		perm.setCanDelete(false);
		adapter.save(Permission.TABLE_NAME, perm);
	}
	
	private void createUs(AdapterInterface adapter) throws Exception {
		SalesTax stax = new SalesTax();
		stax.initialize();
		stax.setDisplayName("Exempt");
		stax.setIsGroup(true);
		stax.setGuid(SalesTax.GROUP_EXEMPT_GUID);
		stax.setTaxRate(BigDecimal.valueOf(0));
		adapter.save(SalesTax.TABLE_NAME, stax);
		
		ContactType ct = new ContactType();
		ct.setGuid(ContactType.TYPE_BUSINESS_GUID);
		ct.setDisplayName("Business");
		ct.setIsAllowed(true);
		adapter.save(ContactType.TABLE_NAME, ct);
		
		ct = new ContactType();
		ct.setGuid(ContactType.TYPE_EMPLOYEE_GUID);
		ct.setDisplayName("Employee");
		ct.setIsAllowed(true);
		adapter.save(ContactType.TABLE_NAME, ct);
		
		Contact contact = new Contact();
		contact.initialize();
		contact.setGuid(Contact.OUR_COMPANY_GUID);
		contact.setDisplayName("YOUR COMPANY - RENAME ME");
		contact.setIsAllowed(true);
		contact.setContactTypesGuid(ContactType.TYPE_BUSINESS_GUID);
		contact.setTaxGroupGuid(SalesTax.GROUP_EXEMPT_GUID);
		adapter.save(Contact.TABLE_NAME, contact);
	}
}
