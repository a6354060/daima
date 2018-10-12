package com.xiaoyujf.shoubaoAdmin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * Created by lxb on 2018/10/12.
 */
@WebFilter(filterName = "EncodingFilter",urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 解决post乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 解决get乱码
        EncodingRequest encodingRequest = new EncodingRequest(
                (HttpServletRequest) request);
        chain.doFilter(encodingRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

}

class EncodingRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    // 判断是否已经编码
    private boolean hasEncode = false;

    public EncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values == null) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        // getParameterMap
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        return values;
    }

    @Override
    public Map getParameterMap() {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")) {// 忽略大小写的比较
            return parameterMap;
        }
        if (!hasEncode) {
            Set<String> keys = parameterMap.keySet();
            for (String key : keys) {
                String[] values = parameterMap.get(key);
                if (values == null) {
                    continue;
                }
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    try {
                        value = new String(value.getBytes("ISO-8859-1"),
                                "utf-8");
                        // 将解码好的值赋值回去
                        values[i] = value;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                hasEncode = true;
            }
        }
        return parameterMap;
    }
}
