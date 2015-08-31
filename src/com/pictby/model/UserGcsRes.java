package com.pictby.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

import com.pictby.enums.UserGcsRole;

@Model(schemaVersion = 1)
public class UserGcsRes extends GcsResources implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 役割
     */
    private UserGcsRole role;
    
    /** 無効フラグ(アイコン画像の変更時に使用) */
    private boolean invalid = false;

    public UserGcsRole getRole() {
        return role;
    }

    public void setRole(UserGcsRole role) {
        this.role = role;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}
