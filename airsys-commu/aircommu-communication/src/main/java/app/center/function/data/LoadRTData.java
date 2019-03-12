package app.center.function.data;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zn.db.dao.RealDataDao;
import com.zn.db.entity.RealData;

import app.center.custom.HJT212LocalProxy;
import app.com.DataToFile;
import app.util.HJT212Communicate;
import app.util.RequestFactory;
import core.interact.center.LoadingProcessorCenter;
import core.structure.CommandPara;
import core.structure.DataPackage;
import core.structure.DataSegment;
import core.structure.DataZone;
import core.structure.item.Item;
import core.structure.item.SimpleItem;

/*
 * 上传实时数据命令的processor实现类
 * 该命令属于上传类命令，其他操作与父类基本一致，直接继承
 */
public class LoadRTData extends LoadingProcessorCenter {

	public HashMap<String, String> dataMap;
	public String qn;
	public boolean loadRtnFlag;
	public LoadRTData(HJT212Communicate cBase,HJT212LocalProxy proxy,RequestFactory reqFactory,boolean loadRtnFlag)
	{
		super(cBase, proxy,reqFactory);
		setCurCN("2011");
		this.loadRtnFlag = loadRtnFlag;
		dataMap = new HashMap<String, String>();
	}
	
	/*
	 * 数据处理
	 * 具体数据处理逻辑分为如下步骤：
	 * （1）将实时数据各个参数值解析到mapper中
	 * （2）将mapper中的数据转到实体类中
	 * （3）调用数据库插入接口将实体类数据存入数据库中
	 */
	@Override
	public boolean handleData(DataPackage resp) {
		// TODO Auto-generated method stub
		//入库，存数据库,返回入库结果
//			DataZone dZone= ((CommandPara)(pck.dataSeg.CP)).dataZone;
//			CommandPara cp = (CommandPara)(pck.dataSeg.CP);
			parseRTData(resp);
			boolean sqlRet=saveToMySQL();
			if(sqlRet&&loadRtnFlag)
				sendData(null);//发送返回帧
		return true;
	}
	
