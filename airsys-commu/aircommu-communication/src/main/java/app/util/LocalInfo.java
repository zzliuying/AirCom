package app.util;

/*
 * 用于服务器标识现场机的注册信息
 */
public class LocalInfo {
	public String MN;
	public String ST;
	public String PW;
	public LocalInfo(String mN, String sT, String pW) {
		super();
		MN = mN;
		ST = sT;
		PW = pW;
	}
}
