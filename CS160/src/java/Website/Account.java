/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Website;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jason
 */
@WebServlet("/account")
public class Account extends HttpServlet {

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

            String email = "";
            String password = "";
            String passwordConf = "";

            email = request.getParameter("email");
            password = request.getParameter("newPassword");
            passwordConf = request.getParameter("verPassword");

            if (password.equals(passwordConf)) {
                if (userConfirm(email, request.getParameter("curPassword"))) {
                    if (updatePass(email, password)) {
                        /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet Account</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Successful p/w change</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    } else {
                        //fail update
                                /* TODO output your page here. You may use following sample code. */
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet Account</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Update p/w failed</h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                } else {
                    //Wrong P/W
                            /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Account</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>The password enter does not match the password in the system<br>or this email does not exist</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Password mismatch</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Account at " + request.getContextPath() + "</h1>");
                out.println("<h1> Password mismatch </h1>");
                out.println("</body>");
                out.println("</html>");

            }

        }
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

    /*Check to see if the current user password match system */
    private boolean userConfirm(String email, String password) {

        int id = 0;
        boolean retv = false;
        String storedPW = "";
        String buffPW = password;
        Database db = new Database();
        Hasher hser = new Hasher();
        Statement statement = null;
        ResultSet rs = null;
        //forlater use

        try (Connection con = db.mySQLdbconnect()) {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT idAccounts,password FROM mydb.accounts ma WHERE ma.email='" + email + "'");
            System.out.println("++++++++++++++++++++++");
            System.out.println(rs);
            while (rs.next()) {
                id = rs.getInt("idAccounts");
                storedPW = rs.getString("password");
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
        hser.calcuHash(buffPW, id);
        System.out.println("hasher version of hashedpw = " + hser.getHashedpwString());
        if (storedPW.equals(hser.getHashedpwString())) {
            retv = true;
        }
        return retv;
    }

    private boolean updatePass(String email, String password) {
        //will handle the db stuff
        //might move the hashing stuff to another class to be called here *still don't know at this momment* 
        Database db = new Database();
        Hasher hser = new Hasher();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int bufferId = -1;
        boolean retv = false;
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */
        String sqlSelectQuery = "SELECT idAccounts FROM mydb.accounts WHERE email=?";
        String sqlUpdateQuery = "UPDATE `mydb`.`accounts` SET `password`=? WHERE `idAccounts`=?";
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */
        //forlater use

        try (Connection con = db.mySQLdbconnect()) {
                           //query is not complete
            //retv=true;
            // adding a SQL call to find the ID of the account
            statement = con.prepareStatement(sqlSelectQuery);
            statement.setString(1, email);
            rs = statement.executeQuery();
                //might need to add commits where ever I connect to the data base
            //con.commit();
            while (rs.next()) {
                bufferId = rs.getInt("idAccounts");
                System.out.println("bufferId = " + bufferId);
            }
            hser.calcuHash(password, bufferId);
            statement = con.prepareStatement(sqlUpdateQuery);
            statement.setString(1, hser.getHashedpwString());
            statement.setInt(2, bufferId);
            if (statement.executeUpdate() == 1) {
                retv = true;
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

        return retv;
    }
}
