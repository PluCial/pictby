package com.pictby.model;

import java.io.Serializable;

import com.pictby.enums.Social;

public class SocialLink implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Social social;
    
    private String urlPath;
    
    /**
     * コンストラクタ
     * @param social
     * @param urlPath
     */
    public SocialLink(Social social, String urlPath) {
        this.social = social;
        this.urlPath = urlPath;
    }

    public Social getSocial() {
        return social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
