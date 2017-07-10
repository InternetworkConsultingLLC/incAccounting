package net.internetworkconsulting.data;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Helper {
	public static String InputStreamToString(InputStream input_stream) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
		
		String sBuffer = br.readLine();
		while(sBuffer != null) {
			sb.append(sBuffer);
			sb.append(System.lineSeparator());
			sBuffer = br.readLine();
		}
		
		return sb.toString();
	}
	public static List<String> InputStreamToStringList(InputStream input_stream) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
		List<String> lstRet = new LinkedList<>();
		
		String sBuffer = br.readLine();
		while(sBuffer != null) {
			lstRet.add(sBuffer);
			sBuffer = br.readLine();
		}
		
		return lstRet;
	}
	public static String[] InputStreamToStringArray(InputStream input_stream) throws Exception {
		return InputStreamToStringList(input_stream).toArray(new String[0]);
	}	
	public static String FileToString(String file_name) throws Exception {
		FileInputStream fis = new FileInputStream(file_name);
		return InputStreamToString(fis);
	}
	
	final private static char[] arrHexChars = "0123456789ABCDEF".toCharArray();
	public static String ByteArrayToHex(byte[] bytes) {
		char[] arrRetChar = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			arrRetChar[j * 2] = arrHexChars[v >>> 4];
			arrRetChar[j * 2 + 1] = arrHexChars[v & 0x0F];
		}
		return new String(arrRetChar);
	}
		
	public static byte[] HexToByteArray(String hex) {
		int len = hex.length();
		byte[] ret = new byte[len / 2];
		for(int cnt = 0; cnt < len; cnt += 2)
			ret[cnt / 2] = (byte) ( (Character.digit(hex.charAt(cnt), 16) << 4) + (Character.digit(hex.charAt(cnt + 1), 16)) );
		return ret;
	}
	public static String Increment(String value) {
		BigInteger bi = new BigInteger(value, 10);
		return bi.add(BigInteger.ONE).toString(10);
	}
	public static String numberToText(String language, String country, String currency, BigDecimal amount) {
		BigDecimal dWholeNumbers = amount.divide(BigDecimal.ONE, 0, RoundingMode.FLOOR);
		BigDecimal dFraction = amount.subtract(dWholeNumbers).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.ONE, 0, RoundingMode.HALF_UP);

		RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale(language, country), RuleBasedNumberFormat.SPELLOUT);

		String sOutput = ruleBasedNumberFormat.format(dWholeNumbers);
		sOutput = UCharacter.toTitleCase(new Locale(language, country), sOutput, null, 0);

		if(dFraction.compareTo(BigDecimal.ZERO) != 0)
			sOutput += " and " + String.format("%2.0f", dFraction) + "/100ths " + currency;
		
		return sOutput;
	}

	public static List<Node> getXmlNodes(Node start, String target) {
		List<Node> lstRet = new LinkedList<Node>();
		
		if(Helper.getXmlName(start) == null)
			return lstRet;
		
		if(target == null)
			return lstRet;
		
		if(Helper.getXmlName(start).endsWith(target))
			lstRet.add(start);
		
		NodeList nlChildren = start.getChildNodes();
		for(int cnt = 0; cnt < nlChildren.getLength(); cnt++)
			lstRet.addAll(getXmlNodes(nlChildren.item(cnt), target));
		
		return lstRet;
	}
	public static String getXmlName(Node item) {
		String sName = "";
		
		Node nCurrent = item;
		sName = ":" + nCurrent.getNodeName() + sName;
		
		while(nCurrent.getParentNode() != null) {
			nCurrent = nCurrent.getParentNode();
			sName = ":" + nCurrent.getNodeName() + sName;
		}
		
		return sName.replace(":#document:", "");
	}
	public static String getXmlValue(Node start, String target) throws Exception {
		List<Node> lstNodes = getXmlNodes(start, target);
		if(lstNodes.size() < 1)
			return null;
		if(lstNodes.size() > 1)
			throw new Exception("Could not locate unique node!");
		
		if(lstNodes.get(0).getFirstChild() == null)
			return null;
		
		String sValue = lstNodes.get(0).getFirstChild().getNodeValue();
		String sName = lstNodes.get(0).getFirstChild().getNodeName();
		short iType = lstNodes.get(0).getFirstChild().getNodeType();
		
		return sValue;
	}
}
