package app.center.function.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.interact.center.GettingProcessorCenter;
import core.structure.DataPackage;

/*
 * 获取注册信息命令processor实现类
 * 注册命令需要设置定时器重复注册，知道注册成功为止，否则无法进行其他命令的执行
 * 对于命令的处理，为了防止断网重连后无法继续接收数据的情况，在命令确定成功执行后，添加接收上传数据的命令
 */
public class GettingIDInfo extends GettingProcessorCenter {
	boolean registFlag=false;
	Timer regisTimer;
	public GettingIDInfo(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory){
		super(cBase, proxy, reqFactory);
		setCurCN("3019");
	}
	/*
	 * (non-Javadoc)
	 * @see core.interact.center.ProcessorCenter#handle(core.structure.DataPackage)
	 * 命令处理函数
	 * 在命令确定成功执行后，添加接收上传数据的命令
	 * 所以，其他状态处理不变，最后添加上传数据命令
	 */
	@Override
	public void handle(DataPackage resp) {
		// TODO Auto-generated method stub
		if(registFlag)
		{
			boolean ret;
			// TODO Auto-generated method stub						
			switch (curState) {
			case REPLY:
				//DO REPLY
				ret = handleReply(resp);
				if (ret) 
					setCurState(STATE.RESPONSE);
				break;
				
			case RESPONSE:
				ret=handleResponseData(resp);
				if (ret) 
					setCurState(STATE.RESULT);
		
				break;
				
			case RESULT:
				ret = handleExeResult(resp);
				if(ret)
				{
					detach();
					proxy.loadingRTData(true);//创建上传数据命令
				}
				
				break;

			default:
				break;
			}
		}
		else {
			super.handle(resp);
		}

	}
	
	
	/*
	 * 执行注册命令
	 */
	public void executeRegist() {
		// TODO Auto-generated method stub	
		registFlag=true;
		regisTimer = new Timer();
		regisTimer.schedule(new RegistTimerTask(), 20*1000,30*1000);		
		sendRegistReq();
		setCurState(STATE.REPLY);
		attach();
	}
	/*
	 * 发送注册命令给现场机
	 */
	public void sendRegistReq()
	{
		DataPackage req = reqFactory.buildRegistReq();
		try {
			cBase.sendPck(req);
			
		}catch(SocketException eSocketException){
			//close socket
			regisTimer.cancel();
			try {
				cBase.sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			System.out.println("GettingIDInfo.sendRegistReq()........"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see core.interact.center.GettingProcessorCenter#handleResponseData(core.structure.DataPackage)
	 * 注册数据响应函数
	 * 与普通命令数据响应处理不同，该命令需要将现场机的基本信息注册到代理中
	 */
	@Override
	protected boolean handleResponseData(DataPackage pck) {
		// TODO Auto-generated method stub		
		boolean ret=false;	
		try {
			if (pck.dataSeg.CN.compareTo(curCN) == 0)
			{
				System.out.println("Getting MN!!!!");
				System.out.println(pck.build());	
				regisTimer.cancel();
				//直接使用之前的注册现场机的代码
				proxy.handleRegist(pck);								
				
				ret = true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}						
		return ret;
		
	}
	
	
	/*
	 * 定时器，定时触发注册命令
	 */
	public class RegistTimerTask extends TimerTask{
		public void run(){
			sendRegistReq();
			
		}
	}
}
