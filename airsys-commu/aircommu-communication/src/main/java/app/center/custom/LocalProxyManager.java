package app.center.custom;

import java.util.HashMap;
import java.util.Map;

/*
 * 代理对象的管理，每建立一个连接就创建一个代理来接管与现场机的通信，待连接中断或者关闭后将代理删除
 */
public class LocalProxyManager{
	private Map<String, HJT212LocalProxy> proxyMapper;

	public LocalProxyManager() {
		super();
		proxyMapper = new HashMap<String, HJT212LocalProxy>(); 
	}
	/*
	 * 在mapper中插入代理
	 */
	public void registProxy(String mn, HJT212LocalProxy proxy)
	{
		proxyMapper.put(mn, proxy);
	}
	
	/*
	 * 在mapper中删除代理
	 */
	public HJT212LocalProxy getProxy(String mn)
	{
		return proxyMapper.get(mn);
	}
	
}
