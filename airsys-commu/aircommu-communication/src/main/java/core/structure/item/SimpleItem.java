package core.structure.item;

import core.structure.item.Item;
/*
 * 键值对
 */
public class SimpleItem extends Item
{
	public SimpleItem()
	{
		super();
	}
	
	public SimpleItem(String str)
	{
		super();
		parse(str);
	}
	
	public SimpleItem(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public boolean parse(String str)
	{
		String[] temp = str.split("=");
		this.key = temp[0];
		this.value = temp[1];
		return true;
	}
	
	public String build()
	{
		return new String(key + "=" + value);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result;
		if(this.key.equals(((SimpleItem)obj).key) && this.value.equals(((SimpleItem)obj).value))
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}

	public String key;
	public String value;
	
	
}
