package net.internetworkconsulting.data.ofx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.internetworkconsulting.data.Helper;
import org.w3c.dom.Node;

public class Transaction implements Serializable {
	Transaction(Node curr) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

		CheckNumber = Helper.getXmlValue(curr, CHECKNUMBER);

		String sDate = Helper.getXmlValue(curr, POSTED);
		Posted = sdf.parse(sDate);
		
		Identifier = Helper.getXmlValue(curr, IDENTIFIER);
		Memo = Helper.getXmlValue(curr, MEMO);
		Name = Helper.getXmlValue(curr, NAME);
		TransactionType = Helper.getXmlValue(curr, TRANSACTIONTYPE);
		
		Amount = new BigDecimal(Helper.getXmlValue(curr, AMOUNT));
	}

	public static String CHECKNUMBER = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:CHECKNUM";
	public String CheckNumber;
	public String getCheckNumber() { return CheckNumber; }
	public void setCheckNumber(String value) { CheckNumber = value; }
	
	public static String POSTED = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:DTPOSTED";
	public Date Posted;
	public Date getPosted() { return Posted; }
	public void setPosted(Date value) { Posted = value; }
	
	public static String IDENTIFIER = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:FITID";
	public String Identifier;
	public String getIdentifier() { return Identifier; }
	public void setIdentifier(String value) { Identifier = value; }
	
	public static String MEMO = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:MEMO";
	public String Memo;
	public String getMemo() { return Memo; }
	public void setMemo(String value) { Memo = value; }
	
	public static String NAME = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:NAME";
	public String Name;
	public String getName() { return Name; }
	public void setName(String value) { Name = value; }

	public static String AMOUNT = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:TRNAMT";
	public BigDecimal Amount;
	public BigDecimal getAmount() { return Amount; }
	public void setAmount(BigDecimal value) { Amount = value; }

	public static String TRANSACTIONTYPE = "OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:TRNTYPE";
	public String TransactionType;
	public String getTransactionType() { return TransactionType; }
	public void setTransactionType(String value) { TransactionType = value; }
}
