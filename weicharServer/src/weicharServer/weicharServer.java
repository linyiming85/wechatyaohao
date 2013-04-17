package weicharServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TestJDBC;
import model.Sha1String;
import model.TalkModel;

import org.apache.solr.client.solrj.SolrServerException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import test.QUICKRPY;
import test.solrtest;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

public class weicharServer {
	private static List<QUICKRPY> quickReplyList;
	private static QUICKRPY result;
	public static void post(HttpServletRequest request,
			HttpServletResponse response) throws JDOMException,
			UnsupportedEncodingException, NumberFormatException, SolrServerException {
		String result = "";
		// velidate(request,response);

		try {
			request.setCharacterEncoding("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			System.out.println(request.getCharacterEncoding() + "requset");
			String line = "";
			StringBuffer buf = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buf.append(line);
			}
			result = buf.toString();
			result = new String(result.getBytes("GBK"), "UTF-8");
			List lst = new ArrayList();
			// 创建一个新的字符串
			StringReader read = new StringReader(result);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			TalkModel talk = new TalkModel();

			try {
				// 通过输入源构造一个Document

				Document document = sb.build(source);
				Element root = document.getRootElement(); // 取的根元素

				System.out.println(root.getName());// 输出根元素的名称（测试）

				// 得到根元素所有子元素的集合
				List<Element> elementsList = root.getChildren();
				// 获得XML中的命名空间（XML中未定义可不写）
				talk = getTalkEntity(elementsList, response);

			} catch (JDOMException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return "toff :  "+result;

	}

	// 处理GET请求

	public static void velidate(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("11111");
		String signature = request.getParameter("signatrue");

		System.out.println(request.getParameter("signature") + "signature");
		String timestamp = request.getParameter("timestamp");
		System.out.println(request.getParameter("timestamp") + "timestamp");
		String nonce = request.getParameter("nonce");
		System.out.println(request.getParameter("nonce") + "nonce");
		String echostr = request.getParameter("echostr");
		System.out.println(request.getParameter("echostr") + "echostr");
		String[] strs = new String[] { "aibetalk", timestamp, nonce };
		Arrays.sort(strs);
		String newString = strs[0] + strs[1] + strs[2];
		System.out.println(newString);
		String sha1String = Sha1String.SHA1(newString);
		System.out.println(sha1String);
		/**
		 * if(sha1String.equals(request.getParameter("signature"))){ return
		 * true; }else{ return false; }
		 */
		try {
			OutputStream out = response.getOutputStream();
			System.out.println(echostr + "resultEchostr");
			byte[] midbytes = echostr.getBytes("UTF8");
			response.setContentLength(midbytes.length);
			System.out.println(midbytes[0]);
			out.write(midbytes);
			out.flush();
			out.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static TalkModel getTalkEntity(List<Element> elementsList,
			HttpServletResponse response) throws IOException, NumberFormatException, SolrServerException {
		TalkModel talk = new TalkModel();
		String xmlInfo=new String();
		String sql = "select apply_name,result_date from apply_result_info where apply_no = '";
		OutputStream out = response.getOutputStream();
		byte[] bytee = null;
		//response.setCharacterEncoding("UTF-8");
		StringBuffer outmsg = new StringBuffer();
		outmsg.append("<xml>");
		outmsg.append("<ToUserName><![CDATA["
				+ ((Element) elementsList.get(1)).getText()
				+ "]]></ToUserName>");
		outmsg.append("<FromUserName><![CDATA["
				+ ((Element) elementsList.get(0)).getText()
				+ "]]></FromUserName>");
		outmsg.append("<CreateTime>" + talk.CreateTime + "</CreateTime>");
		talk.setToUserName(((Element) elementsList.get(1)).getText());
		talk.setFormUserName(((Element) elementsList.get(0)).getText());
		for (int i = 2; i < elementsList.size(); i++) {
			Element element = elementsList.get(i);
			System.out.println(element.getText());
			if (element.getName().equals("Content")) {
				if (element.getText().length() == 13
						&& isDouble(element.getText())) {
					sql += element.getText() + "'";
					List list = TestJDBC.getStrsList(sql);
					sql = "select * from apply_query_log where apply_no ='"
							+ element.getText() + "'";
					List Loglist = TestJDBC.getStrsList(sql);
					if (Loglist.size() == 0) {
						sql = "insert into apply_query_log values ('"
								+ talk.getToUserName() + "','"
								+ element.getText() + "',sysdate)";
						System.out.println(sql);
						TestJDBC.exq(sql);
					}
					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							String[] strs = (String[]) list.get(j);
							talk.setContent("您的姓名是" + strs[0]
									+ ",恭喜您，您摇中了买车资格，您的中签日期是" + strs[1]);
						}
					} else {
						talk.setContent("非常抱歉，您没有摇中买车资格:(");
					}
					outmsg
					.append("<MsgType><![CDATA[text]]></MsgType>");
					outmsg.append("<Content><![CDATA["
					+ talk.getContent() + "]]></Content>");
					outmsg.append("<FuncFlag>0</FuncFlag>");
					outmsg.append("</xml>");
					
				} else if (doReply(element.getText().toLowerCase())) {
					//for (int k = 0; k < quickReplyList.size(); k++) {
						
						String info = result.getREPLY();
						String type = result.getREPLY_TYPE();
						if(type!=null){
						Integer typ=Integer.parseInt(type);
						switch (typ) {
						case 1:
							outmsg
									.append("<MsgType><![CDATA[text]]></MsgType>");
							outmsg.append("<Content><![CDATA["
									+ info + "]]></Content>");
							outmsg.append("<FuncFlag>0</FuncFlag>");
							outmsg.append("</xml>");
							break;
						case 4:
							sql = "select apply_no from apply_query_log where weixin_name='"
									+ talk.getToUserName() + "'";
							System.out.println("用户名:"+talk.getToUserName());
							List applyNoList = TestJDBC.getStrsList(sql);
							String[] sqlWhereClause = new String[applyNoList
									.size()];
							String content = new String();
							if (applyNoList.size() > 0) {
								for (int j = 0; j < applyNoList.size(); j++) {
									String[] strs = (String[]) applyNoList
											.get(j);
									sql = "select apply_name,result_date from apply_result_info where apply_no='"
											+ strs[0] + "'";
									System.out.println("sql:"+sql);
									List res = TestJDBC.getStrsList(sql);
									if (res.size() > 0) {
										String[] strss = (String[]) res.get(0);
										content += "您查询的" + strs[0]
												+ "号码摇中了购车资格，该号码的姓名是"
												+ strss[0] + ",中签日期是"
												+ strss[1] + "\r\n";
									} else {
										content += "非常抱歉,您查询的" + strs[0]
												+ "号码没有摇中:(\r\n";
									}
								}
								talk.setContent(content);
							} else {
								talk.setContent("您还没有输入过任何申请编码，请至少输入一个编码。");
							}
							outmsg
									.append("<MsgType><![CDATA[text]]></MsgType>");
							outmsg.append("<Content><![CDATA["
									+ talk.getContent() + "]]></Content>");
							outmsg.append("<FuncFlag>0</FuncFlag>");
							outmsg.append("</xml>");
							break;
						case 5:
							 String[] infos=info.split(",");
							 outmsg.append("<MsgType><![CDATA[news]]></MsgType>");
							 outmsg.append("<ArticleCount>1</ArticleCount>");
							 outmsg.append("<Articles>"); 
							 outmsg.append("<item>");
							 outmsg.append("<Title><![CDATA["+infos[0]+"]]></Title>");
							 outmsg.append("<Description><![CDATA["+infos[1]+"]]></Description>");
							 outmsg.append(
							 "<PicUrl><![CDATA[http://aibetalkdev.muroom.net/"+infos[2]+".jpg]]></PicUrl>"
							 );
							 outmsg.append(
							 "<Url><![CDATA[http://"+infos[3]+"]]></Url>");
							 outmsg.append("</item>"); 
							 outmsg.append("</Articles>");
							 outmsg.append("<FuncFlag>1</FuncFlag>"); 
							 outmsg.append("</xml>");
							 break;
					
						}
						}
				}

				else {
					talk.setContent("您发送的信息已收到，您可以发送13位申请编码来查询摇号结果，也可以输入“cx”来查询之前查询过的编码。");
					outmsg
					.append("<MsgType><![CDATA[text]]></MsgType>");
					outmsg.append("<Content><![CDATA["
					+ talk.getContent() + "]]></Content>");
					outmsg.append("<FuncFlag>0</FuncFlag>");
					outmsg.append("</xml>");

				}
			} else if (element.getName().equals("Event")) {
				if (element.getText().equals("subscribe")) {
					talk.setContent("欢迎关注本账号，本账号可提供您查询是否摇中购车资格！您可以发送13位申请编码来查询摇号结果，也可以输入“cx”来查询之前查询过的编码。");
					talk.setContent("您发送的信息已收到，您可以发送13位申请编码来查询摇号结果，也可以输入“cx”来查询之前查询过的编码。");
					outmsg
					.append("<MsgType><![CDATA[text]]></MsgType>");
					outmsg.append("<Content><![CDATA["
					+ talk.getContent() + "]]></Content>");
					outmsg.append("<FuncFlag>0</FuncFlag>");
					outmsg.append("</xml>");

				}
			}
		}
		//if (talk.getContent() != null && talk.getContent().length() > 0) {
			xmlInfo = new String(outmsg.toString().getBytes("UTF-8"),("GBK"));
			System.out.println(outmsg);
			bytee = xmlInfo.getBytes();
			response.setContentLength(bytee.length);
			out.write(bytee);
			out.flush();
			out.close();
		//}
		return talk;
	}

	// 判断字符串里面都是数字
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean doReply(String v) throws IOException, SolrServerException {
		String sql = "select reply,reply_type from quick_reply where phrase like '%"
				+ v + "%'";
		quickReplyList = solrtest.getPhrase(v);
		Random rdm=new Random();
		if (quickReplyList.size() == 0) {
			return false;
		}
		
		else if(quickReplyList.size()>1)
			result=quickReplyList.get(rdm.nextInt(quickReplyList.size()));
		else
			result=quickReplyList.get(0);
		return true;
	}
}