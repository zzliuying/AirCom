package core.structure;

import java.io.UnsupportedEncodingException;

public class DataSegment implements IElement{
	public String QN;
	public String PNUM;
	public String PNO;
	public String ST;
	public String CN;
	public String PW;
	public String MN;
	public String Flag;
	public IElement CP;
	
	public DataSegment(String qN, String pNUM, String pNO, String sT,
			String cN, String pW, String mN, String flag, IElement cP) {
		super();
		if(qN != null)
			QN = qN;
		if(pNUM != null)
			PNUM = pNUM;
		if(pNO != null)
			PNO = pNO;
		if(sT != null)
			ST = sT;
		if(cN != null)
			CN = cN;
		if(pW != null)
			PW = pW;
		if(mN != null)
			MN = mN;
		if(flag != null)
			Flag = flag;
		CP = cP;
	}
	
	public DataSegment(String str) {
		super();
		parse(str);
	}
	
	public boolean parse(String str)
	{
		String basicStr = str.substring(0, str.indexOf("CP") - 1);
		String cpStr = str.substring(str.indexOf("CP"));
		String[] subItem = basicStr.split(";");
		String[] kv;
		for(int i = 0; i < subItem.length; i++)
		{
			kv = subItem[i].split("=");
			setBasic(kv[0], kv[1]);
		}
		
		CP = new CommandPara(cpStr);
		
		return true;
	}
	
	private void setBasic(String key, String value)
	{
		if("QN".equals(key))
		{
			this.QN = value;
		}
		else if("PNUM".equals(key))
		{
			this.PNUM = value;
		}
		else if("PNO".equals(key))
		{
			this.PNO = value;
		}
		else if("ST".equals(key))
		{
			this.ST = value;
		}
		else if("CN".equals(key))
		{
			this.CN = value;
		}
		else if("PW".equals(key))
		{
			this.PW = value;
		}
		else if("MN".equals(key))
		{
			this.MN = value;
		}
		else if("Flag".equals(key))
		{
			this.Flag = value;
		}
	}
	
	public String build() throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer();
		if(this.QN != null)
		{
			sb.append("QN=");
			sb.append(this.QN);
			sb.append(";");
		}

		if(this.PNUM != null)
		{
			sb.append("PNUM=");
			sb.append(this.PNUM);
			sb.append(";");
		}
		
		if(this.PNO != null)
		{
			sb.append("PNO=");
			sb.append(this.PNO);
			sb.append(";");
		}
		
		if(this.ST != null)
		{
			sb.append("ST=");
			sb.append(this.ST);
			sb.append(";");
		}
		
		if(this.CN != null)
		{
			sb.append("CN=");
			sb.append(this.CN);
			sb.append(";");
		}
		
		if(this.PW != null)
		{
			sb.append("PW=");
			sb.append(this.PW);
			sb.append(";");
		}
		
		if(this.MN != null)
		{
			sb.append("MN=");
			sb.append(this.MN);
			sb.append(";");
		}
		
		if(this.Flag != null)
		{
			sb.append("Flag=");
			sb.append(this.Flag);
			sb.append(";");
		}
		
		sb.append(CP.build());
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result;
		DataSegment ds = (DataSegment)obj;
		if(this.CN != null && ds.CN != null && this.CN.equals(ds.CN) == false)
		{
			result = false;
		}
		else if(this.CP != null && ds.CP != null && this.CP.equals(ds.CP) == false)
		{
			result = false;
		}
		else if(this.Flag != null && ds.Flag != null && this.Flag.equals(ds.Flag) == false)
		{
			result = false;
		}
		else if(this.MN != null && ds.MN != null && this.MN.equals(ds.MN) == false)
		{
			result = false;
		}
		else if(this.PNO != null && ds.PNO != null && this.PNO.equals(ds.PNO) == false)
		{
			result = false;
		}
		else if(this.PNUM != null && ds.PNUM != null && this.PNUM.equals(ds.PNUM) == false)
		{
			result = false;
		}
		else if(this.PW != null && ds.PW != null && this.PW.equals(ds.PW) == false)
		{
			result = false;
		}
		else if(this.QN != null && ds.QN != null && this.QN.equals(ds.QN) == false)
		{
			result = false;
		}
		else if(this.ST != null && ds.ST != null && this.ST.equals(ds.ST) == false)
		{
			result = false;
		}
		else
		{
			result = true;
		}
			 
		return result;
	}

} 
