/**
 * 
 */
package org.gcube.common.homelibrary.util.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author gioia
 * 
 */
public class EncryptionUtil {

	static private SecretKey key;
	Cipher ecipher;
	Cipher dcipher;

	byte[] buf = new byte[1024];

	static {
		try {
			InputStream in = 
					EncryptionUtil.class.getResourceAsStream("SecretKey.ser");
			ObjectInputStream oin = new ObjectInputStream(in);
			key = (SecretKey)oin.readObject();
			
			oin.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EncryptionUtil() throws Exception {

		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07,
				0x72, 0x6F, 0x5A };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

	}

	public void encrypt(InputStream in, OutputStream out) throws Exception {
		out = new CipherOutputStream(out, ecipher);

		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}

	public void decrypt(InputStream in, OutputStream out) throws Exception {
		in = new CipherInputStream(in, dcipher);

		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}
}
