package app.center.func.slave;



import org.apache.log4j.Logger;

import app.util.LocalInfo;
import core.structure.DataPackage;

public class RegistHandler extends CommandHandler {
	private LocalInfo localInfo;
	public static Logger logger = Logger.getLogger(RegistHandler.class);
	
	public RegistHandler(LocalInfo localInfo) {
		super();
		if(localInfo != null)
		{
			this.localInfo = localInfo;			
		}

	}

	/*
	 * (non-Javadoc)
	 * @see app.center.func.slave.CommandHandler#handle(core.structure.DataPackage, core.structure.DataPackage)
	 * 处理注册信息
	 */
	@Override
	public void handle(DataPackage in, DataPackage out) {
		if(in.dataSeg != null && in.dataSeg.MN != null &&
				in.dataSeg.PW != null && in.dataSeg.ST != null)
		{
			localInfo.MN = in.dataSeg.MN;
			localInfo.PW = in.dataSeg.PW;
			localInfo.ST = in.dataSeg.ST;
			logger.info("local regist successful!");
			logger.info("MN:" + localInfo.MN);
			logger.info("PW:" + localInfo.PW);
			logger.info("ST:" + localInfo.ST);
//			System.out.println("local regist successful!");
//			System.out.println("MN:" + localInfo.MN);
//			System.out.println("PW:" + localInfo.PW);
//			System.out.println("ST:" + localInfo.ST);
			
		}
	}

}
