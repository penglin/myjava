package security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TestCipher {
	public static void main(String[] args) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		String plain = "dfdafdasfdsf";
		try {
			Cipher cipher = Cipher.getInstance("DES");
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			SecretKey deskey=new SecretKeySpec("00000000".getBytes(),"DES");     
			SecretKey secretKey = generator.generateKey();
			System.out.println(secretKey.getAlgorithm());
			System.out.println(secretKey.getEncoded());
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			System.out.println(cipher.getBlockSize());
			byte[] ciphers = cipher.doFinal(plain.getBytes());
			System.out.println(new String(ciphers));
			Provider provider = cipher.getProvider();
			System.out.println(provider.getName());
			
			CipherOutputStream out = new CipherOutputStream(new FileOutputStream("cipher.txt"),cipher);
			out.write(plain.getBytes());
			out.flush();
			out.close();
			
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			
			System.out.println("--"+new String(cipher.doFinal(ciphers)));
			FileInputStream file = new FileInputStream("cipher.txt");
			CipherInputStream in = new CipherInputStream(file,cipher);
			byte[] bytes = new byte[1024];
			System.out.println(file.available());
			/*while(in.read(bytes)!=-1){
				
			}*/
			in.read(bytes);
			plain = new String(bytes);
			in.close();
			System.out.println(plain);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
