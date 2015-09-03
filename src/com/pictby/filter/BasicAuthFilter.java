package com.pictby.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.util.Base64;

public class BasicAuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(BasicAuthFilter.class.getName());
    public static final String BASIC_AUTH_USERNAME = "BASIC_AUTH_USERNAME";

    Map<String, String>  userMap = new HashMap<String, String>();
    
    String REALM = "Staging"; // defaultのREALM
    String passwdFile = "WEB-INF/passwd.prop"; // defaultのパスワードファイル

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String authHeader = req.getHeader("Authorization");
            
            // ターゲットチェック & Authorization ヘッダを確認
            if (isTarget(req) && !tryAuth(authHeader, req)) {
                // 承認処理
                send401(response, REALM, "Authentication Required for " + REALM);
            
            }else {
                // 通常処理
                chain.doFilter(request, response);
            }
            
        } catch (ServletException e) {
            log.severe(e.getMessage());
        } catch (IOException e) {
            log.severe(e.getMessage());
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        userMap.put("bases", "hakata");
    }
    
    public void destroy() {
        // TODO 自動生成されたメソッド・スタブ

    }
    
    /**
     * フィルターターゲットかをチェック
     * @param req
     * @return
     */
    private boolean isTarget( HttpServletRequest request) {
        
        // ドメインチェック(ローカルの場合は除外)
        String serverName = request.getServerName() != null ? request.getServerName() : "";
        if(serverName.equals("localhost") || serverName.equals("127.0.0.1")) return false;
        
        // 静的ファイルチェック
        String url = request.getRequestURL().toString();
        if(url.endsWith(".js") 
                || url.endsWith(".html") 
                || url.endsWith(".jpg") 
                || url.endsWith(".png")) return false;
        
        // 埋め込みタグ
        if(url.indexOf("embed") >= 0) return false;
        
        return true;
    }

    private boolean tryAuth(String authHeader, HttpServletRequest req) {
        if (authHeader == null)
            return false;
        String[] pair = authHeader.split(" ");
        if (pair.length != 2) {
            log.severe("failed to parse authHeader: " + authHeader);
            return false;
        }
        if (!pair[0].equals("Basic")) { // 認証スキームがBasicであることを確認
            log.severe("unsupported login scheme: " + pair[0]);
            return false;
        }
        String decoded = new String(Base64.decodeBase64(pair[1].getBytes()));
        String[] userPass = decoded.split(":");
        if (userPass.length != 2 || !userMap.containsKey(userPass[0])
                || !userMap.get(userPass[0]).equals(userPass[1])) {
            log.severe("AuthFailure: " + decoded);
            return false;
        }
        log.info("authentication succeeded for user '" + userPass[0] + "'");
        req.setAttribute(BASIC_AUTH_USERNAME, userPass[0]);
        return true;
    }

    // 401 レスポンスを送信
    private void send401(ServletResponse response, String realm, String message)
            throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(401);
        res.setHeader("WWW-Authenticate", "Basic realm=" + realm);
        res.getWriter().println("<body><h1>" + message + "</h1></body>\n");
        return;
    }

}
