package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class CommunicateBase {
	public InputStream in;
	public OutputStream out;
	public Socket sock;
	//public boolean socketConnFlag=true;
	
	public CommunicateBase(Socket sock)
	{
		this.sock = sock;
		init();
	}
	
	void init()
	{
		try {
			in = sock.getInputStream();
			out = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * socket发送数据接口
	 */
	public void sendBytes(byte[] bytes) throws IOException
	{
			out.write(bytes);
	}
	/*
	 * socket接收数据接口
	 */
	public byte[] recvBytes() throws IOException
	{
		byte[] bytes = new byte[1024];

			int len = in.read(bytes);				
			if(len>0)
			{
			byte[] validBytes = Arrays.copyOfRange(bytes, 0, len);	
			return validBytes;
			}
			else {
				byte[] ilbytes = {'*','*','*','*'};//若socket输入流关闭返回-1后，进行特殊处理
				return ilbytes;
			}
	}
	/*
	 * socket发送string类型数据接口
	 */
	protected void sendStr(String data) throws IOException
	{
		sendBytes(data.getBytes("US-ASCII"));	
	}
	
	/*
	 * socket接收string类型数据接口
	 */
	protected String recvStr() throws IOException
	{
		return new String(recvBytes(), "US-ASCII");
	}
}
