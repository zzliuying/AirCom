package app.center.function.parameter;

import core.interact.center.SettingProcessorCenter;
import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.RequestFactory;

/*
 * 设置现场端系统时间processor实现类
 * 只需设置具体命令，其他操作与父类获取类命令处理一致，直接继承处理
 */
public class SettingSystemTime extends SettingProcessorCenter{
	

	public SettingSystemTime(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory) {
		// TODO Auto-generated constructor stub
		super(cBase, proxy, reqFactory);
		setCurCN("1012");
		
	}
	
	
	
	
		
}
