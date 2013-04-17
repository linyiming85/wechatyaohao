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
		String str=new String("PHRASE:���");
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
             //��ʼ����ѯ����
             query = new SolrQuery();
             query.setQuery(key);
             //������ʼλ���뷵�ؽ����
             query.setStart(start);
             query.setRows(count);
             query.setFields("REPLY REPLY_TYPE");
             //��������
      } catch (Exception e) {
             e.printStackTrace();
      }

      QueryResponse rsp = null;
     
      rsp = solr.query(query);
      System.out.println("1232132131231232fdsfsafsdafsadfasfdsa"+rsp.toString());
      List<QUICKRPY> listt=rsp.getBeans(QUICKRPY.class);
      //SolrDocumentList list = rsp.getResults();
      //System.out.println("һ�������"+list.size()+"����Ϣ");
      /**
      if(list.size()<50){
	      for(int i=0;i<list.size();i++){
	    	  
	    	  System.out.println("����ѯ�Ĳ�Ʒ����: "+list.get(i).getFieldValue("PRODUCT_NAME").toString());
	    	  System.out.println("����ѯ�Ĳ�Ʒ��������: "+list.get(i).getFieldValue("MU_NAME").toString());
	      }
      }
      */
      
      for(QUICKRPY rpy:listt){
    	  System.out.println(rpy.REPLY+"type"+rpy.REPLY_TYPE);
      }
      
      //���ز�ѯ���
      return listt;
}
	
	/**
	 * 1��  �������
Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 0; i < list.size(); i++) {
               Item item = list.get(i);
               //����ÿ���ֶβ���Ϊ�գ��������ύ����ǰ���м��
               if (CheckItem(item)) {
                      SolrInputDocument doc = new SolrInputDocument();
                      //��������ע��date�ĸ�ʽ��Ҫ�����ʵ���ת�����������ᵽ
                      doc.addField("id", item.getId());
                      ��������
                      docs.add(doc);
               }
        }
 
        try {
               solr.add(docs);
               //�����������Ż�
               solr.optimize();
               solr.commit();
        } catch (Exception e) {
               e.printStackTrace();
        }
	 * 
	 * 
	 */
	
	
	
	/**
	 *   2.  ʹ�þݴ���bean�����б�
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
       �����ύ��ϡ�
	 */
	
	/**
	 * 3��  ɾ������
       �ݲ�ѯ���ɾ����      
        try {
                     //ɾ�����е�����
                     solr.deleteByQuery("*:*");
                     solr.commit();
              } catch (Exception e) {
                     e.printStackTrace();
              }
 
       ����������ɾ��������
       try {
                     solr.deleteById(ids);
                     solr.commit();
              } catch (Exception e) {
                     e.printStackTrace();
              }
	 */
}
