package com.pictby.model;

import java.io.Serializable;

import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class TextTagResources extends TextResources implements Serializable {

    private static final long serialVersionUID = 1L;

    private ModelRef<ItemTag> itemTagRef = new ModelRef<ItemTag>(ItemTag.class);

    public ModelRef<ItemTag> getItemTagRef() {
        return itemTagRef;
    }
}
