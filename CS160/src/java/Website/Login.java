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
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = ("([A-Z]|[a-z])[a-z]*");
            String lastname = "\\." + name;
            String emailRegx = name + lastname + "@sjsu.edu";

            if (email.matches(emailRegx)) {
                //  System.out.println("email is not : " + email);
                //System.out.println("password is not :" + password); 
                System.out.println("authUser = " + authUser(email, password));
                if (authUser(email, password)) {
                    /*
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Login</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>email is a sjsu email <h1>");
                    out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
                    out.println("<h2> account Name= " + accName + "</h2>");
                    out.println("<h2> email= " + email + "</h2>");
                    //this is bad but is here for debug REMOVE at later point
                    out.println("<h2> password= " + hashPW(password, makeSalt(id)) + "</h2>");
                    out.println("<h2>This is a vaild user</h2>");
                    out.println("</body>");
                    out.println("</html>");
                    //response.sendRedirect("loginconfirm.html");
                    */
                    
                     request.setAttribute("message", "email is a sjsu email"); // This will be available as ${message}
                    request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                    request.setAttribute("message2", "account Name= " + accName);
                    request.setAttribute("message3", " email= " + email);
                    request.setAttribute("message4", "password= " + hashPW(password, makeSalt(id)));
                    request.setAttribute("message5", "This is a vaild user");
                    request.getRequestDispatcher("loginconfirm.jsp").forward(request, response);
                    
                }else{
                    /*
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Login</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> bad authUser <h1>");
                out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
                out.println("<h2> email= " + email + "</h2>");
                out.println("<h2> password= " + password + "</h2>");
                out.println("<h2> hashedpassword= " + hashPW(password, makeSalt(id)) + "</h2>");
                out.println("<h2> debugbuff="+debugbuff+"</h2>");
                out.println("</body>");
                out.println("</html>");
                    */
                    
                    request.setAttribute("message", " bad authUser"); // This will be available as ${message}
                    request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                    request.setAttribute("message2", "email= " + email);
                    request.setAttribute("message3", "password= " + password);
                    request.getRequestDispatcher("loginfail.jsp").forward(request, response);
                }
            } else {
                /*
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Login</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1> bad email adress:email adress is not a sjsu email <h1>");
                out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
                out.println("<h2> email= " + email + "</h2>");
                out.println("<h2> password= " + password + "</h2>");
                out.println("</body>");
                out.println("</html>");
                //response.sendRedirect("loginconfirm.html");
                */
                
                request.setAttribute("message", "bad email adress:email adress is not a sjsu email"); // This will be available as ${message}
                request.setAttribute("message1", "Servlet Login at " + request.getContextPath());
                request.setAttribute("message2", "email= " + email);
                request.setAttribute("message3", "password= " + password);
                request.getRequestDispatcher("loginfail.jsp").forward(request, response);
                
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("hasher version of hashedpw = "+hser.getHashedpwString());
            if (storedPW.equals(hser.getHashedpwString())) {
                retv = true;
            }
        return retv;
    }

    private String bToHex(byte[] input) {
        Formatter fmat = new Formatter();
        for (int i = 0; i < input.length; i++) {
            fmat.format("%02x", input[i]);

        }
        return fmat.toString();
    }

    private byte[] makeSalt(int id) {
        SecureRandom sr = new SecureRandom(ByteBuffer.allocate(64).putInt(id).array());
        byte[] salt = new byte[64];
        sr.nextBytes(salt);
        return salt;
    }

    private String hashPW(String pw, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        sha512.update(salt);
        sha512.update(pw.getBytes(), 0, pw.length());
        byte[] hashedpassword = sha512.digest();

        for (int i = 0; i < 100000; i++) {
            sha512.update(salt);
            sha512.update(hashedpassword, 0, hashedpassword.length);
            hashedpassword = sha512.digest();
        }

        sha512.reset();
        return bToHex(hashedpassword);
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
