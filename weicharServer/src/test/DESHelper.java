package test;
import java.security.SecureRandom;  
import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
  
public class DESHelper {  
    /** 
     * ���ܽ��ܽӿ� 
     * @param data      ���� 
     * @param password  ���ܽ������� ����8λ�ֽ� 
     * @param flag      ���ܽ��ܱ�־ 0������ ��1������ 
     * @return 
     */  
    public static String doWork(String data, String password,int flag) {  
        try {  
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(password.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey securekey = keyFactory.generateSecret(desKey);  
            Cipher cipher = Cipher.getInstance("DES");  
            //    
            if(flag == 0){  
                BASE64Encoder base64encoder = new BASE64Encoder();  
                cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
                return base64encoder.encode(cipher.doFinal(data.getBytes("UTF-8")));  
            }else{  
                BASE64Decoder base64decoder = new BASE64Decoder();  
                byte[] encodeByte = base64decoder.decodeBuffer(data);  
                cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
                byte[] decoder = cipher.doFinal(encodeByte);  
                return new String(decoder,"UTF-8");  
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * test 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            // ����   
            String str = "mobile:15810557051|type:0|content:���";  
            // ����   
            String password = "01010101";  
            String desc = DESHelper.doWork(str, password,0);  
            System.out.println("���ģ�" + desc);  
            // ����   
            str = DESHelper.doWork(desc, password,1);  
            System.out.println("���ģ�" +str);  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
  
    }  
}  