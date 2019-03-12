package app.center.function.parameter;

import app.center.custom.HJT212LocalProxy;
import app.util.HJT212Communicate;
import app.util.LocalInfo;
import app.util.RequestFactory;
import core.interact.center.SettingProcessorCenter;

/*
 * 设置现场端系统密码processor实现类
 * 只需设置具体命令，其他操作与父类获取类命令处理一致，直接继承处理
 * 
 */
public class SettingPassWord extends SettingProcessorCenter {

	public String pwString;
	public LocalInfo localInfo;
	public SettingPassWord(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory,LocalInfo localInfo) {
		// TODO Auto-generated constructor stub
		super(cBase, proxy, reqFactory);
		this.localInfo = localInfo;
		setCurCN("1072");	
	}

	/*
	 * 保存临时密码
	 */
	public void setPW(String pw)
	{
		pwString = pw;
	}
	
	/*
	 * 设置密码后，重置注册信息中的密码
	 */
	public void setLocalInfoPW()
	{
		this.localInfo.PW = pwString;
	}
	

	/*
	 * 处理结束，将命令最终执行结果及响应添加到web端反馈list中
	 * retType int类型    值含义：0-正常返回   1-站点连接出错  2-执行命令出错   3-执行超时
	 * 命令成功后需要修改注册密码
	 */
	@Override
	public void endHandleToResp(int retType) {
		// TODO Auto-generated method stub
		super.endHandleToResp(retType);
		setLocalInfoPW();
	}
}
