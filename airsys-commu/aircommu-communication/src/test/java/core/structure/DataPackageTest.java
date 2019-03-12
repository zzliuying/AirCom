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
import core.structure.DataPackage;
import core.structure.DataSegment;

@RunWith(value=Parameterized.class)
public class DataPackageTest {

	private DataPackage pck;
	private DataSegment seg;
	private String str;
	
	public DataPackageTest(DataPackage pck, DataSegment seg, String str) {
		super();
		this.pck = pck;
		this.seg = seg;
		this.str = str;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		DataSegment seg1 = new DataSegment("QN=20160801085857223;ST=32;CN=1062;PW=100000;"
				    		+ "MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&");

		DataSegment seg2 = new DataSegment("ST=32;CN=2011;PW=123456;MN=0873HHMZWSLH02;"
							+ "CP=&&DataTime=20160103133030;060-Rtd=3.0943,060-Flag=N;011-Rtd=18.0412,011-Flag=N;"
							+ "001-Rtd=8.2137,001-Flag=N;B01-Rtd=10.9890,B01-Flag=N&&");
		
		DataSegment seg3 = new DataSegment("QN=20040516010101001;ST=32;CN=2011;PW=123456;MN=88888880000001;"
				+ "Flag=3;CP=&&&&");
		
		return Arrays.asList(new Object[][]
				{
				    {new DataPackage(), seg1, "##0101QN=20160801085857223;ST=32;CN=1062;PW=100000;"
				    		+ "MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1c80\r\n"},
					{new DataPackage(), seg2, "##0178ST=32;CN=2011;PW=123456;MN=0873HHMZWSLH02;"
							+ "CP=&&DataTime=20160103133030;060-Rtd=3.0943,060-Flag=N;011-Rtd=18.0412,011-Flag=N;"
							+ "001-Rtd=8.2137,001-Flag=N;B01-Rtd=10.9890,B01-Flag=N&&a9c1\r\n"},
					{new DataPackage(), seg3, "##0077QN=20040516010101001;ST=32;CN=2011;PW=123456;MN=88888880000001;"
							+ "Flag=3;CP=&&&&7bc0\r\n"}
				});
	}
	
	@Test
	public void testParse() {
		pck.parse(str);
		assertEquals("equals", seg, pck.dataSeg);
	}

	@Test
	public void testBuild() throws UnsupportedEncodingException {
		pck.dataSeg = seg;
		assertEquals("equals", pck.build(), str);
	}

}