	/*
	 * 发送数据
	 * 发送上传数据接收后的响应信息
	 */
	@Override
	public void sendData(String para) {
		// TODO Auto-generated method stub
		DataPackage req = reqFactory.buildDataRet(dataCN, this.qn, curCN);
		try {
			cBase.sendPck(req);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 数据解析
	 * 将212数据包中的数据域解析出来，以键值对的形式存入mapper中
	 */
	public void parseRTData(DataPackage resp)
	{	
		//System.out.println("parseRTData-----------------");
		DataSegment dataSegment = resp.dataSeg;			
		DataZone dataZone =((CommandPara)(dataSegment.CP)).dataZone;
		
		this.qn=dataSegment.QN;
		
		//生成一个 map<key,value> mapper
		//将类对象的属性一一赋值，赋值形式为 so2-rtd = mapper["so2-rtd"]
		dataMap.clear();
		dataMap.put("MN", dataSegment.MN);	
		
		List<Item> dataList = dataZone.itemList;			
		for(int i=0; i< dataList.size(); i++)
		{
			SimpleItem sItem =(SimpleItem)dataList.get(i);
			dataMap.put(sItem.key, sItem.value);//将键值对存储到mapper中
		}						
		
		//保存到TXT中
		DataToFile dtToFile = new DataToFile();
		dtToFile.saveDataTxt(dataSegment.MN,dataZone.build());			
	}
	
	
	/*
	 * 存储到数据库中
	 * 将212协议数据包解析，转储到实体类中，通过实体类的数据库插入操作，完成数据的入库
	 */
	public boolean  saveToMySQL() {
		// TODO Auto-generated method stub
		boolean ret= false;
		
		try {
			RealData realData = new RealData();	
			
			String tempstr;
			
			//将数据填充到实体类中
			
			SimpleDateFormat famt = new SimpleDateFormat("yyyyMMddHHmmss");
			ParsePosition pos = new ParsePosition(0);
			Date strToDatetime = famt.parse(dataMap.get("DataTime"), pos);
			realData.setTime(strToDatetime);
			

	        realData.setMN(dataMap.get("MN"));        
//	        realData.setLatitude(8.69);
//	        realData.setLongtitude(8); 
	        	        
			realData.setAQIindex(3);
	        realData.setAQIlevel(8);
	        
	        tempstr = dataMap.get("a01002-Rtd");
	        if(tempstr != null)
	        	realData.setMoisture(Double.parseDouble(tempstr));
	        realData.setMoistrue_flag(dataMap.get("a01002-Flag")); 
	        
	        
	        tempstr = dataMap.get("a01006-Rtd");
	        if(tempstr != null)
	        	realData.setWind_direction(Double.parseDouble(tempstr));
	        realData.setWind_direction_flag(dataMap.get("a01006-Flag"));
	        
	        tempstr = dataMap.get("a01007-Rtd");
	        if(tempstr != null)
	        	realData.setWind_speed(Double.parseDouble(tempstr));
	        realData.setWind_speed_flag(dataMap.get("a01007-Flag"));       
	        
	        
	        
	        tempstr = dataMap.get("a01006-Rtd");
	        if(tempstr != null)
	        	realData.setAtm_pressure(Double.parseDouble(tempstr));
	        realData.setAtm_pressure_flag(dataMap.get("a01006-Flag"));       
	        
	        tempstr = dataMap.get("a01001-Rtd");
	        if(tempstr != null)
	        	realData.setTemperature(Double.parseDouble(tempstr));
	        realData.setTemperature_flag(dataMap.get("a01001-Flag"));
	       
	        tempstr = dataMap.get("a21005-Rtd");
	        if(tempstr != null)
	        	realData.setCO(Double.parseDouble(tempstr));
	        realData.setCO_flag(dataMap.get("a21005-Flag"));
	        
	        tempstr = dataMap.get("a21004-Rtd");
	        if(tempstr != null)
	        	realData.setNO2(Double.parseDouble(tempstr));  
	        realData.setNO2_flag(dataMap.get("a21004-Flag"));
	        
	        tempstr = dataMap.get("a05024-Rtd");
	        if(tempstr != null)
	        	realData.setO3(Double.parseDouble(tempstr));
	        realData.setO3_flag(dataMap.get("a05024-Flag"));
	        
	        tempstr = dataMap.get("a21026-Rtd");
	        if(tempstr != null)
	        	realData.setSO2(Double.parseDouble(tempstr));
	        realData.setSO2_flag(dataMap.get("a21026-Flag")); 
	        
	        tempstr = dataMap.get("a34002-Rtd");
	        if(tempstr != null)
	        	realData.setPM10(Double.parseDouble(tempstr));
	        realData.setPM10_flag(dataMap.get("a34002-Flag"));             
	        
	        tempstr = dataMap.get("a34004-Rtd");
	        if(tempstr != null)
	        	realData.setPM25(Double.parseDouble(tempstr));
	        realData.setPM25_flag(dataMap.get("a34004-Flag"));                             
	        
	        tempstr = dataMap.get("a99054-Rtd");
	        if(tempstr != null)
	        	realData.setTVOC(Double.parseDouble(tempstr));
	        realData.setTVOC_flag(dataMap.get("a99054-Flag"));
	        
	        
	        //调用数据库接口，将实体类存储到到数据库中	        
	        ApplicationContext ctx=null;
	        ctx=new ClassPathXmlApplicationContext("applicationContext.xml");	
	        RealDataDao realDataDao=(RealDataDao) ctx.getBean("realDataDao");	
	        realDataDao.addRealData(realData);//调用插入操作接口
	        System.out.println("realData insert done!!!");
//多数据源配置
	        //可以向多个数据库中存数据，对数据进行备份
//	        RealDataDao realDataDao2 = (RealDataDao)ctx.getBean("realDataDao_2");
//	        realDataDao2.addRealData(realData);
//	        System.out.println("realData 2 insert done!!!");

	        ret = true;
			return ret;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	

}
