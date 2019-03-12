package core.interact.center;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.structure.CommandPara;
import core.structure.DataPackage;
import core.structure.DataZone;

/*
 * 设置现场机参数类的命令processor实现
 * 这类命令状态会有2个：请求应答、执行结果
 * 因此，需要重载handle函数
 * 
 */
public class SettingProcessorCenter extends ProcessorCenter {

	public SettingProcessorCenter(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory)
	{
		super(cBase, proxy,reqFactory);
	}
	
	/*
	 *  命令发送函数
	 *  发送此类命令需要额外参数，需要先转换参数，再调用工厂类生成212协议数据包
	 */
	@Override
	public void sendData(String para) {
		// TODO Auto-generated method stub
		DataZone dZone = new DataZone(para);
		DataPackage req = reqFactory.buildRequest(curCN, new CommandPara(dZone));
		
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
	 * 按照命令执行的阶段划分状态，根据不同状态进行不同处理
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
				setCurState(STATE.RESULT);
	
			break;
			
		case RESULT:
			ret = handleExeResult(resp);
			if(ret)
			{
				timer.cancel();
				endHandleToResp(0);
			}
			
			break;

		default:
			break;
		}
	}

}
