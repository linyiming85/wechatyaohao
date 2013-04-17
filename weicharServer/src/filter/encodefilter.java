package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class encodefilter  implements Filter {

    protected String encoding = null;   
    
    protected FilterConfig filterConfig = null;   
    
    protected boolean ignore = true;   
    
  
  public void destroy() {   
    
   this.encoding = null;   
   this.filterConfig = null;   
    
  }   
    
  public void doFilter1(ServletRequest request, ServletResponse response,   
    FilterChain chain) throws IOException, ServletException {   
    
       if (ignore || (request.getCharacterEncoding() == null)) {   
    String encoding = selectEncoding(request);   
    if (encoding != null) {   
     request.setCharacterEncoding(encoding);   
    }   
   } 
     
   // Pass control on to the next filter   
   chain.doFilter(request, response);   
    
  }   
 public void init(FilterConfig filterConfig) throws ServletException {   
    
   this.filterConfig = filterConfig;   
   this.encoding = filterConfig.getInitParameter("encoding");   
   String value = filterConfig.getInitParameter("ignore");   
   if (value == null) {   
    this.ignore = true;   
   } else if (value.equalsIgnoreCase("true")) {   
    this.ignore = true;   
   } else if (value.equalsIgnoreCase("yes")) {   
    this.ignore = true;   
   } else {   
    this.ignore = false;   
   }   
    
  }   
    
  protected String selectEncoding(ServletRequest request) {   
    
   return (this.encoding);   
    
  }

public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
		throws IOException, ServletException {
	// TODO Auto-generated method stub
	
}   

}
