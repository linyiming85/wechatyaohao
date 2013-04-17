package test;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.net.URLConnection;  
  
import test.DESHelper;
  
public class test {  
    public static void post(){  
        String result = "";  
        try {  
            String urlStr = "http://10.1.251.155:7001/weicharServer/index.jsp";  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/html");  
              
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());      
            String xmlInfo = "mobile:用户号码|type:运营商标志|content:我来了";  
            out.write(DESHelper.doWork(xmlInfo, "01010101", 0));  
            out.flush();  
            out.close();  
              
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));  
            String line = "";  
            StringBuffer buf = new StringBuffer();  
            while ( (line = br.readLine()) != null ) {  
                buf.append(line);  
            }  
            result = buf.toString();  
            result = DESHelper.doWork(result, "01010101", 1);  
            System.out.println("回复："+result);  
            //result = new String(DESHelper.doWork(result, "sopu01hz", 1));   
            //System.out.println(result);   
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    public static void main(String[] args){  
    	System.out.println("1111");
        test.post();  
    }  
}  
