package ca.sait.lab6.servlets;

import ca.sait.lab6.models.Role;
import ca.sait.lab6.models.User;
import ca.sait.lab6.services.RoleService;
import ca.sait.lab6.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sheng Ming Yan
 */
public class UserServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService service = new UserService();
        RoleService serviceRole = new RoleService();
        
        try {
            List<User> users = service.getAll();
            List<Role> roles = serviceRole.getAll();
            
            request.setAttribute("users", users);
            
//            this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String action = request.getParameter("action");
//        if (action != null && action.equals("view")) {
        if (action != null) {
            try {
                String email = request.getParameter("email");
                User user = service.get(email);
                request.setAttribute("user", user);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");       
        UserService service = new UserService();
        RoleService serviceRole = new RoleService();

        String action = request.getParameter("action");
        boolean active = request.getParameter("active") == "Y";
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        
        String roleName= request.getParameter("roleName");
        int roleID = Integer.parseInt(request.getParameter("roleID"));
        
//        Role role = new role(roleID,roleName);

        try {
            switch (action) {
                case "create":
                    service.insert(email, active, firstName, lastName, password, roleID);
                    break;
                case "update":
                    service.update(email, active, firstName, lastName, password, roleID);
                    break;
                case "delete":
                    service.delete(email);
                    break;
            }
//            request.setAttribute("message", action);
        } catch (Exception ex) {
             Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            request.setAttribute("message", "error");
        }

        try {
            List<User> users = service.getAll();
            
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }


}
