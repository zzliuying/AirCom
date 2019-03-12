package app.center.custom;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import core.structure.DataPackage;
import core.structure.util.QNGenerator;
import app.center.func.slave.CenterRecvProcessor;
import app.util.HJT212Communicate;
import app.util.LocalInfo;
import app.util.RequestFactory;
/*
 * 没有实现反控函数212 代理
 */
public class HJT212LocalProxy extends LocalProxy{
		
	public static Logger logger = Logger.getLogger(HJT212LocalProxy.class);
	public LocalInfo localInfo;
	public HJT212Communicate cBase;
	public RequestFactory reqFactory;
	private DataPackage inPck;
	private DataPackage outPck;
	
	public CenterRecvProcessor recvProcessor;
	
	public boolean retChangedFlag;
	public String retString;
	
	public LocalProxyManager proxyManager;
	protected LinkedBlockingQueue<CustomRequest> list = new LinkedBlockingQueue<CustomRequest>();
	public boolean registFlag;

	public HJT212LocalProxy(HJT212Communicate cBase, LocalProxyManager manager/*,LinkedBlockingQueue<CustomResponse> repList*/) {
		super();
		this.cBase = cBase;
		localInfo = new LocalInfo(null, null, null);
		recvProcessor = new CenterRecvProcessor(localInfo);	//注册处理类	
		reqFactory = new RequestFactory(localInfo, new QNGenerator());//生成212协议的工厂类
		proxyManager = manager;
	}
	public void loadingRTData(boolean loadRtnFlag)
	{
		
	}
	/*
	 * 接收web端命令的接口
	 * 该接口由web端接收服务器来调用，将web端发来的请求转成命令存储到待处理命令请求list中
	 */
	public void addReq(CustomRequest req)
	{
		list.add(req);
	}
	/*
	 * 返回web端命令执行结果的接口
	 * 将返回结果添加到返回list中，web端端接收服务器对list中的返回结果进行处理反控给web端 
	 */
	public void addResp(CustomResponse resp)
	{
		resp.mn =localInfo.MN;
		responseList.add(resp);
	}
	
	/*
	 * 判断网络异常的测试函数
	 */
	public boolean testNetConn() {
		// TODO Auto-generated method stub
		boolean ret=true;
		try {
			//发送检测是否断开  
            cBase.sock.sendUrgentData(0xFF);
            return ret;
        } catch (IOException e) {//断开产生异常，关闭对象  
           
        	ret =false; 
        	System.out.println("isrunning =  "+isRunning);
        	return ret;          
        } 

		
	}
	
	/*
	 * (non-Javadoc)
	 * @see app.center.custom.LocalProxy#recvToQueue()
	 * 从socket中读数据，存入缓存队列queue
	 */
	@Override
	protected void recvToQueue() {
		// TODO Auto-generated method stub		
		ArrayList<DataPackage> list = new ArrayList<DataPackage>();
		
		while(isRunning)
		{	
				try {
					list = cBase.recvPck();
					for(int i=0; i<list.size();i++)
					{										
						queue.put(list.get(i));//存入缓存队列中
					}
					//System.out.println("queue.size----------------------------"+queue.size());
				}catch(SocketTimeoutException exception)				   
				{
					// 网络超时或中断后关闭socket
					isRunning = false;
					//System.out.println("HJT212LocalProxy.recvToQueue().....SocketTimeoutException.......isRunning="+isRunning);
					logger.warn("SocketTimeoutException.......isRunning="+isRunning);
					try {
						if(cBase.sock!=null)
						{
							cBase.sock.close();
							//System.out.println("HJT212LocalProxy.recvToQueue()......socket   closed!!!");
							logger.warn("socket   closed!!!");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block						
						e.printStackTrace();
					}
					exception.printStackTrace();
				}catch (SocketException e) {
					// TODO: handle exception
					isRunning = false;
					e.printStackTrace();
					break;
				}catch(IOException ee)
				{
					isRunning = false;
					ee.printStackTrace();
					break;					
				}catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();										
		    }
		}
	}
	
	

	/*
	 * 	(non-Javadoc)
	 * @see app.center.custom.LocalProxy#handleRecv()
	 * 处理接收缓存队列中的212数据
	 */
	@Override
	protected void handleRecv() {
		// TODO Auto-generated method stub
		//从缓存队列中取，然后通知观察者
		
		//handleRegist();
		while(isRunning)
		{
			try {
				//inPck = (DataPackage)queue.take();
				inPck = (DataPackage)queue.poll(10, TimeUnit.SECONDS);
				if (inPck !=null) {
					setChanged();
					notifyObservers(inPck);//通知观察者
				}

					
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}			
		}		
	}

	/*
	 * 处理注册信息，由专门的注册processor来处理
	 */
	public void handleRegist(DataPackage inpack){
			recvProcessor.process(inpack, outPck);
			//System.out.println("localInfo.MN="+localInfo.MN);
			proxyManager.registProxy(localInfo.MN, this);
			
	}
//	protected void handleRegist(){
//		try {
//			inPck = (DataPackage)queue.take();
//			recvProcessor.process(inPck, outPck);
//			System.out.println("localInfo.MN="+localInfo.MN);
//			proxyManager.registProxy(localInfo.MN, this);
//				
//		}catch(InterruptedException e)
//		{
//			e.printStackTrace();
//		}	
//		
//	}


	
		


	
}
