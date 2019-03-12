package core.structure.util;

public class CRC16Helper
{
	public static int CRC16_Checkout (byte[] bs, int usDataLen )
	{
		int i,j,crc_reg,check;
		crc_reg = 0xFFFF;
		for(i=0;i<bs.length;i++)
		{
			crc_reg = (crc_reg>>8) ^ bs[i];
			for(j=0;j<8;j++)
			{
				check = crc_reg & 0x0001;
				crc_reg >>= 1;
				if(check==0x0001)
				{
					crc_reg ^= 0xA001;
				}
			}
		}
		return crc_reg;
	}
}