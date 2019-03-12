package app.center.custom;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
/*
 * 代理基类
 * 代理主要完成3件事：
 * （1）从待处理命令请求list中处理web端发来的请求命令，生成processor，向socket发送命令请求（下位机）
 * （2）（下位机返回数据）从socket读取返回数据，进行212协议数据帧的解析，并将解析好的数据放入缓存list中
 * （3）从缓存list中逐条取出数据包发送给各个processor进行处理
 * 
 * 一条命令一个processor，
 * 
 */
public class LocalProxy extends Observable {


	public boolean isRunning;
	
	// 仓库最大存储量
	private final int MAX_SIZE = 100;

	// 仓库存储的载体
	//协议帧缓存队列
	protected LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(MAX_SIZE);
	public LinkedBlockingQueue<CustomResponse> responseList = new LinkedBlockingQueue<CustomResponse>();
	
	protected void recvToQueue(){
		//从socket中读数据，存入缓存队列
		// receive data from socket to recvQueue		
	}
	protected void handleReq() {
		//handle reqList，处理web端的请求命令
	}
	protected void handleRecv() {
		//handle recvQueue，处理缓存队列中的返回数据
	}

	
	protected void addRepList(){
		// add 反控响应结果
		
	}

	public void start()
	{
		
		isRunning = true;
		new Thread(){
			public void run()
			{
				recvToQueue();//
			}
		}.start();
		
		new Thread(){
			public void run()
			{
				handleReq();
			}
		}.start();
		

		new Thread(){
			public void run()
			{
				handleRecv();
			}
		}.start();
		
			
	
	}
	
	public void stop()
	{
		isRunning = false;
	}
}
