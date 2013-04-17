package test;
import java.io.IOException;
import java.util.List;
import test.QUICKRPY;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public class solrtest {
	static String url = "http://10.1.251.155:9090/solr/collection1";
	static HttpSolrServer solr=new HttpSolrServer(url);
	
	
	public static void main(String[] args)throws IOException, SolrServerException{
		connectSolr();
		String str=new String("PHRASE:你好");
		Search(null, str, 0, 40, null, null);
	}
	
	public static  List getPhrase(String phrase)throws IOException, SolrServerException{
		connectSolr();
		String str=new String("PHRASE:"+phrase);
		List list=Search(null, str, 0, 40, null, null);
		return list;
	}
	
	public static void connectSolr(){

		
		solr.setSoTimeout(1000);  // socket read timeout

		solr.setConnectionTimeout(100);

		solr.setDefaultMaxConnectionsPerHost(100);

		solr.setMaxTotalConnections(100);

		solr.setFollowRedirects(false);  // defaults to false

		  // allowCompression defaults to false.

		  // Server side must support gzip or deflate for this to have any effect.

		solr.setAllowCompression(true);

		solr.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
		
	}
	
	
	public static   List Search(String[] field,String key, int start,
             int count, Boolean[] flag, Boolean hightlight) throws SolrServerException {

     
      SolrQuery query = null;
      try {
             //初始化查询对象
             query = new SolrQuery();
             query.setQuery(key);
             //设置起始位置与返回结果数
             query.setStart(start);
             query.setRows(count);
             query.setFields("REPLY REPLY_TYPE");
             //设置排序
      } catch (Exception e) {
             e.printStackTrace();
      }

      QueryResponse rsp = null;
     
      rsp = solr.query(query);
      System.out.println("1232132131231232fdsfsafsdafsadfasfdsa"+rsp.toString());
      List<QUICKRPY> listt=rsp.getBeans(QUICKRPY.class);
      //SolrDocumentList list = rsp.getResults();
      //System.out.println("一共查出了"+list.size()+"条信息");
      /**
      if(list.size()<50){
	      for(int i=0;i<list.size();i++){
	    	  
	    	  System.out.println("您查询的产品名称: "+list.get(i).getFieldValue("PRODUCT_NAME").toString());
	    	  System.out.println("您查询的产品构成名称: "+list.get(i).getFieldValue("MU_NAME").toString());
	      }
      }
      */
      
      for(QUICKRPY rpy:listt){
    	  System.out.println(rpy.REPLY+"type"+rpy.REPLY_TYPE);
      }
      
      //返回查询结果
      return listt;
}
	
	/**
	 * 1、  添加索引
Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 0; i < list.size(); i++) {
               Item item = list.get(i);
               //设置每个字段不得为空，可以在提交索引前进行检查
               if (CheckItem(item)) {
                      SolrInputDocument doc = new SolrInputDocument();
                      //在这里请注意date的格式，要进行适当的转化，上文已提到
                      doc.addField("id", item.getId());
                      …………
                      docs.add(doc);
               }
        }
 
        try {
               solr.add(docs);
               //对索引进行优化
               solr.optimize();
               solr.commit();
        } catch (Exception e) {
               e.printStackTrace();
        }
	 * 
	 * 
	 */
	
	
	
	/**
	 *   2.  使用据创建bean对象列表，
        try {
              solr.addBeans(beansList);
              } catch (Exception e) {
                     e.printStackTrace();
              } finally {
                     try {
                            solr.optimize();
                            solr.commit();
                     } catch (Exception e) {
                            e.printStackTrace();
                     }
              }
       索引提交完毕。
	 */
	
	/**
	 * 3、  删除索引
       据查询结果删除：      
        try {
                     //删除所有的索引
                     solr.deleteByQuery("*:*");
                     solr.commit();
              } catch (Exception e) {
                     e.printStackTrace();
              }
 
       根据索引号删除索引：
       try {
                     solr.deleteById(ids);
                     solr.commit();
              } catch (Exception e) {
                     e.printStackTrace();
              }
	 */
}
