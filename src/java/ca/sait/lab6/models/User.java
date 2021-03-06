package ca.sait.lab6.models;

import java.io.Serializable;
        
/**
 * Represent a user
 * @author Sheng Ming Yan
 */
public class User implements Serializable {
    private String email;
    private boolean active;
    private String firstName;
    private String lastName;
    private String password;
    private int roleID;
    private String roleName;
    
    private Role role;      //Because role is a FK and it refer to another table, 
                            //even thought it is INT type in the database, but we need to create a class for it
    
    public User() {
        
    }
    
        public User(String email, boolean active, String firstName, String lastName, String password, int roleID) {
        this.email = email;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roleID = roleID;
    }

//    public User(String email, boolean active, String firstName, String lastName, String password, int roleID, String roleName) {
//        this.email = email;
//        this.active = active;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//        this.roleID = roleID;
//        this.roleName = roleName;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
    

    
    
    
    
}
