package Website;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley
 */
@WebServlet("/login")
public class Login extends HttpServlet {

    //using this for the salt may need to be something else
    //like the password file a file for the init random gen
    private int id;
    private String accName;

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
            /* TODO output your page here. You may use following sample code. */
            Hasher debugHash =new Hasher();
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = ("([A-Z]|[a-z])[a-z]*");
            String lastname = "\\." + name;
            String emailRegx = name + lastname + "@sjsu.edu";

            if (email.matches(emailRegx)) {
                if (authUser(email, password)) {
                    /**
                     debug code
                     */
                    debugHash.calcuHash(password, id);
                    /**
                     debug code
                     */
                    request.setAttribute("message", "email is a sjsu email"); // This will be available as ${message}
                    request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                    request.setAttribute("message2", "account Name= " + accName);
                    request.setAttribute("message3", " email= " + email);
                    request.setAttribute("message4", "password= " + debugHash.getHashedpwString());
                    request.setAttribute("message5", "This is a vaild user");
                    request.getRequestDispatcher("loginconfirm.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", " bad authUser"); // This will be available as ${message}
                    request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                    request.setAttribute("message2", "email= " + email);
                    request.setAttribute("message3", "password= " + password);
                    request.getRequestDispatcher("loginfail.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "bad email adress:email adress is not a sjsu email"); // This will be available as ${message}
                request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                request.setAttribute("message2", "email= " + email);
                request.setAttribute("message3", "password= " + password);
                request.getRequestDispatcher("loginfail.jsp").forward(request, response);

            }
        }
    }

    private boolean authUser(String email, String password) {

        boolean retv = false;
        String storedPW = "";
        String buffPW = password;
        Database db = new Database();
        Hasher hser = new Hasher();
        Statement statement = null;
        ResultSet rs = null;
        //forlater use
        try {
            Connection con = db.mySQLdbconnect();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT idAccounts, userName, password FROM mydb.accounts ma WHERE ma.email='" + email + "'");
            while (rs.next()) {
                id = rs.getInt("idAccounts");
                accName = rs.getString("userName");
                storedPW = rs.getString("password");

            }

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
