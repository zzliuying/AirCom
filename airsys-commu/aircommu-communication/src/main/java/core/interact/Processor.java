package core.interact;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import core.structure.DataPackage;

/*
 * processor基类
 * 实现了观察者接口
 * 每个代理（被观察者）都有多个processor（观察者）来处理接收数据，一旦接收到数据就通知processor处理
 * processor是具体命令的实现类
 * processor主要做两件事：
 * （1）发送命令：发送命令请求，将自己注册到代理中
 * （2）处理返回结果：处理具体命令的具体处理函数
 */
public class Processor implements Observer {

	protected Timer timer;
	protected HJT212Communicate cBase;
	protected HJT212LocalProxy proxy;
	//protected String curCN="";
	public Processor(HJT212Communicate cBase,HJT212LocalProxy proxy)
	{
		this.cBase = cBase;
		this.proxy = proxy;
	}
	
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		handle((DataPackage)arg);
	}
/*
 * 发送命令
 * 发送命令请求，将自己注册到代理中
 */
	public void execute(String para)
	{
		
	}
	/*
	 * 处理返回结果
	 */
	public void handle(DataPackage resp) {
		
		
	}
	
//	public void setCurCN(String cn)
//	{
//		curCN = cn;
//	}
}
