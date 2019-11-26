package com.yf.cloud.secret;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

	/**
	 * md5加解密
	 * @author lxw
	 *
	 */
	public class AesUtils {//密钥 (需要前端和后端保持一致)十六位作为密钥
	    private static final String KEY = "{g;,9~lde^[w`SR5";

	    //密钥偏移量 (需要前端和后端保持一致)十六位作为密钥偏移量
	    private static final String IV = "$JL<&*lZFsZ?:p#1";
	    
	    /**
	     * base 64 decode 
	     * @param base64Code 待解码的base 64 code 
	     * @return 解码后的byte[]
	     */
	    public static byte[] base64Decode(String base64Code) {
	        /* sun.misc.BASE64Decoder是java内部类，有时候会报错，
	         * 用org.apache.commons.codec.binary.Base64替代，效果一样。
	         */
	        //Base64 base64 = new Base64();
	        //byte[] bytes = base64.decodeBase64(new String(base64Code).getBytes());
	        //new BASE64Decoder().decodeBuffer(base64Code);
	        return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code.getBytes());
	    }
	    
	    /** 
	     * AES解密 
	     * @param encryptBytes 待解密的byte[] 
	     * @return 解密后的String 
	     * @throws Exception 
	     */  
	    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        
	        byte[] temp = IV.getBytes(StandardCharsets.US_ASCII);
	        IvParameterSpec iv = new IvParameterSpec(temp);
	        
	        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.US_ASCII), "AES"), iv);
	        byte[] decryptBytes = cipher.doFinal(encryptBytes);
	        
	        return new String(decryptBytes);
	    }

	    /** 
	     * 将base 64 code AES解密 
	     * @param encryptStr 待解密的base 64 code
	     * @return 解密后的string
	     * @throws Exception
	     */ 
	    public static String aesDecrypt(String encryptStr) throws Exception {  
	        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr));  
	    }
	    /**
	     * 加密
	     * @param str
	     * @return
	     */
	    public static String encryptString(String str) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            IvParameterSpec iv = new IvParameterSpec("$JL<&*lZFsZ?:p#1".getBytes(StandardCharsets.US_ASCII));
	            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("{g;,9~lde^[w`SR5".getBytes(StandardCharsets.US_ASCII), "AES"), iv);
	            byte[] encryptBytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

	            //return Base64.getEncoder().encodeToString(encryptBytes);  //java.util.Base64
	            return new String(Base64.encodeBase64(encryptBytes),StandardCharsets.US_ASCII);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return "";
	        }
	    }
	    public static void main(String[] args) throws Exception {
			System.out.println(encryptString("111"));
			System.out.println(aesDecrypt("9+DaJsSKRZbEoru+Ukk2cQ=="));
		}
	  }
