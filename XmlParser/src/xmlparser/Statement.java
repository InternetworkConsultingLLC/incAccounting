package xmlparser;

import java.math.BigDecimal;
import java.text.Exception;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Node;

public class Statement {
	Statement(Node statement) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

		AvailableBalance = new BigDecimal(Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:AVAILBAL:BALAMT"));
		
		String sDate = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:AVAILBAL:DTASOF").substring(0, 14);
		AvailableDate = sdf.parse(sDate);
		
		BankAcountIdentifier = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:ACCTID");
		BankAccountType = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:ACCTTYPE");
		BankIdentifier = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:BANKID");

		sDate = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:DTEND").substring(0, 14);
		End = sdf.parse(sDate);

		sDate = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:DTSTART").substring(0, 14);
		Start = sdf.parse(sDate);

		Currency = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:CURDEF");

		LedgerBalance = new BigDecimal(Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:LEDGERBAL:BALAMT"));

		sDate = Helper.getXmlValue(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:LEDGERBAL:DTASOF").substring(0, 14);
		LedgerDate = sdf.parse(sDate);
		
		//Transactions;
		List<Node> lstNodes = Helper.getXmlNodes(statement, "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN");
		setTransactions(new LinkedList<>());
		for(Node curr : lstNodes) {
			Transaction stmt = new Transaction(curr);
			Transactions.add(stmt);
		}		
	}


	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:AVAILBAL:BALAMT
	private BigDecimal AvailableBalance;
	public BigDecimal getAvailableBalance() { return AvailableBalance; }
	public void setAvailableBalance(BigDecimal value) { AvailableBalance = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:AVAILBAL:DTASOF
	private Date AvailableDate;
	public Date getAvailableDate() { return AvailableDate; }
	public void setAvailableDate(Date value) { AvailableDate = value; }
	
	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:ACCTID
	private String BankAcountIdentifier;
	public String getBankAcountIdentifier() { return BankAcountIdentifier; }
	public void setBankAcountIdentifier(String value) { BankAcountIdentifier = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:ACCTTYPE
	private String BankAccountType;
	public String getBankAccountType() { return BankAccountType; }
	public void setBankAccountType(String value) { BankAccountType = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKACCTFROM:BANKID
	private String  BankIdentifier;
	public String getBankIdentifier() { return BankIdentifier; }
	public void setBankIdentifier(String value) { BankIdentifier = value; }
	
	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:DTEND
	private Date End;
	public Date getEnd() { return End; }
	public void setEnd(Date value) { End = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:DTSTART
	private Date Start;
	public Date getStart() { return Start; }
	public void setStart(Date value) { Start = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:CURDEF
	private String Currency;
	public String getCurrency() { return Currency; }
	public void setCurrency(String value) { Currency = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:LEDGERBAL:BALAMT
	private BigDecimal LedgerBalance;
	public BigDecimal getLedgerBalance() { return LedgerBalance; }
	public void setLedgerBalance(BigDecimal value) { LedgerBalance = value; }

	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:LEDGERBAL:DTASOF
	private Date LedgerDate;
	public Date getLedgerDate() { return LedgerDate; }
	public void setLedgerDate(Date value) { LedgerDate = value; }
	
	// OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN
	private List<Transaction> Transactions;
	public List<Transaction> getTransactions() { return Transactions; }
	public void setTransactions(List<Transaction> value) { Transactions = value; }
}
