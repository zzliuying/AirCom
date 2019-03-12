package app.util;



import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import app.com.DataToFile;
import core.structure.DataPackage;
import core.structure.util.CRC16Helper;

public class HJT212Communicate extends CommunicateBase {
	public static Logger logger = Logger.getLogger(HJT212Communicate.class);
	private String leftDataString=null;
	SimpleDateFormat dFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//	System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	DataToFile dtstreamToFile = new DataToFile();

	public HJT212Communicate(Socket sock) {
		super(sock);
	}
	
	/*
	 * 212协议数据发送接口
	 */
	public void sendPck(DataPackage data) throws IOException
	{
		String str = data.build();
		sendStr(str);
		

		System.out.println(dFormat.format(new Date())+"  send 212 data:" + str);
	}
	
	/*
	 * 212协议数据接收接口
	 * 处理方式：由于有时会有半帧数据的情况，所以处理时，只处理完整的数据帧，对于半帧数据，需要合并到下一次接收的数据流的前面，然后在解析
	 */
	public ArrayList<DataPackage> recvPck() throws IOException
	{		
		ArrayList<DataPackage> list = new ArrayList<DataPackage>();
		String recvStr;
		recvStr = new String(recvBytes(), "US-ASCII");
		if(recvStr.compareTo("****")==0)
		{
			SocketException exception = new SocketException("socket read close!");
			throw exception;
		}
		else {
			logger.info("recved data:" + recvStr);
			//System.out.println(dFormat.format(new Date())+"  recved data:" + recvStr);

	//3版本	
			//先判断是否是DTU的心跳包数据
			if(recvStr.compareTo("123456789")==0)
			{
				System.out.println("no-212 data:" + recvStr);
			}else {
				if(recvStr.contains("&&")==true)
				{
					String recvData =recvStr;
					if(leftDataString!=null)
					{
						recvData=leftDataString+recvStr;
						leftDataString=null;
					}
					//根据包头的特殊字符 ## 来分割每一帧数据	
					String[] splitStrings=recvData.split("##");
					for(int i=1; i<splitStrings.length; i++)
					{
						//读取数据长度字段的值，来截取后续字符流中该帧的长度
						String lenString=splitStrings[i].substring(0, 4);
						//匹配【0-9】
						if(lenString.matches("\\d{4}"))
						{							
							int lenint = Integer.parseInt(lenString);//转成int							
							//判断是否是半帧数据
							if(splitStrings[i].length()>= lenint+10)
							{
								//完整帧的处理
								String segString = splitStrings[i].substring(4, lenint+4);								
								String tempString="##"+splitStrings[i];
								//正则匹配
								if(HJT212FormatValidator.validate(segString))
								{
									//crc校验
									String nocrcString = tempString.substring(6, tempString.length()-6);
									String crcString = tempString.substring(tempString.length()-6,tempString.length()-2);
									int crc = CRC16Helper.CRC16_Checkout(nocrcString.getBytes("US-ASCII"), nocrcString.length());
									String crcs=String.format("%04X", crc);									
									if(crcString.compareTo(crcs)==0)
									{	
										//解析数据包，添加到接收数据包列表中
										DataPackage pck = new DataPackage();																
										if(pck.parse(tempString))//进行parse
											list.add(pck);	
										//80001数据流存文件
										if(pck.dataSeg.MN.compareTo("88888880000001")==0)
											dtstreamToFile.saveDataTxt1(tempString);
										logger.info("212 data:" + tempString);
									}																		
								}
								else {
									logger.error("error data:" +tempString);
									//System.out.println("error data:" +tempString);
								}
							}else {
								//半帧数据的话，将其还原并保存，等待下次接收
								leftDataString="##"+splitStrings[i];
							}
						}
					}																		
				}else {
					logger.warn("not-212 data:" + recvStr);
					//System.out.println("not-212 data:" + recvStr);
				}
			}
		}
		
		
		
//2版本		
//		if(recvStr.contains("##")==true)
//		{
//			String[] splitStrings=recvStr.split("##");
//			//System.out.println("splitStrings.length="+splitStrings.length);
//			for(int i=1; i<splitStrings.length; i++)
//				{
//					//System.out.println("数据包长度="+splitStrings[i].length());
//					String lenString=splitStrings[i].substring(0, 4);
//					//匹配【0-9】
//					if(lenString.matches("\\d{4}"));
//					{
//						
//						int lenint = Integer.parseInt(lenString);//转成int
//						//System.out.println("matches------[0-9]  lenint="+lenint);
//						if(splitStrings[i].length()>= lenint+10)
//						{
//							String segString = splitStrings[i].substring(4, lenint+4);
//							//System.out.println("segString="+segString);
//							String tempString="##"+splitStrings[i];
//							//正则匹配
//							if(HJT212FormatValidator.validate(segString))
//							{
//								//System.out.println("matches------()()()(0)");
//								DataPackage pck = new DataPackage();								
//								if(pck.parse(tempString))//进行parse
//									list.add(pck);	
//								System.out.println("recved 212 data:" + tempString);
//							}
//							else {
//								System.out.println("recved error data:" +tempString);
//							}
//						}
//					}															
//				}
//		}
//		else {
//			System.out.println("recved no-212 data:" + recvStr);
//		}
		
		
//1版本		
//		String recvStr;
//		while(isRecved == false)
//		{
//			recvStr = new String(recvBytes(), "US-ASCII");
//			if(HJT212FormatValidator.validate(recvStr) == true)
//			{
//				//是212协议格式帧
//				
//				System.out.println("recved 212 data:" + recvStr);
//				recvStr+="  ";
//				String[] splitStrings=recvStr.split("\r\n");
//				int i=0;
//				for(; i<splitStrings.length-1; i++)
//				{
//					System.out.println("数据包长度="+splitStrings[i].length());
//					String lenString=splitStrings[i].substring(2, 6);
//					int lenint = Integer.parseInt(lenString);
//					System.out.println("数据段长度"+lenint);
//						DataPackage pck = new DataPackage();
//						pck.parse(splitStrings[i]+"\r\n");
//						list.add(pck);											
//				}
//				System.out.println("数据包长度1="+splitStrings[i].length());
//				String lenString=splitStrings[i].substring(2, 6);
//				int lenint = Integer.parseInt(lenString);
//				System.out.println("数据段长度1"+lenint);
//				if ((splitStrings[i].contains("\r\n")) && (lenint == splitStrings[i].length()-10) )
//				{   //10=数据段长度4+crc4+包尾2，，包头##已被去掉
//					DataPackage pck = new DataPackage();
//					pck.parse(splitStrings[i]+"\r\n");
//					list.add(pck);
//				}
//				String[] splitStrings=recvStr.split("\r\n");
//				for(int i=0; i<splitStrings.length; i++)
//				{
//					System.out.println("HJT212Communicate.recvPck()-----"+splitStrings[i]);
//					pck.parse(splitStrings[i].concat("\r\n"));
//					list.add(pck);
//				}
//				//pck.parse(recvStr);
//				isRecved = true;
//
//			}
//			else
//			{
//				System.out.println("recved no-212 data:" + recvStr);
//			}
//		}


//		for(int i=0; i<list.size(); i++)
//		{
//			System.out.println("HJT212Communicate.recvPck()---------"+list.get(i).build());			
//		}
		//System.out.println("list.size() =   " + list.size());
		//return pck;
		return list;
	}
	

}
