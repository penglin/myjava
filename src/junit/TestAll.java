package junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** 
 * @author �¾��� E-mail:jingbo2759@163.com
 * @version ����ʱ�䣺Aug 10, 2009 3:58:07 PM 
 * ��˵�� 
 */

public class TestAll extends TestCase {

	public static Test suite()//����ķ��������������...
	{
		TestSuite suite = new TestSuite();

		suite.addTestSuite(CalculateTest.class);//�������ӵ��ǲ������class..��������������������...����һ������

		return suite;
	}

}