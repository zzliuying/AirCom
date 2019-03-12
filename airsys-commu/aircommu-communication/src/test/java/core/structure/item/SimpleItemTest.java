package core.structure.item;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.structure.item.SimpleItem;

public class SimpleItemTest {
	
	private SimpleItem sItem;

	@Before
	public void setUp() throws Exception {
		sItem = new SimpleItem();
	}

	@Test
	public void testParse() {
		sItem.parse("a=b");
		assertEquals("equals", "a", sItem.key);
	}

	@Test
	public void testBuild() {
		sItem.key = "a";
		sItem.value = "b";
		assertEquals("equals", "a=b",sItem.build());
	}

}
