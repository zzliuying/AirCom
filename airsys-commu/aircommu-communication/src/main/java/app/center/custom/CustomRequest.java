package app.center.custom;

/*
 * web端的请求命令，包括命令名与所需的参数列表
 */
public class CustomRequest {
	public String action;
	public String[] paras;
	public CustomRequest(String action, String[] paras) {
		super();
		this.action = action;
		this.paras = paras;
	}
	
	
}
