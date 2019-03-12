package simpleserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import app.center.custom.CustomResponse;
import app.center.custom.HJT212LocalProxy;
import app.center.custom.HJT212QueueLocalProxy;
import app.center.custom.LocalProxyManager;
import app.util.HJT212Communicate;
/*
 * 无反控的下位机服务
 */
public class SimpleCenter {
	public static final int PORT = 60001;//����Ķ˿ں� 
	public LocalProxyManager proxyManager;
	private final Executor exec = Executors.newFixedThreadPool(20);
	//public LinkedBlockingQueue<CustomResponse> respList = new LinkedBlockingQueue<CustomResponse>();
	
	public LinkedBlockingQueue<CustomResponse> rrrr;
    public SimpleCenter() {
		super();
		proxyManager = new LocalProxyManager();
	}

	public static void main(String[] args) {  
        System.out.println("server start...\n");  
        SimpleCenter server = new SimpleCenter();  
        server.init();  
    }  
	
  
    public void init() { 
    	ServerSocket serverSocket;
        try {  
            serverSocket = new ServerSocket(PORT);  
            while (true) {  
                //监听接受连接
                Socket client = serverSocket.accept();  
                // 处理连接
                exec.execute(new HandlerThread(client));  
            }  
        } catch (Exception e) {  
            System.out.println("server error " + e.getMessage());  
        } 
    }  

	  protected class HandlerThread implements Runnable {  
		  protected HJT212LocalProxy localProxy;
		  
		  public HandlerThread(Socket client) { 
		      HJT212Communicate cBase = new HJT212Communicate(client);//创建socket基类
		      //localProxy = new HJT212LocalProxy(cBase, proxyManager);
		      localProxy = new HJT212QueueLocalProxy(cBase, proxyManager);//创建代理
		  }  
		
		  public void run() {
		      localProxy.start();
		  }  
	  } 
}
