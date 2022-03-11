package ca.sait.lab6.dataaccess;

import ca.sait.lab6.models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ca.sait.lab6.models.User;


public class UserDB {

    //public List<User> getAll(String owner) throws Exception {  // No need owner, owner is who own this note
                                                                // we dont care role right now
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //String sql = "SELECT * FROM user WHERE owner=?";  // since we dont have owner, we can take out WHERE clause!
        //String sql = "SELECT * FROM user";
        
        String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role";   // here can include the ; from the SQL statement or not
                                                                                                           //ex.
                                                                                                            //String sql = 
                                                                                                            //"SELECT * FROM user INNER JOIN role ON role.role_id = user.role;";
        
        try {
            
            ps = con.prepareStatement(sql);  //since we dont have to edit anything, no input for SQL, so dont have to use prepare Statement
                                            // just use regular statement is fine
            
            //ps.setString(1, owner);    // we dont have owner, we dont have to edit anything, so take out the setString() as we dont need
            rs = ps.executeQuery();
            //prepare statement is more secure try to use it, dont just use regular stament
            //I would have any input and jsut stuck with create statement, that is insecure
            // and that allows for SQL injection happen
            
            // Above two lines is equiveilant as a single line in below
            //Regular Statement:                           
            //con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                String email = rs.getString(1);  // or rs.getString("email"); either get the column number or the column name is ok
                                                // when you specific join a table use column name
                                                
                                                //if two tables have the same column name, they both have different data
                                                //in this case, either change the select statement of SQL
                                                //ex. SELECT user.role AS user_role_id, role.role AS role_role_role From user
                                                // INNER JOIN role ON role.role_id = user.role;
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(7);

                Role role = new Role(roleID, roleName);
                User user = new User(email, active, firstName, lastName, password, role);
                
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }

    public User get(String email) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //String sql = "SELECT * FROM user WHERE email=?";
        //String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email =?";
        String sql = "SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email =? LIMIT 1";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);     //here will replace the ? mark above --> String sql = "SELECT * FROM user WHERE email=?";
            rs = ps.executeQuery();
            if (rs.next()) {            // here can only use if statement, cannot use while, because
                                        //SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email =?
                                        //will only return either 0 or 1 record !!
                                        
                                //How to ensure SQL return only 1 result with the same email
                                //add: LIMIT 1
                                //ex.
                                //SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email =? LIMIT 1;
                                //LIMIT 1 means only 1 result can be return
                                //LIMIT 0,1 means 0 or 1 same as LIMIT 1     -- LIMIT 0,1 = LIMIT 1
                                //SELECT * FROM user INNER JOIN role ON role.role_id = user.role WHERE email =? LIMIT 1
                                //same as
                                //SELECT * FROM user INNER JOIN role ON role.role_id = user.role LIMIT 1
                                
                boolean active = rs.getBoolean(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                String roleName = rs.getString(7);

                Role role = new Role(roleID, roleName);
                user = new User(email, active, firstName, lastName, password, role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return user;
    }

    public boolean insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user ('email', 'first_name', 'last_name','password','role') VALUES (?, ?, ?, ?, ?)";
        
        boolean inserted = false;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getId());
//            inserted = ps.executeUpdate() != 0 ? true: false;
            inserted = ps.executeUpdate() != 0;   // same as above
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return inserted;
    }

    public boolean update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET 'first_name' = ?, 'last_name' = ?,'password' = ?,'role' = ? WHERE 'email'=?";
        
        boolean updated;
        
        try {
            ps = con.prepareStatement(sql);
            
            //No need to do any senitization or vailidation (if condition) here, this is just simple for sending it all to the database
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getId());
            ps.setString(5, user.getEmail());
            
            updated = ps.executeUpdate() != 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return updated;
    }

    public boolean delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        //String sql = "DELETE FROM user WHERE email=?";  // WHERE email = (input)
        String sql = "UPDATE user SET active = 0 WHERE email=?";   // instead of permenantly delete a user, we can make it as a soft delete
        
        boolean deleted;
        
        // because the user role, the deletion does not effected for the role
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            deleted = ps.executeUpdate()!=0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        
        return deleted;
    }

}
