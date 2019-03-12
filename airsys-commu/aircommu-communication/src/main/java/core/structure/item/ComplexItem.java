package core.structure.item;

import java.util.ArrayList;
import java.util.List;

public class ComplexItem extends Item
{
	public List<SimpleItem> items;

	public ComplexItem() {
		super();
	}

	public ComplexItem(List<SimpleItem> items) {
		super();
		this.items = items;
	}
	
	public boolean parse(String str)
	{
		String[] temp = str.split(",");
		SimpleItem sItem;
		items = new ArrayList<SimpleItem>();
		for(int i = 0; i < temp.length; i++)
		{
			sItem = new SimpleItem();
			sItem.parse(temp[i]);
			items.add(sItem);
		}
		return true;
	}
	
	public String build()
	{
		StringBuffer sb = new StringBuffer();
		SimpleItem sItem;
		for(int i = 0; i < items.size(); i++)
		{
			sItem = items.get(i);
			sb.append(sItem.build());
			if(i < items.size() - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result;
		
		if(this.items.size() != ((ComplexItem)obj).items.size())
		{
			result = false;
		}
		else
		{
			for(int i = 0; i < this.items.size(); i++)
			{
				if(this.items.get(i).equals(((ComplexItem)obj).items.get(i)) == false)
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
