package core.structure.util;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
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
import core.structure.util.CRC16Helper;

@RunWith(value=Parameterized.class)
public class CRC16HelperTest {
	
	private String str;
	private int excepted;

	public CRC16HelperTest(String str, int excepted) {
		super();
		this.str = str;
		this.excepted = excepted;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		return Arrays.asList(new Object[][]
				{
					{"QN=20040516010101001;ST=22;CN=1011;PW=654321;MN=88888880000001;"
							+ "Flag=3;CP=&&&&", Integer.parseInt("1C80", 16)},
					{"ST=32;CN=2011;PW=123456;MN=0873HHMZWSLH02;CP=&&DataTime=20160103133030;"
							+ "060-Rtd=3.0943,060-Flag=N;011-Rtd=18.0412,011-Flag=N;001-Rtd=8.2137,"
							+ "001-Flag=N;B01-Rtd=10.9890,B01-Flag=N&&", Integer.parseInt("A9C1", 16)},
					{"ST=32;CN=2051;PW=123456;MN=0873HHMZWSLH02;CP=&&DataTime=20160103133000;060-Cou=0.0174,"
							+ "060-Min=3.0943,060-Avg=3.0943,060-Max=3.0943;011-Cou=0.1013,011-Min=18.0412,"
							+ "011-Avg=18.0412,011-Max=18.0412;001-Min=8.1325,001-Avg=8.2838,001-Max=8.4103;"
							+ "B01-Cou=5.6166,B01-Min=0.0000,B01-Avg=9.3610,B01-Max=23.1990&&", 
							Integer.parseInt("4040", 16)}
				});
	}
	
	@Test
	public void testCRC16_Checkout() throws UnsupportedEncodingException {
		assertEquals("equals", excepted, CRC16Helper.CRC16_Checkout(str.getBytes("US-ASCII"), str.length()));
	}

}
