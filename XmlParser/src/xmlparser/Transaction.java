package xmlparser;

import java.math.BigDecimal;
import java.util.Date;
import org.w3c.dom.Node;

public class Transaction {
	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:CHECKNUM
	public String CheckNumber;
	Transaction(Node curr) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	public String getCheckNumber() { return CheckNumber; }
	public void setCheckNumber(String value) { CheckNumber = value; }
	
	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:DTPOSTED
	public Date Posted;
	public Date getPosted() { return Posted; }
	public void setPosted(Date value) { Posted = value; }
	
	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:FITID
	public String Identifier;
	public String getIdentifier() { return Identifier; }
	public void setIdentifier(String value) { Identifier = value; }
	
	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:MEMO
	public String Memo;
	public String getMemo() { return Memo; }
	public void setMemo(String value) { Memo = value; }
	
	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:NAME
	public String Name;
	public String getName() { return Name; }
	public void setName(String value) { Name = value; }

	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:TRNAMT
	public BigDecimal Amount;
	public BigDecimal getAmount() { return Amount; }
	public void setAmount(BigDecimal value) { Amount = value; }

	//:OFX:BANKMSGSRSV1:STMTTRNRS:STMTRS:BANKTRANLIST:STMTTRN:TRNTYPE
	public String TransactionType;
	public String getTransactionType() { return TransactionType; }
	public void setTransactionType(String value) { TransactionType = value; }
}
