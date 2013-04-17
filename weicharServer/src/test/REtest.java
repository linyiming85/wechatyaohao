package test;
import java.util.regex.*;
public class REtest {
	public static void main(String[] s){
		Pattern pattern=Pattern.compile("");
		Matcher match=pattern.matcher("1122");
		if(match.find()){
			System.out.println(match.group());
		}
	}
}
