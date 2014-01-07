/**
 * 加密器对象
 */
package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 * 
 * 加密器对象
 */
public class Crypto
{
	/**
	 * 摘要MD5算法定义常量
	 */
	public final static String ALGORITHM_MD5_DIGEST = "MD5";

	/**
	 * 摘要SHA-1算法定义常量
	 */
	public final static String ALGORITHM_SHA_DIGEST = "SHA-1";

	/**
	 * 系统加密解密操作初始化
	 */
	static
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * 根据指定摘要算法，生成源数据的摘要信息
	 * 
	 * @param origin 源数据
	 * @param ALGORITHM_DIGEST 摘要算法
	 * @return 二进制摘要信息
	 * @throws Exception 操作异常
	 */
	public static byte[] digest(byte[] origin, String ALGORITHM_DIGEST) throws Exception
	{
		// 生成摘要信息
		MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_DIGEST);
		messageDigest.update(origin);
		return messageDigest.digest();
	}

	/**
	 * 生成DESede密钥
	 * 
	 * @return DESede密钥
	 * @throws Exception 操作异常
	 */
	public static byte[] generateDESedeKey() throws Exception
	{
		KeyGenerator generator = KeyGenerator.getInstance("DESede");
		SecretKey secretKey = generator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 根据密钥加密指定数据
	 * 
	 * @param keybyte byte[] 密钥
	 * @param plainData byte[] 源数据
	 * @throws Exception 操作异常
	 * @return 加密后二进制信息
	 */
	public static byte[] encryptDESede(byte[] keybyte, byte[] plainData) throws Exception
	{
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(keybyte, "DESede");
		// DESede加密
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(plainData);
	}

	/**
	 * 根据密钥解密字符串
	 * 
	 * @param keybyte byte[] 密钥
	 * @param cipherData byte[] 密文
	 * @throws Exception 操作异常
	 * @return byte[] 源数据
	 */
	public static byte[] decryptDESede(byte[] keybyte, byte[] cipherData) throws Exception
	{
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(keybyte, "DESede");
		// 解析得到加密密文
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(cipherData);
	}
	
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	public static void main(String[] args) {
		try {
			byte[] cipher = digest("test1312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr50".getBytes(),ALGORITHM_MD5_DIGEST);
			System.out.println(cipher.length);
			System.out.println(new String(cipher));
			
			String t = getMD5Str("test1312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr501312632729022atov768h1ixci6fcr50");
			System.out.println(t);
			System.out.println(t.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}