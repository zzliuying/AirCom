package com.zn.db.dao;

import java.util.List;

import com.zn.db.entity.RealData;

/*
 * 实时数据DAO层接口
 */
public interface RealDataDao {
	public List<RealData> getRealData(RealData realData);
	public void addRealData(RealData realData);
	public void updateRealData(RealData realData);
	public void deleteRealData(int RealDataID);
}
