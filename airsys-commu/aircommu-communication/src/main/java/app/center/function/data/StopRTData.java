package app.center.function.data;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.interact.center.GettingProcessorCenter;
import core.interact.center.LoadingProcessorCenter;
import core.interact.center.NotifyProcessorCenter;
import core.structure.DataPackage;
/*
 * 停止上传实时数据命令processor实现类
 * 该命令属于通知类，但具体处理时与普通通知类不同，需要停止上传命令，涉及与其他命令的交互
 * 因此，需要重载处理函数
 */
public class StopRTData extends NotifyProcessorCenter{
	LoadingProcessorCenter loadingProcessor;
	GettingProcessorCenter gettingProcessor;
	
	public StopRTData(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory,LoadingProcessorCenter loadingProcessor) {
		// TODO Auto-generated constructor stub
		super(cBase, proxy, reqFactory);
		this.loadingProcessor = loadingProcessor;
		setCurCN("2012");
		
	}
	
	/*
	 * 处理接收命令
	 * 按照命令执行的阶段划分状态，根据状态进行处理
	 */
	@Override
	public void handle(DataPackage resp) {
		// TODO Auto-generated method stub
		switch (curState) {
		case REPLY:
			//DO REPLY
				
			boolean ret = handleReply(resp);
			if(ret)
			{
				stopGettingRTData();//成功应答后，停止上传实时数据
			}
			break;

		default:
			break;
		}
	}

	/*
	 * 停止上传实时数据
	 */
	public void stopGettingRTData() {
		// TODO Auto-generated method stub
		timer.cancel();
		endHandleToResp(0);
		if(loadingProcessor!=null)
		{
			loadingProcessor.detach();//将上传数据的processor从代理中删除
			loadingProcessor=null;
		}

	}
	
}
