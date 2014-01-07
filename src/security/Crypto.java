/**
 * ����������
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
 * ����������
 */
public class Crypto
{
	/**
	 * ժҪMD5�㷨���峣��
	 */
	public final static String ALGORITHM_MD5_DIGEST = "MD5";

	/**
	 * ժҪSHA-1�㷨���峣��
	 */
	public final static String ALGORITHM_SHA_DIGEST = "SHA-1";

	/**
	 * ϵͳ���ܽ��ܲ�����ʼ��
	 */
	static
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * ����ָ��ժҪ�㷨������Դ���ݵ�ժҪ��Ϣ
	 * 
	 * @param origin Դ����
	 * @param ALGORITHM_DIGEST ժҪ�㷨
	 * @return ������ժҪ��Ϣ
	 * @throws Exception �����쳣
	 */
	public static byte[] digest(byte[] origin, String ALGORITHM_DIGEST) throws Exception
	{
		// ����ժҪ��Ϣ
		MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_DIGEST);
		messageDigest.update(origin);
		return messageDigest.digest();
	}

	/**
	 * ����DESede��Կ
	 * 
	 * @return DESede��Կ
	 * @throws Exception �����쳣
	 */
	public static byte[] generateDESedeKey() throws Exception
	{
		KeyGenerator generator = KeyGenerator.getInstance("DESede");
		SecretKey secretKey = generator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * ������Կ����ָ������
	 * 
	 * @param keybyte byte[] ��Կ
	 * @param plainData byte[] Դ����
	 * @throws Exception �����쳣
	 * @return ���ܺ��������Ϣ
	 */
	public static byte[] encryptDESede(byte[] keybyte, byte[] plainData) throws Exception
	{
		// ������Կ
		SecretKey secretKey = new SecretKeySpec(keybyte, "DESede");
		// DESede����
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(plainData);
	}

	/**
	 * ������Կ�����ַ���
	 * 
	 * @param keybyte byte[] ��Կ
	 * @param cipherData byte[] ����
	 * @throws Exception �����쳣
	 * @return byte[] Դ����
	 */
	public static byte[] decryptDESede(byte[] keybyte, byte[] cipherData) throws Exception
	{
		// ������Կ
		SecretKey secretKey = new SecretKeySpec(keybyte, "DESede");
		// �����õ���������
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(cipherData);
	}
	
	/**
	 * MD5 ����
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