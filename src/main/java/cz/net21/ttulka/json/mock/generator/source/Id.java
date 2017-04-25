package cz.net21.ttulka.json.mock.generator.source;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Source for IDs.
 * 
 * @author ttulka
 *
 */
public class Id implements Source<String> {

	private static final int LENGTH = 16;

	private final MessageDigest messageDigest;
	
	private int id;

	public Id() {
		super();
		this.messageDigest = createMessageDigest("MD5");
	}

	@Override
	public String getNext() {
		messageDigest.update(Integer.toString(id ++).getBytes());
		return convertToHex(messageDigest.digest()).substring(0, LENGTH);
	}
	
	private MessageDigest createMessageDigest(String alg) {
		try {
			return MessageDigest.getInstance(alg);
		} 
		catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private String convertToHex(byte[] byteData) {
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
