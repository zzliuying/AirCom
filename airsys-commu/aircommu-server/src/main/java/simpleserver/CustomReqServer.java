package simpleserver;

import java.net.ServerSocket;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.center.custom.CustomRequest;
import app.center.custom.CustomResponse;
import app.center.custom.HJT212LocalProxy;
import app.center.custom.HJT212QueueLocalProxy;
import app.util.HJT212Communicate;


/** 
 * 和下位机建立socket通信，启动代理，将控制权交给代理，
 * 一个socket连接由一个代理负责,代理主线程长期存在
 * 
 * **/

public class CustomReqServer extends SimpleCenter{
	
	
	public static void main(String[] args) {  
        
    }
		
	private final Executor exec = Executors.newFixedThreadPool(20);//与下位机建立连接的连接池
	
		
	public void init() { 		
		ServerSocket serverSocket;
        try {  
            serverSocket = new ServerSocket(60001);
            
            System.out.println("sysServer starting...\n");
            while (true) {  
                // accept the connection 监听接受连接
                Socket client = serverSocket.accept();
                client.setSoTimeout(120*1000);
                // handle this connection 处理连接
                exec.execute(new CustomReqHandlerThread(client));  
            }  
        } catch (Exception e) {  
            System.out.println("Server error:" + e.getMessage());  
        } 
    }  
	
	
	  protected class CustomReqHandlerThread extends HandlerThread { 
		  protected HJT212QueueLocalProxy localProxy;
		  
		  public CustomReqHandlerThread(Socket client) {
			  super(client);
		    HJT212Communicate cBase = new HJT212Communicate(client);//创建socket基类
			localProxy = new HJT212QueueLocalProxy(cBase, proxyManager);//创建代理						
		  }		  		  	  
		  
		  public void run() {
			  System.out.println("sysServerSimple connect one!");
		      localProxy.start();		      		      
			//  开始请求对方注册		      
			try {
				Thread.sleep(1*1000);
				CustomRequest req;
			    req = new CustomRequest("registReq", null);//注册
				localProxy.addReq(req);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  }  
	  } 
}
