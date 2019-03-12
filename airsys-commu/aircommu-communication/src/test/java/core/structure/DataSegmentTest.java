package core.structure;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import core.structure.CommandPara;
import core.structure.DataSegment;
import core.structure.DataZone;

@RunWith(value=Parameterized.class)
public class DataSegmentTest {

	private DataSegment expected;
	private DataSegment value;
	private String str;
	
	public DataSegmentTest(DataSegment expected, DataSegment value, String str) {
		super();
		this.expected = expected;
		this.value = value;
		this.str = str;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		DataSegment excepted1 = new DataSegment("20040516010101001", null, null, "32", "1000", "123456", 
				"88888880000001", "3", new CommandPara("CP=&&OverTime=5;ReCount=3&&"));
		DataSegment value1 = new DataSegment("QN=20040516010101001;ST=32;CN=1000;PW=123456;"
				+ "MN=88888880000001;Flag=3;CP=&&OverTime=5;ReCount=3&&");
		
		DataSegment excepted2 = new DataSegment(null, null, null, "91", "9012", "123456", 
				"88888880000001", null, new CommandPara("CP=&&QN=20040516010101001;ExeRtn=1&&"));
		DataSegment value2 = new DataSegment("ST=91;CN=9012;PW=123456;MN=88888880000001;"
				+ "CP=&&QN=20040516010101001;ExeRtn=1&&");
		
		return Arrays.asList(new Object[][]
				{
				    {excepted1, value1, "QN=20040516010101001;ST=32;CN=1000;PW=123456;"
							+ "MN=88888880000001;Flag=3;CP=&&OverTime=5;ReCount=3&&"},
					{excepted2, value2, "ST=91;CN=9012;PW=123456;MN=88888880000001;"
							+ "CP=&&QN=20040516010101001;ExeRtn=1&&"}
				});
	}
	
	@Test
	public void testParse() {
		assertEquals("equals", expected, value);
	}

	@Test
	public void testBuild() {
		try {
			assertEquals("equals", str, expected.build());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
