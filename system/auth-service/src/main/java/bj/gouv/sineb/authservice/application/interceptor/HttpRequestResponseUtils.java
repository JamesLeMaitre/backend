package bj.gouv.sineb.authservice.application.interceptor;

import bj.gouv.sineb.authservice.application.constant.AppAuthConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


@Slf4j
public final class HttpRequestResponseUtils {

    private HttpRequestResponseUtils() {
    }

    private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

    public static String getClientIpAddress() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getRequestUrl() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURL().toString();
    }

    public static String getRequestUri() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURI();
    }

    public static String getRefererPage() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String referer = request.getHeader("Referer");

        return referer != null ? referer : request.getHeader("referer");
    }

    public static String getPageQueryString() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getQueryString();
    }

    public static String getUserAgent() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String userAgent = request.getHeader("User-Agent");

        return userAgent != null ? userAgent : request.getHeader("email-agent");
    }

    public static String getRequestMethod() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getMethod();
    }

    public static String getLoggedInUser() {
        String userJson = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            ObjectMapper mapper = new ObjectMapper();

            try {
                userJson = mapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return userJson;
        }

        return userJson;
    }

    public static String getLoggedInUserName() {
        /* DECOMMENTER CECI PEUT CREER UN NPE SURTOUT A LA DERNIERRE LIGNE
        System.out.println("SecurityContextHolder.getContext .... : "+SecurityContextHolder.getContext());
        System.out.println("SecurityContextHolder.getContext().getAuthentication() .... : "+SecurityContextHolder.getContext().getAuthentication());
        System.out.println("SecurityContextHolder.getContext().getAuthentication().isAuthenticated() ... : "+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());*/
        try{
            if (SecurityContextHolder.getContext().getAuthentication() != null
                    && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                    && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
                return SecurityContextHolder.getContext().getAuthentication().getName();
            }else {
                log.info("Goting the default value of userId");
                return AppAuthConstant.SYSTEM_USER_EMAIL; //AppConstant.SYSTEM_USER_ID.toString();
            }
        }catch (Exception exception){
            log.info("Goting the default value of userId after exception raised");
            return AppAuthConstant.SYSTEM_USER_EMAIL; //AppConstant.SYSTEM_USER_ID.toString();
        }
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        // Create a new HttpServletRequestWrapper instance, wrapping the original event
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request) {
            private byte[] requestBodyBytes;

            @Override
            public ServletInputStream getInputStream() throws IOException {
                if (requestBodyBytes == null) {
                    // Buffer the event body
                    requestBodyBytes = super.getInputStream().readAllBytes();
                }
                return new ServletInputStream() {
                    private ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBodyBytes);

                    @Override
                    public boolean isFinished() {
                        return byteArrayInputStream.available() == 0;
                    }

                    @Override
                    public boolean isReady() {
                        return true;
                    }

                    @Override
                    public void setReadListener(ReadListener listener) {
                    }

                    @Override
                    public int read() throws IOException {
                        return byteArrayInputStream.read();
                    }
                };
            }

            @Override
            public BufferedReader getReader() throws IOException {
                if (requestBodyBytes == null) {
                    // Buffer the event body
                    requestBodyBytes = super.getInputStream().readAllBytes();
                }
                return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestBodyBytes)));
            }
        };

        // Return the event body as a string
        StringBuilder requestBodyBuilder = new StringBuilder();
        BufferedReader reader = requestWrapper.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        return requestBodyBuilder.toString();
    }


}