package core.structure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import core.structure.CommandPara;
import core.structure.DataZone;

@RunWith(value=Parameterized.class)
public class CommandParaTest {

	private CommandPara cp;
	private DataZone dz;
	private String str;
	
	public CommandParaTest(CommandPara cp, DataZone dz, String str) {
		super();
		this.cp = cp;
		this.dz = dz;
		this.str = str;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Parameters
	public static Collection<Object[]> getParameters()
	{
		DataZone dz1 = new DataZone("PW=654321");
		DataZone dz2 = new DataZone("DataTime=20040516020111;101-Rtd=1.1,101-Flag=N");
		return Arrays.asList(new Object[][]
				{
				    {new CommandPara(dz1), dz1, "CP=&&PW=654321&&"},
				    {new CommandPara(dz2), dz2, "CP=&&DataTime=20040516020111;101-Rtd=1.1,101-Flag=N&&"}
				});
	}
	
	@Test
	public void testParse() {
		cp.parse(str);
		assertEquals("equals", dz, cp.dataZone);
	}

	@Test
	public void testBuild() {
		assertEquals("equals", str, cp.build());
	}

}
