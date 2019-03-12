package core.structure.item;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.structure.item.ComplexItem;
import core.structure.item.SimpleItem;

public class ComplexItemTest {
	
	private ComplexItem cItem;
	
	@Before
	public void setUp() throws Exception {
		cItem = new ComplexItem();
		List<SimpleItem> items = new ArrayList<SimpleItem>();
		items.add(new SimpleItem("a", "b"));
		items.add(new SimpleItem("c", "d"));
		cItem.items = items;
	}

	@Test
	public void testParse() {
		cItem.parse("e=f,g=h");
		assertEquals("equals", new SimpleItem("e", "f"),cItem.items.get(0));
		assertEquals("equals", new SimpleItem("g", "h"),cItem.items.get(1));
	}

	@Test
	public void testBuild() {
		assertEquals("equals", "a=b,c=d",cItem.build());
	}

}
