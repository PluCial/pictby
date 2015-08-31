package com.pictby.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.pictby.enums.ItemGcsRole;

@Model(schemaVersion = 1)
public class ItemGcsRes extends GcsResources implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 役割
     */
    private ItemGcsRole role;
    
    /** Itemとの関連 */
    private ModelRef<Item> itemRef = new ModelRef<Item>(Item.class);

    public ItemGcsRole getRole() {
        return role;
    }

    public void setRole(ItemGcsRole role) {
        this.role = role;
    }

    public ModelRef<Item> getItemRef() {
        return itemRef;
    }
}
