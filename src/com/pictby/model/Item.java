package com.pictby.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.pictby.enums.ItemGcsRole;
import com.pictby.enums.ItemTextRole;
import com.pictby.service.GcsItemResourcesService;
import com.pictby.service.TextItemResourcesService;

@Model(schemaVersion = 1)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * ソート順
     */
    private double sortOrder = 0.0;
    
    /** Userとの関連 */
    private ModelRef<User> userRef = new ModelRef<User>(User.class);
    
    /**
     * UserId 検索した結果リストの表示時に利用する
     */
    @Attribute(unindexed = true)
    private String userId;
    
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
    private ItemTextRes nameResources;
    
    /**
     * 詳細リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private ItemTextRes detailResources;
    
    /**
     * UserのText Resourcesの設定
     * @param resourcesMap
     */
    public void setTextResources(Map<String, ItemTextRes> resourcesMap) {
        this.setNameResources(
            TextItemResourcesService.getResourcesByMap(resourcesMap, ItemTextRole.ITEM_NAME)
                );
        
        this.setDetailResources(
            TextItemResourcesService.getResourcesByMap(resourcesMap, ItemTextRole.ITEM_DETAIL)
                );
    }

    public String getName() {
        return nameResources == null ? null : nameResources.getContentString();
    }
    
    public String getNameResourcesKey() {
        return nameResources == null ? null : nameResources.getKey().getName();
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
    private ItemGcsRes originalImageResources;
    
    /**
     * UserのGcs Resourcesの設定
     * @param resourcesMap
     */
    public void setGcsResources(Map<String, ItemGcsRes> resourcesMap) {
        this.setOriginalImageResources(
            GcsItemResourcesService.getResourcesByMap(resourcesMap, ItemGcsRole.ITEM_ORIGINAL_IMAGE)
                );
    }
    
    public String getOriginalImageUrl() {
        return originalImageResources == null ? "" : originalImageResources.getServingUrl();
    }
    
    public String getOriginalImageContentType() {
        return originalImageResources == null ? null : originalImageResources.getContentType();
    }
    
    public int getOriginalImageWidth() {
        return originalImageResources == null ? 0 : originalImageResources.getWidth();
    }
    
    public int getOriginalImageHeight() {
        return originalImageResources == null ? 0 : originalImageResources.getHeight();
    }
    

    
    // ----------------------------------------------------------------------
    // tagsList(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * タグリスト
     */
    @Attribute(persistent = false)
    private List<String> tagsList = new ArrayList<String>();

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
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
        Item other = (Item) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public double getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ModelRef<User> getUserRef() {
        return userRef;
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
    
    public ItemGcsRes getOriginalImageResources() {
        return originalImageResources;
    }

    public void setOriginalImageResources(ItemGcsRes originalImageResources) {
        this.originalImageResources = originalImageResources;
    }

    public ItemTextRes getNameResources() {
        return nameResources;
    }

    public void setNameResources(ItemTextRes nameResources) {
        this.nameResources = nameResources;
    }

    public ItemTextRes getDetailResources() {
        return detailResources;
    }

    public void setDetailResources(ItemTextRes detailResources) {
        this.detailResources = detailResources;
    }
}
