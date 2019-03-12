package core.interact.center;

import java.io.UnsupportedEncodingException;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.structure.DataPackage;

/*
 * 设置现场机参数类的命令processor实现
 * 这类命令状态会有1个：数据响应，只要没有停止命令，会一直接收上传数据
 * 因此，需要重载handle函数
 * 
 */
public class LoadingProcessorCenter extends ProcessorCenter {

	public LoadingProcessorCenter(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory){
		super(cBase, proxy,reqFactory);
	}
	
	/*
	 * 发送命令
	 * 发送命令请求，将自己注册到代理中
	 */
	@Override
	public void execute(String para) {
		// TODO Auto-generated method stub
		setCurState(STATE.RESPONSE);
		attach();
	}	
	
	/*
	 * 处理接收命令的处理模板
	 * 按照命令执行的阶段划分状态，根据状态进行处理
	 */
	@Override
	public void handle(DataPackage resp) {						
		switch (curState) {			
		case RESPONSE:
			handleResponseData(resp);	
			break;
		default:
			break;
		}
	}
	
	
	/*
	 * 上传类型命令的数据响应处理函数
	 * 上传的数据需要进行入库处理，具体哪种类型的数据需要怎样的处理，都会因命令不同而不同
	 * 因此将具体处理放在handleData函数中
	 */
	@Override
	protected boolean handleResponseData(DataPackage pck) {
		// TODO Auto-generated method stub
		if (pck.dataSeg.CN.compareTo(curCN) == 0)
		{
			System.out.println("loading handleResponseData!!!!");				
			//对于2017版，此处发送接收应答send()。			
			return handleData(pck);			
		}
		else {
			return false;
		}
	}
	
	/*
	 * 上传数据处理函数
	 * 由于具体命令的处理方式不同，这里给出函数框架，具体命令类可重载该函数进行具体处理
	 */	
	public boolean handleData(DataPackage resp)
	{
		//入库，存数据库,返回入库结果
		try{
//			DataZone dZone= ((CommandPara)(pck.dataSeg.CP)).dataZone;
//			CommandPara cp = (CommandPara)(pck.dataSeg.CP);	
			System.out.println(resp.build());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}
						
	
}
