package core.interact.center;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import app.center.custom.CustomResponse;
import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.interact.Processor;

import core.structure.CommandPara;
import core.structure.DataPackage;
import core.structure.item.SimpleItem;
/*
 * processor的实现类
 * 实现了一些processor的公共处理函数，包括：发送命令函数、处理返回结果函数模板、超时后的处理函数等
 * 
 */
public class ProcessorCenter extends Processor {

	protected enum STATE{REPLY,RESPONSE,RESULT};
	final protected String replyCN="9011";
	final protected String exeRtnCN="9012";
	final protected String notifyCN="9013";
	final protected String dataCN="9014";
	protected String curCN="";
	protected STATE curState;
	public RequestFactory reqFactory;
	
	public CustomResponse cResponse = new CustomResponse();

	public boolean timeoutFlag;//超时标志	
	public int repeatcount=0;//超时重发次数
	
	public ProcessorCenter(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory)
	{
		super(cBase, proxy);
		this.reqFactory = reqFactory;
	}

	public void setCurCN(String cn)
	{
		curCN = cn;
	}
	/*
	 * 发送命令
	 * 发送命令请求，将自己注册到代理中
	 */
	@Override
	public void execute(String para) {
		// TODO Auto-generated method stub
		if(!cBase.sock.isClosed())
		{
			sendData(para);
			setCurState(STATE.REPLY);
			attach();//注册
		}

	}
	/*
	 * (non-Javadoc)
	 * @see core.interact.Processor#handle(core.structure.DataPackage)
	 * 处理接收命令的处理模板
	 * 按照命令执行的阶段划分状态，根据不同状态进行不同处理
	 */
	@Override
	public void handle(DataPackage resp) {
		boolean ret;
		// TODO Auto-generated method stub						
		switch (curState) {
		case REPLY:
			//DO REPLY
			//请求应答状态，则接收到后处理应答
			ret = handleReply(resp);
			if (ret) 
				setCurState(STATE.RESPONSE);
			break;
			
		case RESPONSE:
			//数据响应状态，则处理具体数据
			ret=handleResponseData(resp);
			if (ret) 
				setCurState(STATE.RESULT);
	
			break;
			
		case RESULT:
			//执行结果状态，处理命令执行结果
			ret = handleExeResult(resp);
			if(ret)
			{
				timer.cancel();
				endHandleToResp(0);//retType int类型    值含义：0-正常返回   1-出错
			}
			
			break;

		default:
			break;
		}
	}
	
	/*
	 * 处理结束，将命令最终执行结果及响应添加到web端反馈list中
	 * retType int类型    值含义：0-正常返回   1-站点连接出错  2-执行命令出错   3-执行超时
	 */
	public void endHandleToResp(int retType)
	{
		detach();
		cResponse.retType =retType;	//处理结果类型，将每种处理结果情况进行了分类			
		addResponse(cResponse);
		
	}
	/*
	 * 设置当前命令执行状态
	 */
	public void setCurState(STATE state) {
		curState = state;		
	}
	
	/*
	 * 发送命令请求，设置定时器，便于进行超时处理
	 */
	public void sendData(String para)
	{
		timer=new Timer();
		timer.schedule(new TimeoutTimerTask(timer), 30*1000);
	}
	
	/*
	 * 将自己作为观察者注册到代理中
	 */
	public void attach()
	{
		proxy.addObserver(this);
		//System.out.println("ProcessorCenter.attach().......................");
	}
	
	/*
	 * 从代理中去除观察者
	 */
	public void detach()
	{		
		proxy.deleteObserver(this);
		//System.out.println("ProcessorCenter.detach()......................");
	}
	
	/*
	 * 向web端添加反馈响应
	 */
	public void addResponse(CustomResponse resp) {
		proxy.addResp(resp);
	}
	
	/*
	 * 请求应答具体处理
	 * 根据现场端的应答信息的特定返回值，确定现场端是否正确接收请求
	 */
	protected boolean handleReply(DataPackage pck) {
		System.out.println("handlereply!!!!");
		boolean ret=false;		
			if (pck.dataSeg.CN.compareTo(replyCN) == 0)
			{				
				//System.out.println(pck.build());				
				CommandPara cp=(CommandPara)pck.dataSeg.CP;
				int index = cp.dataZone.itemList.size();
				SimpleItem qnRtn =(SimpleItem) cp.dataZone.itemList.get(index-1);//取出特定位置返回值
				
				if(qnRtn.value.compareTo("1")==0)
				{
					ret=true;
				}
				else {
					/////////detach();/////后面应添加超时重发，重发失败后才能detach()
					timer.cancel();
					endHandleToResp(2);
				}
			}						
		return ret;
	}
	
	/*
	 * 数据响应具体处理
	 * 每条命令的数据响应具体处理都不一样，因此在继承此类的子类中具体实现
	 */
	protected boolean handleResponseData(DataPackage pck) {
		System.out.println("handleResponseData!!!!");
		boolean ret=false;
		ret = true;
		return ret;
	}
	
	/*
	 * 执行结果具体处理
	 * 根据现场端的执行结果信息的特定返回值，确定现场端最终是否正确执行命令
	 */
	protected boolean handleExeResult(DataPackage pck) {
		//System.out.println("processorcenter handleExeResult!!!!");
		boolean ret=false;		
			if (pck.dataSeg.CN.compareTo(exeRtnCN) == 0)
			{	System.out.println("handleExeResult!!!!");			
				//System.out.println(pck.build());
				
				CommandPara cp=(CommandPara)pck.dataSeg.CP;
				int index = cp.dataZone.itemList.size();
				SimpleItem exeRtn =(SimpleItem) cp.dataZone.itemList.get(index-1);//取出特定位置返回值
				
				if(exeRtn.value.compareTo("1")==0)
				{
					ret=true;
				}
				else {
					timer.cancel();
					endHandleToResp(2);
				}

			}						
		return ret;
	}
	
	/*
	 * 超时后，返回执行结果超时
	 */
	public class TimeoutTimerTask extends TimerTask{
		public Timer timer;
		public TimeoutTimerTask(Timer timer) {
			// TODO Auto-generated constructor stub
			this.timer = timer;
		}
		public void run(){
			//timeoutFlag = true;
			timer.cancel();
			endHandleToResp(3);
		}
	}

}
