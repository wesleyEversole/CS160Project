/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Website;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley
 */
public class Registration extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //might change to User Name
            String userName = request.getParameter("usernName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String passwordConf = request.getParameter("passwordConfirmation");
            if (password.equals(passwordConf)) {
                //process data 
                if (addNewUser(userName, email, password)) {
                    //goto confermation page
                    //could also send user to the login page
                    //or send an email *still don't what really should go here*
                } else {
                    //give errors on what happend/return user to registration page
                }
            } else {
                //reject the passwords, might be posible to do this at the html level
                /* TEMPLATE FOR DISPLAYING THE JSP
                request.setAttribute("message", "email is a sjsu email"); // This will be available as ${message}
                    request.setAttribute("message1", "Servlet Register at " + request.getContextPath());
                    request.setAttribute("message2", "account Name= " + accName);
                    request.setAttribute("message3", " email= " + email);
                    request.setAttribute("message4", "password= " + debugHash.getHashedpwString());
                    request.setAttribute("message5", "STRING!!!");
                    request.getRequestDispatcher("registerconfirm.jsp").forward(request, response);
                */
                
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registration</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registration at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private boolean addNewUser(String userName, String email, String password) {
        
        //will handle the db stuff
        //might move the hashing stuff to another class to be called here *still don't know at this momment* 
        //boolean retv = false;
       // String storedPW = "";
       // String buffPW = password;
        Database db = new Database();
        Hasher hser = new Hasher();
        Statement statement = null;
        ResultSet rs = null;
        String insertToAccount="INSERT INTO `mydb`.`accounts` (`userName`, `email`) VALUES (?, ?);";
        //forlater use

        try (Connection con = db.mySQLdbconnect()) {
            statement = con.prepareStatement(insertToAccount);
           //statement.setString(1,"test");
           //statement.setString(2,"test");
           // statement.executeUpdate();
            while (rs.next()) {
               // id = rs.getInt("idAccounts");
               // accName = rs.getString("userName");
             //   storedPW = rs.getString("password");

            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
                rs = null;
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                }
                statement = null;
            }

        }
        //The check for valid user by checking the stored password with the new user given password.
        //planing on changing this to use my hasher class
        //hser.calcuHash(buffPW, id);
        System.out.println("hasher version of hashedpw = " + hser.getHashedpwString());
//        if (storedPW.equals(hser.getHashedpwString())) {
//            retv = true;
//        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
