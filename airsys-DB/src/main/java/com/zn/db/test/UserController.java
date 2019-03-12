package com.zn.db.test;



import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;








import com.zn.db.dao.RealDataDao;
import com.zn.db.entity.RealData;


public class UserController {
    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx=null;
        ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        RealDataDao realDataDao=(RealDataDao) ctx.getBean("realDataDao");//获取bean实例
        //RealDataDao realDataDao2=(RealDataDao) ctx.getBean("realDataDao_2");

        RealData realData=new RealData();
        //添加记录
        while(true)
        {
        realData.setSite_name("kongqizhan");
        realData.setSite_num("123564");
        realData.setAQIindex(3);
        realData.setAQIlevel(8);
        realData.setCO(9);
//date
        Date date=new Date();
        realData.setTime(date);
        realData.setMoisture(8.36);
        realData.setLatitude(8.69);
        realData.setLongtitude(8);
        realData.setMN("201808160951");
        realData.setNO2(9);
        realData.setO3(8);
        realData.setPM10(3.36);
        realData.setPM25(9);
        realData.setAtm_pressure(1.9398);
        realData.setSO2(9);
        realData.setTemperature(8);
        realData.setTVOC(9);
        realData.setWind_speed(6.3989);
        realData.setWind_direction(5);
        realData.setWind_direction_flag("23");;

        realData.setCO_flag("1");
        realData.setMoistrue_flag("0.36");
 
        realData.setNO2_flag("3");
        realData.setO3_flag("4");
        realData.setPM10_flag("2");
        realData.setPM25_flag("5");
        realData.setAtm_pressure_flag("5");
        realData.setSO2_flag("55");
        realData.setTemperature_flag("6");
        realData.setTVOC_flag("5");
        realData.setWind_speed_flag("3");
        
        //插入数据
        realDataDao.addRealData(realData);
        System.out.println("realData insert done!!!");
        //realDataDao2.addRealData(realData);
        //System.out.println("realData  2   insert done!!!");

        
        realData.setMN("1234567891");
        
        //查询数据
        List <RealData> selectList=new ArrayList<RealData>();
        selectList=realDataDao.getRealData(realData);
        System.out.println("查到"+selectList.size()+"条数据：");
        for(int i=0;i<selectList.size();i++)    
        System.out.println(selectList.get(i).toString());
        Thread.sleep(60000);
        }

        
    }

}