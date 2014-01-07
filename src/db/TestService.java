package db;

import java.util.List;

public interface TestService {
	public List<Object[]> query(String sql) throws Exception;
	
	public void update(String sql) throws Exception;
}
