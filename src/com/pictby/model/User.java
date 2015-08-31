package com.pictby.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.pictby.enums.UserGcsRole;
import com.pictby.enums.UserTextRole;
import com.pictby.service.GcsUserResourcesService;
import com.pictby.service.TextUserResourcesService;

@Model(schemaVersion = 1)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /** ユーザーID */
    private String userId;
    
    /**
     * メールアドレス
     */
    private Email email;
    
    /**
     * パスワード
     */
    @Attribute(unindexed = true)
    private String password;
    
    /**
     * アイテム数
     */
    private int itemCount = 0;
    
    /**
     * ソート順index
     */
    @Attribute(unindexed = true)
    private double sortOrderIndex = 0.0;
    
    /**
     * ソーシャルリンクリスト
     */
    @Attribute(lob = true, unindexed = true)
    private List<SocialLink> socialLinks;
    
    /**
     * 無効フラグ
     */
    private boolean invalid = false;
    
    /**
     * 作成日時
     */
    @Attribute(listener = CreationDate.class)
    private Date createDate;
    
    /**
     * 更新日時
     */
    @Attribute(listener = ModificationDate.class)
    private Date updateDate;
    
    // ----------------------------------------------------------------------
    // TextResources
    // ----------------------------------------------------------------------
    /**
     * 名前リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private UserTextRes nameResources;
    
    /**
     * キャッチコピーリソース(永久かしない)
     */
    @Attribute(persistent = false)
    private UserTextRes catchCopyResources;
    
    /**
     * 詳細リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private UserTextRes detailResources;
    
    /**
     * UserのText Resourcesの設定
     * @param resourcesMap
     */
    public void setTextResources(Map<String, UserTextRes> resourcesMap) {
        this.setNameResources(
            TextUserResourcesService.getResourcesByMap(resourcesMap, UserTextRole.USER_NAME)
                );
        
        this.setCatchCopyResources(
            TextUserResourcesService.getResourcesByMap(resourcesMap, UserTextRole.USER_CATCH_COPY)
                );
        
        this.setDetailResources(
            TextUserResourcesService.getResourcesByMap(resourcesMap, UserTextRole.USER_DETAIL)
                );
    }

    public String getName() {
        return nameResources == null ? null : nameResources.getContentString();
    }
    
    public String getNameResourcesKey() {
        return nameResources == null ? null : nameResources.getKey().getName();
    }
    
    public String getCatchCopy() {
        return catchCopyResources == null ? null : catchCopyResources.getContentString();
    }
    
    public String getCatchCopyResourcesKey() {
        return catchCopyResources == null ? null : catchCopyResources.getKey().getName();
    }
    
    public String getDetail() {
        return detailResources == null ? null : detailResources.getContentString();
    }
    
    public String getDetailResourcesKey() {
        return detailResources == null ? null : detailResources.getKey().getName();
    }
    
    // ----------------------------------------------------------------------
    // GCSリソース
    // ----------------------------------------------------------------------
    /**
     * アイコンイメージリソース(永久化しない)
     */
    @Attribute(persistent = false)
    private UserGcsRes iconImageResources;
    
    /**
     * アイコンイメージのUrlを取得
     * @return
     */
    public String getIconImageUrl() {
        return iconImageResources == null ? null : iconImageResources.getServingUrl();
    }
    
    /**
     * UserのGcs Resourcesの設定
     * @param resourcesMap
     */
    public void setGcsResources(Map<String, UserGcsRes> resourcesMap) {
        this.setIconImageResources(
            GcsUserResourcesService.getResourcesByMap(resourcesMap, UserGcsRole.USER_ICON_IMAGE)
                );
    }
    
    
    // ----------------------------------------------------------------------
    // ソーシャルリンクマップの取得
    // ----------------------------------------------------------------------
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public Map<String, SocialLink> getSocialLinkMap() {
        
        Map<String,SocialLink> map = new HashMap<String,SocialLink>();
        
        if(socialLinks == null) return map;
        
        for (SocialLink i : socialLinks) map.put(i.getSocial().toString(),i);
        
        return map;
    }
    
    // ----------------------------------------------------------------------
    // itemTagList(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * タグリスト
     */
    @Attribute(persistent = false)
    private List<ItemTag> itemTagList = new ArrayList<ItemTag>();
    

    public List<ItemTag> getItemTagList() {
        return itemTagList;
    }

    public void setItemTagList(List<ItemTag> itemTagList) {
        this.itemTagList = itemTagList;
    }

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public UserGcsRes getIconImageResources() {
        return iconImageResources;
    }

    public void setIconImageResources(UserGcsRes iconImageResources) {
        this.iconImageResources = iconImageResources;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getSortOrderIndex() {
        return sortOrderIndex;
    }

    public void setSortOrderIndex(double sortOrderIndex) {
        this.sortOrderIndex = sortOrderIndex;
    }

    public UserTextRes getNameResources() {
        return nameResources;
    }

    public void setNameResources(UserTextRes nameResources) {
        this.nameResources = nameResources;
    }

    public UserTextRes getCatchCopyResources() {
        return catchCopyResources;
    }

    public void setCatchCopyResources(UserTextRes catchCopyResources) {
        this.catchCopyResources = catchCopyResources;
    }

    public UserTextRes getDetailResources() {
        return detailResources;
    }

    public void setDetailResources(UserTextRes detailResources) {
        this.detailResources = detailResources;
    }
}
