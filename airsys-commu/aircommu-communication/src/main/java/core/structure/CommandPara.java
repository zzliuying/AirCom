package core.structure;

public class CommandPara implements IElement {
	public DataZone dataZone;
	
	public CommandPara() {
		super();
	}
	
	public CommandPara(DataZone dataZone) {
		super();
		this.dataZone = dataZone;
	}

	public CommandPara(String str) {
		super();
		parse(str);
	}
	
	/* (non-Javadoc)
	 * @see core.structure.IElement#parse(java.lang.String)
	 */
	public boolean parse(String str)
	{
		boolean result = false;
		if(str.length() > 5)
		{
			String dzStr = str.substring(5, str.length() - 2);
			dataZone = new DataZone();
			result = dataZone.parse(dzStr);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see core.structure.IElement#build()
	 */
	public String build()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("CP=&&");
		if(dataZone != null)
		{
			sb.append(dataZone.build());
		}
		sb.append("&&");
		
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see core.structure.IElement#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result;
		
		if(this.dataZone.equals(((CommandPara)obj).dataZone))
		{
			result = true;
		}
		else
		{
			result = false;
		}
		
		return result;
	}
}
