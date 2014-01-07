package junit;

import junit.framework.Assert;
import junit.framework.TestCase;

/** 
 * @author 陈静波 E-mail:jingbo2759@163.com
 * @version 创建时间：Aug 10, 2009 10:13:10 AM 
 * 类说明 
 */

public class CalculateTest extends TestCase {

	Calculate cal;

	public void setUp() {
		cal = new Calculate();
		System.out.println("here");//这个方法将被执行三次..而三次创建的对象都是新的对象..
	}

	public void tearDown() {
		System.out.println("gose");
	}

	public void testAdd() {
		int result = cal.add(10, 20);

		/**
		 * 对比,其中30为你期望的值,result是计算后的值
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
		Assert.assertEquals("除数不能为0", a.getMessage());
	}

}