package core.structure;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import core.structure.DataZone;
import core.structure.item.ComplexItem;
import core.structure.item.Item;
import core.structure.item.SimpleItem;

@RunWith(value=Parameterized.class)
public class DataZoneTest {

	private DataZone dz;
	private List<Item> items;
	private String str;
	
	public DataZoneTest(DataZone dz, List<Item> items, String str) {
		super();
		this.dz = dz;
		this.items = items;
		this.str = str;
	}

	@Before
	public void setUp() throws Exception {

	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		List<Item> l1 = new ArrayList<Item>();
		l1.add(new SimpleItem("PW=654321"));
		List<Item> l2 = new ArrayList<Item>();
		l2.add(new SimpleItem("DataTime=20040516020111"));
		
		List<SimpleItem> l3 = new ArrayList<SimpleItem>();
		l3.add(new SimpleItem("101-Rtd=1.1"));
		l3.add(new SimpleItem("101-Flag=N"));
		ComplexItem cItem = new ComplexItem(l3);
		l2.add(cItem);

		return Arrays.asList(new Object[][]
				{
					{new DataZone(l1), l1, "PW=654321"},
					{new DataZone(l2), l2, "DataTime=20040516020111;101-Rtd=1.1,101-Flag=N"},
					{new DataZone(), null, ""}
				});
	}
	
	@Test
	public void testParse() {
		dz.parse(str);
		assertEquals("equals", items, dz.itemList);
	}

	@Test
	public void testBuild() {
		assertEquals("equals", str, dz.build());
	}

}
