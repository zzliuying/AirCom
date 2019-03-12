package core.structure;

import java.io.UnsupportedEncodingException;

import core.structure.util.CRC16Helper;

public class DataPackage implements IElement{
	public static final String header = "##";
	public String dataSegLen;
	public DataSegment dataSeg;
	public int CRC;
	public static final String tail = "\r\n";
	
	public DataPackage() {
		super();
	}
	
	public DataPackage(DataSegment dataSeg) {
		super();
		this.dataSeg = dataSeg;
	}

	public boolean parse(String str)
	{
		try {
			dataSegLen = str.substring(2, 6);
			String segStr = str.substring(6, Integer.parseInt(dataSegLen, 10) + 6);
			dataSeg = new DataSegment(segStr);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
		
	}
	
	public String build() throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		String dsStr = dataSeg.build();
		sb.append(String.format("%04d", dsStr.length()));
		sb.append(dsStr);
		CRC = CRC16Helper.CRC16_Checkout(dsStr.getBytes("US-ASCII"), dsStr.length());
		sb.append(String.format("%04X", CRC));
		sb.append(tail);
		
		return sb.toString();
	}
}









