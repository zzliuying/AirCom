package app.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import app.util.HJT212FormatValidator;
import core.structure.CommandPara;
import core.structure.DataZone;
@RunWith(value=Parameterized.class)
public class HJT212FormatValidatorTest {
	private String str;
	private boolean expected;

	public HJT212FormatValidatorTest(String str, boolean expected) {
		super();
		this.str = str;
		this.expected = expected;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		return Arrays.asList(new Object[][]
				{
				    {"##0101QN=20160801085857223;ST=32;CN=1062;PW=100000;"
				    		+ "MN=010000A8900016F000169DC0;Flag=5;CP=&&RtdInterval=30&&1c80\r\n", true},
				    {"##0178ST=32;CN=2011;PW=123456;MN=0873HHMZWSLH02;"
							+ "CP=&&DataTime=20160103133030;060-Rtd=3.0943,060-Flag=N;011-Rtd=18.0412,011-Flag=N;"
							+ "001-Rtd=8.2137,001-Flag=N;B01-Rtd=10.9890,B01-Flag=N&&a9c1\r\n", true}
				});
	}
	
	@Test
	public void test() {
		assertEquals("equals", expected, HJT212FormatValidator.validate(str));
	}

}
