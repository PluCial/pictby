package com.pictby.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.pictby.enums.ItemTextRole;

@Model(schemaVersion = 1)
public class ItemTextRes extends TextResources implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 役割
     */
    private ItemTextRole role;
    
    /** Itemとの関連 */
    private ModelRef<Item> itemRef = new ModelRef<Item>(Item.class);

    public ItemTextRole getRole() {
        return role;
    }

    public void setRole(ItemTextRole role) {
        this.role = role;
    }

    public ModelRef<Item> getItemRef() {
        return itemRef;
    }
}
