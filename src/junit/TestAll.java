package junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** 
 * @author 陈静波 E-mail:jingbo2759@163.com
 * @version 创建时间：Aug 10, 2009 3:58:07 PM 
 * 类说明 
 */

public class TestAll extends TestCase {

	public static Test suite()//这里的方法名必须是这个...
	{
		TestSuite suite = new TestSuite();

		suite.addTestSuite(CalculateTest.class);//这里添加的是测试类的class..可以添加任意多个测试类...最后会一起运行

		return suite;
	}

}