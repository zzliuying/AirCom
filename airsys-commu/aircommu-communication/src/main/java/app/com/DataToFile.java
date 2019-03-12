package app.com;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataToFile {

	
	/*
	 * 创建要存储的文件的目录
	 */
	public String createFolder()
	{
		Date nowDate = new Date();
		String path = "F:/java212/data/"+new SimpleDateFormat("yyyy/MM/dd").format(nowDate);;
		
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		return path;
	}
	/*
	 * 将string类型的数据存储到文件中
	 */
	public void saveDataTxt(String mn,String dataString) {
		String txtpath =createFolder();		
		txtpath+="/"+mn+"_data.txt";
				
		File dataFile = new File(txtpath);
		if(!dataFile.exists())
		{
			try {
				dataFile.createNewFile();
				saveData(dataString, dataFile);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			saveData(dataString, dataFile);
		}
	}
	/*
	 * 将01号机子的string类型的数据存储到文件中，测试时使用
	 */
	public void saveDataTxt1(String dataString) {
		String txtpath =createFolder();		
		txtpath+="/01"+"_datastream.txt";
				
		File dataFile = new File(txtpath);
		if(!dataFile.exists())
		{
			try {
				dataFile.createNewFile();
				saveData(dataString, dataFile);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			saveData(dataString, dataFile);
		}
	}
	/*
	 * 将string类型的数据存储到指定的文件中
	 */
	public void saveData(String dataString,File dataFile)
	{
		try
		{	
			FileWriter	fw = new FileWriter(dataFile, true);			
			PrintWriter pw = new PrintWriter(fw);// 创建一个printWriter类的实例，其构造函函数是一个File对象
			pw.write(dataString);// 调用writer()方法写入数据	
			pw.write("\r\n");
			fw.close();
			pw.close();// 调用close()方法释放资源
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}
}
