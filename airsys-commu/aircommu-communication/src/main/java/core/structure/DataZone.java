package core.structure;

import java.util.ArrayList;
import java.util.List;

import core.structure.item.ComplexItem;
import core.structure.item.Item;
import core.structure.item.SimpleItem;

public class DataZone implements IElement{
	public List<Item> itemList;
	
	public DataZone() {
		super();
	}

	public DataZone(String str) {
		super();
		parse(str);
	}
	
	public DataZone(List<Item> itemList) {
		super();
		this.itemList = itemList;
	}
	
	public boolean parse(String str)
	{
		String[] itemTemp;
		SimpleItem sItem;
		ComplexItem cItem;
		
		if(str != null && str.equals("") == false)
		{
			itemList = new ArrayList<Item>();
			itemTemp = str.split(";");
			for(int i = 0; i< itemTemp.length; i++)
			{
				if(itemTemp[i].contains(",") == true)
				{
					cItem = new ComplexItem();
					cItem.parse(itemTemp[i]);
					//itemList.add(cItem);
					for(int j=0; j<cItem.items.size(); j++)
					{
						itemList.add(cItem.items.get(j));
					}
				}
				else
				{
					sItem = new SimpleItem();
					sItem.parse(itemTemp[i]);
					itemList.add(sItem);
				}
			}
		}
		
		return true;	
	}
	
	public String build()
	{
		StringBuffer sb = new StringBuffer();
		SimpleItem sItem;
		ComplexItem cItem;
		if(itemList != null)
		{
			for(int i = 0; i < itemList.size(); i++)
			{
				if(itemList.get(i) instanceof ComplexItem == true)
				{
					cItem = (ComplexItem)itemList.get(i);
					sb.append(cItem.build());
				}
				else if(itemList.get(i) instanceof SimpleItem == true)
				{
					sItem = (SimpleItem)itemList.get(i);
					sb.append(sItem.build());
				}
				if(i < itemList.size() - 1)
				{
					sb.append(";");
				}
			}
		}
		
		return sb.toString();		
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result;
		
		if(this.itemList.size() != ((DataZone)obj).itemList.size())
		{
			result = false;
		}
		else
		{
			for(int i = 0; i < this.itemList.size(); i++)
			{
				if(this.itemList.get(i).equals(((DataZone)obj).itemList.get(i)) == false)
				{
					result = false;
					break;
				}
			}
			result = true;
		}
		
		return result;
	}
}
