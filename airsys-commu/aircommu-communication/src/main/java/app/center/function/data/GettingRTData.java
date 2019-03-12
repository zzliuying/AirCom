package app.center.function.data;

import java.io.UnsupportedEncodingException;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.interact.center.GettingProcessorCenter;
import core.interact.center.LoadingProcessorCenter;
import core.structure.CommandPara;
import core.structure.DataPackage;
import core.structure.item.SimpleItem;

/*
 * 请求查看实时数据命令processor实现类
 * 虽然该命令属于获取类命令，但该命令的数据响应由上传数据命令代替了
 * 因此，在处理上会有所不同，需要重载进行个别处理
 */
public class GettingRTData  extends GettingProcessorCenter{

	//LoadingProcessorCenter loadingProcessor;
	public boolean loadRtnFlag;
	
	public	GettingRTData(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory){
		super(cBase, proxy, reqFactory);
		setCurCN("2011");
		//this.loadingProcessor=loadingProcessor;
		loadRtnFlag=false;
	}
	
	
	/*
	 * 处理接收命令
	 * 按照命令执行的阶段划分状态，根据状态进行处理
	 */
	@Override
	public void handle(DataPackage resp) {
		// TODO Auto-generated method stub
		boolean ret;					
		switch (curState) {
		case REPLY:
			//DO REPLY
			ret = handleReply(resp);
			if (ret) 
			{
				proxy.loadingRTData(this.loadRtnFlag);//接收成功后，创建上传命令processor，开始接收上传数据
				timer.cancel();
				endHandleToResp(0);//retType int类型    值含义：0-正常返回  
			}							
			break;
		default:
			break;
		}
	}
	
	
	/*
	 * 请求应答具体处理
	 * 此处理函数要做两件事情：
	 * （1）根据现场端的应答信息的特定返回值，确定现场端是否正确接收请求
	 * （2）根据返回结果中是否发送确认信息标识，确定是否给下位机发送确认信息
	 */
	@Override
	protected boolean handleReply(DataPackage pck) {
		// TODO Auto-generated method stub
		System.out.println("GettingRTData handlereply!!!!");
		boolean ret=false;		
			if (pck.dataSeg.CN.compareTo(replyCN) == 0)
			{				
				//System.out.println(pck.build());				
				CommandPara cp=(CommandPara)pck.dataSeg.CP;
				int index = cp.dataZone.itemList.size();
				SimpleItem qnRtn =(SimpleItem) cp.dataZone.itemList.get(index-1);//判断特定返回值
				
				if(qnRtn.value.compareTo("1")==0)
				{
					ret=true;
					if(pck.dataSeg.Flag.compareTo("1") == 0) //判断是否发送确认信息标识
						loadRtnFlag = true;
				}
				else {
					/////////detach();/////后面应添加超时重发，重发失败后才能detach()
					timer.cancel();
					endHandleToResp(2);
				}
			}						
		return ret;
	}
			

}

