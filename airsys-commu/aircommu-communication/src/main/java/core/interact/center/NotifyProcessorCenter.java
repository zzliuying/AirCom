package core.interact.center;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import core.structure.CommandPara;
import core.structure.DataPackage;
import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;

/*
 * 设置现场机参数类的命令processor实现
 * 这类命令状态会有1个：请求应答
 * 因此，需要重载handle函数
 * 
 */
public class NotifyProcessorCenter extends ProcessorCenter {
	public NotifyProcessorCenter(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory) {
		// TODO Auto-generated constructor stub
		super(cBase, proxy, reqFactory);
	}
	
	/*
	 *  命令发送函数
	 *  发送此类命令不需要额外参数，直接调用工厂类生成212协议数据包
	 */
	@Override
	public void sendData(String para) {
		// TODO Auto-generated method stub
		DataPackage req = reqFactory.buildRequest(curCN, new CommandPara());
		
		try {
			cBase.sendPck(req);
			super.sendData(null);
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 处理接收命令的处理模板
	 * 按照命令执行的阶段划分状态，根据状态进行处理
	 */
	@Override
	public void handle(DataPackage resp) {
		// TODO Auto-generated method stub
		boolean ret;
		// TODO Auto-generated method stub						
		switch (curState) {
		case REPLY:
			//DO REPLY
			ret = handleReply(resp);
			if (ret) 
			{
				timer.cancel();
				endHandleToResp(0);//retType int类型    值含义：0-正常返回  
			}
			
			break;

		default:
			break;
		}
	}
	/*
	 * 通知类型命令请求应答具体处理
	 * 根据现场端的应答信息的特定返回值，确定现场端是否正确接收请求
	 */
	@Override
	protected boolean handleReply(DataPackage pck) {
		// TODO Auto-generated method stub
		boolean ret=false;		
			if (pck.dataSeg.CN.compareTo(notifyCN) == 0)			
			{
				System.out.println("notify handlereply!!!!");
				//System.out.println(pck.build());											
				ret = true;
			}						
		return ret;
	}
	

	
}
