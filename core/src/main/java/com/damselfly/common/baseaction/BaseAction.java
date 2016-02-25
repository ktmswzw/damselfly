package com.damselfly.common.baseaction;

import com.damselfly.common.SecurityConstants;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.DateConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;


public class BaseAction  implements Serializable {

    protected Logger logger = LoggerFactory.getLogger(getClass());
	/** 
	 * paging params
	 * 
	 */
	//limit=10&offset=10&order=asc&searchText=abcdddd&sortName=username&sortOrder=desc&_=1419487833812
	//limit=10&offset=0&order=asc&pageSize=10&pageNumber=1&searchText=abcdddd&sortName=user_code&sortOrder=desc&_=1419481596318
	protected static final int    PAGING_DEFAULT_PAGESIZE = 10;
	protected static final int    PAGING_DEFAULT_PAGENUMBER = 0;
	protected static final String PAGING_PARAM_PAGESIZE = "limit";
	protected static final String PAGING_PARAM_PAGENUMBER ="offset";
	protected static final String PAGING_PARAM_SORTNAME = "sortName";
	protected static final String PAGING_PARAM_SORTORDER ="sortOrder";
	protected static final String PAGING_RESOULT_ROWS = "rows";
	protected static final String PAGING_RESOULT_TOTAL = "total";



	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	/**
	 * ModelAttribute 
	 * 放置在方法的形参上：表示引用Model中的数据
	 * 放置在方法上面：表示请求该类的每个Action前都会首先执行它
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.session = request.getSession();
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public Page page() {
		
	    	return new Page(
	    			request.getParameter(PAGING_PARAM_PAGENUMBER) != null ? Integer.parseInt(request.getParameter(PAGING_PARAM_PAGENUMBER)) : PAGING_DEFAULT_PAGENUMBER,//第x页
	    			request.getParameter(PAGING_PARAM_PAGESIZE) != null ? Integer.parseInt(request.getParameter(PAGING_PARAM_PAGESIZE)) :PAGING_DEFAULT_PAGESIZE,//每页大小
	    			request.getParameter(PAGING_PARAM_SORTNAME),//排序字段
	    			request.getParameter(PAGING_PARAM_SORTORDER)//排序方式：asc,desc
	    	);
	 }
	 
	 /**
		 * 将HTTP请求参数映射到bean对象中
		 * @param beanClass
		 * @return
		 * @throws Exception
		 */
		public <T> T form(Class<T> beanClass) {
			try{
				T bean = beanClass.newInstance();
				BeanUtils.populate(bean, getParametersStartingWith(request,SecurityConstants.SEARCH_PREFIX));
				return bean;
			}catch(Exception e) {
                e.printStackTrace();
				//throw new ActionException(e.getMessage());
				return null;
			}
		}

    public <T> T SearchForm(Class<T> beanClass) {
        try{
            ConvertUtils.register(new DateConverter(), java.util.Date.class);
            T bean = beanClass.newInstance();
            BeanUtils.populate(bean, getParametersStartingWith(request,SecurityConstants.SEARCH_PREFIX));
            return bean;
        }catch(Exception e) {
            e.printStackTrace();
            //throw new ActionException(e.getMessage());
            return null;
        }
    }

    /**
     * 将HTTP请求参数映射到bean对象中
     * @param beanClass
     * @return
     * @throws Exception
     */
    public <T> T dynamicForm(Class<T> beanClass) {
        try{
            ConvertUtils.register(new DateConverter(), java.util.Date.class);
            T bean = beanClass.newInstance();
            BeanUtils.populate(bean, getParametersStartingWith(request,SecurityConstants.DYNAMIC_PREFIX));
            return bean;
        }catch(Exception e) {
            e.printStackTrace();
            //throw new ActionException(e.getMessage());
            return null;
        }
    }
		
		/**
		 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
		 * 
		 * 返回的结果的Parameter名已去除前缀.
		 */
		@SuppressWarnings("rawtypes")
		public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
			Validate.notNull(request, "Request must not be null");
			Enumeration paramNames = request.getParameterNames();
			Map<String, Object> params = new TreeMap<String, Object>();
			if (prefix == null) {
				prefix = "";
			}
			while ((paramNames != null) && paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				if ("".equals(prefix) || paramName.startsWith(prefix)) {
					String unprefixed = paramName.substring(prefix.length());
					String[] values = request.getParameterValues(paramName);
					if ((values == null) || (values.length == 0)) {
						// Do nothing, no values found at all.
					} else if (values.length > 1) {
						params.put(unprefixed, values);
					} else {
						params.put(unprefixed, values[0]);
					}
				}
			}
			return params;
		}
}
