package app.center.func.slave;

import app.util.LocalInfo;
import core.structure.DataPackage;

public class CenterRecvProcessor extends RecvProcessor {
	
	RegistHandler regHandler;
	LocalInfo localInfo;
	
	
	public CenterRecvProcessor(LocalInfo localInfo) {
		super();
		this.localInfo = localInfo;
		regHandler = new RegistHandler(localInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see app.center.func.slave.RecvProcessor#process(core.structure.DataPackage, core.structure.DataPackage)
	 * 处理注册信息
	 */
	@Override
	public void process(DataPackage in, DataPackage out) {
		if(localInfo.MN == null || localInfo.PW == null || localInfo.ST == null)
		{
			regHandler.handle(in, out);
		}
		else
		{
			
		}
	}
	
}
