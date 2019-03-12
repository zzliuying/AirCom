package simpleserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import app.center.custom.CustomRequest;
import app.center.custom.CustomResponse;
import app.center.custom.HJT212LocalProxy;
import app.center.custom.LocalProxyManager;

/*
 * 和web通信
 */
public class WebServerSimple {

	public static Logger logger = Logger.getLogger(WebServerSimple.class);
	public static final int PORT = 60002;
	private final Executor exec = Executors.newFixedThreadPool(10);
	public LocalProxyManager proxyManager;
	public WebServerSimple(LocalProxyManager proxyManager)
	{
		this.proxyManager = proxyManager;
	}
	
	public void webInit(){
		ServerSocket serverSocket;
        try {  
            serverSocket = new ServerSocket(PORT);  
            //System.out.println("WEBServer starting...\n");
            logger.warn("WEBServer starting...");
            
            while (true) {   
                Socket webClient = serverSocket.accept();            	
				exec.execute(new Handler(webClient));
           
            }  
        } catch (Exception e) {  
            //System.out.println("server error:" + e.getMessage());  
        	logger.error("server error:" + e.getMessage());
        } 
	}
	protected class Handler implements Runnable {  
		InputStream webin;
		OutputStream webout;
		Socket webClient;  
		
		public Handler(Socket webClient) { 
		     this.webClient = webClient;
		}  
		//web发送给下位机和下位机发送给web都在次函数进行；注意这是由同步阻塞实现
		public void run() {
		      try {
		    	  //System.out.println("WebServerSimple connect one!");
		    	  logger.info("WebServerSimple connect one!");
		    	  
		    	  webin = webClient.getInputStream(); 
		    	  webout = webClient.getOutputStream();
		    	  
		    	  InputStreamReader inReader = new InputStreamReader(webin);
		    	  BufferedReader bufferedReader = new BufferedReader(inReader);
		    	  
		    	  OutputStreamWriter outWriter = new OutputStreamWriter(webout);
		    	  BufferedWriter bufferedWriter = new BufferedWriter(outWriter);
		    	  
		    	  
		    	  String buffer = null;
		    	  while((buffer =bufferedReader.readLine())!=null){
		    		  //System.out.println("WebServerSimple readbuffer:"+buffer);
		    		  String[] splitStrings=buffer.split(";");//MN;CN;Para
		    		  HJT212LocalProxy proxy = proxyManager.getProxy(splitStrings[0]);
		    		  String writeString =null;
		    		  if(proxy==null || !proxy.isRunning)
		    		  {
		    			  writeString = splitStrings[0]+";1;null\n";
		    			  //System.out.println("webServerSimple rtn  one errer sysRtn !");
		    			  bufferedWriter.write(writeString);
		    			  bufferedWriter.flush();
		    			  continue;
		    		  }
		    		  
		    		  String[] reqPara =null;
		    		  if(splitStrings.length==3){
		    			  reqPara = new String[] {splitStrings[2]};
		    		  }
		    		  else if (splitStrings.length==4) {
		    			  reqPara = new String[] {splitStrings[2],splitStrings[3]};
		    		  }
		    		  CustomRequest req = new CustomRequest(splitStrings[1],reqPara);//转换成服务识别的命令
		    		  proxy.addReq(req);//将命令添加到代理中
		    		  boolean flag =false;
		    		  while(proxy.isRunning)
		    		  {
		    			  
		    			  try {
							//CustomResponse response = proxy.responseList.poll(10, TimeUnit.SECONDS);
		    				  CustomResponse response = proxy.responseList.take();//阻塞等待下位机响应，给web端提供反控
						if(response !=null)//取到数据返回给web端
							{
								//System.out.println("webServerSimple read  one sysRtn !");
								writeString=response.mn+";"+response.retType+";"+response.respString+"\n";
								flag = true;
								break;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		  }
		    		  if(!flag)
		    			  writeString = splitStrings[0]+";1;null\n";
		    		  //System.out.println("WebServerSimple writeString:"+writeString);
		    		  bufferedWriter.write(writeString);
		    		  bufferedWriter.flush();
		    	  }
		    	  
		            //关闭相对应的资源
		            bufferedWriter.close();
		            outWriter.close();
		            webout.close();
		            bufferedReader.close();
		            inReader.close();
		            webin.close();
		            webClient.close();
		            //System.out.println("WebServerSimple.Handler.run()------------close one WEBsocket!111111111111");
		            logger.warn("WebServerSimple.Handler.run()------------close one WEBsocket!111111111111");
			} catch (IOException e) {
				// TODO: handle exception
				 try {
					webClient.close();
					//System.out.println("WebServerSimple.Handler.run()------------close one WEBsocket!2222222222222");
					logger.warn("WebServerSimple.Handler.run()------------close one WEBsocket!2222222222222");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}  
	 } 
}
