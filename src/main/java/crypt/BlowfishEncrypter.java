package  crypt;



import org.apache.logging.log4j.LogManager;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.logging.Logger;

/**
 * Encrypt and decrypt byte using blowfish algorithm
 */
public class BlowfishEncrypter  {
    /**
     * logger
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BlowfishEncrypter.class);


    /**
     * Password TODO use char[]
     */
    private String password;

    /**
     * key create from the password
     */
    private Key key;

    /**
     * Constructor
     * @param pass
     */
    public BlowfishEncrypter(final String pass) {
        password =pass;
        try {
            key = new SecretKeySpec(password.getBytes("UTF-8"), "Blowfish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * encrypt data
     * @param acrypt
     * @return
     */
    public byte[] encrypt(final byte[] acrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(acrypt);
        } catch (Exception e) {
            LOGGER.error("Error while encrypting data",e);
            return null;

        }
    }

    /**
     * Decrypt data
     * @param toDecrypt
     * @return
     */
    public byte[] decrypt(final byte[] toDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] temp = cipher.doFinal(toDecrypt);
            return temp;
        } catch (Exception e) {
            LOGGER.error("Error while decrypting data",e);
            return null;
        }
    }
}
