package ca.sait.lab6.services;

import ca.sait.lab6.dataaccess.RoleDB;
import java.util.List;
import ca.sait.lab6.dataaccess.UserDB;
import ca.sait.lab6.models.Role;
import ca.sait.lab6.models.User;

public class RoleService {
    
    //We can create a field and re-use a field
    private RoleDB roleDB = new RoleDB();
    

    public List<Role> getAll() throws Exception {
        List<Role> roles = this.roleDB.getAll();
        return roles;
    }
    
}
