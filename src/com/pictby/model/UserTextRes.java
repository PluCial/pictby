package com.pictby.model;

import java.io.Serializable;

import org.slim3.datastore.Model;

import com.pictby.enums.UserTextRole;

@Model(schemaVersion = 1)
public class UserTextRes extends TextResources implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 役割
     */
    private UserTextRole role;

    public UserTextRole getRole() {
        return role;
    }

    public void setRole(UserTextRole role) {
        this.role = role;
    }
}
