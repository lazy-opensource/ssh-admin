package com.lzy.jurisdcition.ssh.common.sys.filter;

    import org.apache.log4j.Logger;

    import javax.servlet.*;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Enumeration;

public class GlobalRequestFilter implements Filter {

        private Logger log = Logger.getLogger(GlobalRequestFilter.class);


        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

            log.info("GlobalRequestFilter start >>>>>>>>>");
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


            HttpServletResponse response = (HttpServletResponse) servletResponse;

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String noFilter[] = "login/toLogin,login/login,.jsp ,.js,css,images,themes,json,icons".split(",");

            String requestURI = request.getRequestURI();

            log.info("请求路径>>>>>>>"+requestURI);


            boolean isFilter = true;
            for(String url : noFilter){
                if(requestURI.indexOf(url) != -1 || "/".equals(requestURI)){
                    isFilter = false;
                    break;
                }
            }
            if(isFilter){

                String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + "/login/toLogin";

                if(request.getSession(false) == null){

                    log.info("session 失效");

                    Enumeration enu=request.getHeaderNames();//取得全部头信息
                         while(enu.hasMoreElements()) {//以此取出头信息
                             String headerName = (String) enu.nextElement();
                             String headerValue = request.getHeader(headerName);//取出头信息内容

                             log.info("headerName:   "+headerName);
                             log.info("headerValue" + headerValue);
                         }

                    if (request != null
                            && ("XMLHttpRequest".equalsIgnoreCase(request
                            .getHeader("X-Requested-With")) ||
                            "XMLHttpRequest".equalsIgnoreCase(request.getParameter("xRequestedWith")))
                            || request.getHeader("Content-Type").equalsIgnoreCase("application/x-www-form-urlencoded")) {
                        response.setHeader("sessionstatus","timeout");

                        request.getRequestDispatcher(path).forward(request,response);
                        // String open = "<script type='text/javascript'>$(function(){window.open('"+path+"','_top'); })</script>";
                        String href = "<script type='text/javascript'>$(function(){window.location.href= '"+path+"' })</script>";
                        log.info("-------------I m is ajax request");
                        //request.getRequestDispatcher("/login/loginPage").forward(request,response);
                        PrintWriter pw = response.getWriter();
                        try{
                            pw.write(href);
                        }finally{
                            if(pw != null){
                                pw.close();
                            }
                        }
                    }else{
                        System.out.println(path+">>>>>>>>>>>>>>>>>>>");
                        response.setHeader("sessionstatus","timeout");

                        response.sendRedirect(path);
                    }

                }else{
                    filterChain.doFilter(request ,response);
                }
            }else{
                filterChain.doFilter(request ,response);
            }

        }

        @Override
        public void destroy() {

            log.info("GloblaRequestFilter destory  >>>>>>>>");

        }
    }


