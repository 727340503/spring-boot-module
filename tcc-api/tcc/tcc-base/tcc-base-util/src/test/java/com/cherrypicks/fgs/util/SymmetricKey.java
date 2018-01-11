package com.cherrypicks.fgs.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class SymmetricKey {
  //  private static final byte[] iv = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static void main(final String[] args) throws Exception {

        // 16 digit custom key
       // final String symmetricKey = "767464537A496C6D6955535956716F6D";

       // System.out.println("key : " + symmetricKey);
       // final String iv = getRandomIv();

       // System.out.println(iv);

        // Encrypt Data
       // final String encryptedData = encryptWithAESKey("asd", symmetricKey,iv);

       // final String encryptedData = EncryptionCBC.encrypt("asd", symmetricKey, iv, "SunJCE");

        //System.out.println("Encrypted Data : " + encryptedData);

       // System.out.println("Decrypted Data : " + EncryptionCBC.decrypt(encryptedData, symmetricKey, iv, "SunJCE"));

        // Decrypt Data
      //  System.out.println("Decrypted Data : " + decryptWithAESKey(encryptedData, symmetricKey,iv));


       /* final String cherrypicksDataEncryptionKeyDecrypted = EncryptionCBC.decrypt(Hex.decodeHex(cherrypicksDataEncryptionKey.toCharArray()),
                "767464537A496C6D6955535956716F6D", "30303030303030303030303030303030", "SunJCE");*/

       /* final String cherrypicksDataEncryptionIvDecrypted = EncryptionCBC.decrypt("abc",
                "767464537A496C6D6955535956716F6D", "30303030303030303030303030303030");
        System.out.println(cherrypicksDataEncryptionIvDecrypted);*/
        final String t = new String(Hex.decodeHex("767464537A496C6D6955535956716F6D".toCharArray()));//
        System.out.println(t);
    }

    public static String encryptWithAESKey(final String data, final String key,final String iv) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, DecoderException {

        // Generate symmetric key
        final SecretKey secKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Encrypt data using the symmetric Key
        cipher.init(Cipher.ENCRYPT_MODE, secKey, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
        final byte[] newData = cipher.doFinal(data.getBytes());

        return Base64.encodeBase64String(newData);
    }

    public static String decryptWithAESKey(final String inputData, final String key,final String iv) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, DecoderException {

        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Generate symmetric key
        final SecretKey secKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        cipher.init(Cipher.DECRYPT_MODE, secKey, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
        final byte[] newData = cipher.doFinal(Base64.decodeBase64(inputData.getBytes()));
        return new String(newData);

    }

    public static String getRandomIv() {
        final byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        return Hex.encodeHexString(iv).toUpperCase(Locale.ENGLISH);
    }
}
