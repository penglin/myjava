package junit;

import junit.framework.Assert;
import junit.framework.TestCase;

/** 
 * @author �¾��� E-mail:jingbo2759@163.com
 * @version ����ʱ�䣺Aug 10, 2009 10:13:10 AM 
 * ��˵�� 
 */

public class CalculateTest extends TestCase {

	Calculate cal;

	public void setUp() {
		cal = new Calculate();
		System.out.println("here");//�����������ִ������..�����δ����Ķ������µĶ���..
	}

	public void tearDown() {
		System.out.println("gose");
	}

	public void testAdd() {
		int result = cal.add(10, 20);

		/**
		 * �Ա�,����30Ϊ��������ֵ,result�Ǽ�����ֵ
		 */
		Assert.assertEquals(30, result);
	}

	public void testDivide() {
		int result = 0;
		try {
			result = cal.divide(10, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertEquals(5, result);

	}

	public void testDivide2() {
		Throwable a = null;

		try {
			cal.divide(10, 0);
			Assert.fail();
		} catch (Exception e) {
			a = e;
		}

		System.out.println(a.getMessage());
		Assert.assertNotNull(a);
		Assert.assertEquals(Exception.class, a.getClass());
		Assert.assertEquals("��������Ϊ0", a.getMessage());
	}

}