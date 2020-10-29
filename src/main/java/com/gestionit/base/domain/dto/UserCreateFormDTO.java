/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestionit.base.domain.dto;

import com.gestionit.base.domain.Role;
import javax.validation.constraints.NotNull;


/**
 *
 * @author fafre
 */
public class UserCreateFormDTO {
    @NotNull
    private String email="";
    
    @NotNull
    private String password="";
    
    @NotNull
    private String passwordRepeated="";
    

    private Role role = Role.USER;
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
    
    
}
