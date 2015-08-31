package com.pictby.enums;

/**
 * テキストリソース役割
 * <pre>
 * 追加や変更の場合は必ずTextResourcesService 内のUpdateも合わせて修正
 * </pre>
 * @author takahara
 *
 */
public enum Social {
    FACEBOOK(
        "Facebook", 
        "https://www.facebook.com/",
        "fb",
        "fa fa-facebook"),
    GOOGLEPLUS(
        "Google+", 
        "https://plus.google.com/",
        "gplus",
        "fa fa-google-plus"),
    TWITTER(
        "Twitter", "https://twitter.com/",
        "twt",
        "fa fa-twitter"),
    LINKEDIN(
        "Linkedin", "https://www.linkedin.com/",
        "linkdin",
        "fa fa-linkedin"),
    WEBSITE(
        "Webサイト",
        null, 
        "dribble",
        "fa fa-dribbble");

    private String socialName;
    private String baseUrl;
    private String linkStyleClass;
    private String iconStyleClass;

    private Social(String socialName, String baseUrl, String linkStyleClass, String iconStyleClass) {
        this.socialName = socialName;
        this.baseUrl = baseUrl;
        this.linkStyleClass = linkStyleClass;
        this.iconStyleClass = iconStyleClass;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getLinkStyleClass() {
        return linkStyleClass;
    }

    public void setLinkStyleClass(String linkStyleClass) {
        this.linkStyleClass = linkStyleClass;
    }

    public String getIconStyleClass() {
        return iconStyleClass;
    }

    public void setIconStyleClass(String iconStyleClass) {
        this.iconStyleClass = iconStyleClass;
    }

    
}
