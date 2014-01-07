package db;

import java.util.List;

import cn.adsit.common.dao.ObjectDao;

public class TestServiceImpl implements TestService{
	private ObjectDao reportDao ;

	public ObjectDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(ObjectDao reportDao) {
		this.reportDao = reportDao;
	}
	
	public List<Object[]> query(String sql) throws Exception{
		List<Object[]> list = reportDao.findBySQL(sql);
		return list;
	}
	
	public void update(String sql) throws Exception{
		this.reportDao.executeBySQL(sql);
	}
}
