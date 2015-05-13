/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Website;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jason
 */
@WebServlet("/posting")
public class Posting extends HttpServlet {

    private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
    
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
            String opcode = "0";//this is the topic selection
            String option = request.getParameter("topic");
            String title = request.getParameter("title");
            //just need these parts of data
            String content = request.getParameter("content");
            String username = request.getParameter("username");
            //just have the user inpute their username
            if (option.equals("0")) {

            } else if (option.equals("1")) {
                opcode = "1";
            } else if (option.equals("2")) {
                opcode = "2";
            } else if (option.equals("3")) {
                opcode = "3";
            } else if (option.equals("4")) {
                opcode = "4";
            } else if (option.equals("5")) {
                opcode = "5";
            }
            
            int user = userExist(username);
            if (user < 0) {
                request.setAttribute("text", content);
                request.setAttribute("title", title);
                request.getRequestDispatcher("posting.jsp").forward(request, response);
            } else {
                if (makePost(username, title,content,opcode)) {
                     request.setAttribute("title", title);
                     request.setAttribute("title1", title);
                     request.setAttribute("author", username);
                     request.setAttribute("message", content);
                     request.getRequestDispatcher("thread.jsp").forward(request, response);
                } else {

                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Post Failed</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1> Title: " + title + "\nContent: " + content + "</h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
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

    public int userExist(String userName) {
        boolean retv = false;
        int id = -1;
        Database db = new Database();
        Hasher hser = new Hasher();
        Statement statement = null;
        ResultSet rs = null;
        //forlater use

        try (Connection con = db.mySQLdbconnect()) {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT idAccounts FROM mydb.accounts ma WHERE ma.userName='" + userName + "'");
            System.out.println("++++++++++++++++++++++");
            System.out.println(rs);
            while (rs.next()) {
                id = rs.getInt("idAccounts");
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
        return id;
    }

    public boolean makePost(String user,String title, String content,String opCode) throws SQLException {
        boolean retv = false;       
        Blob bufferContent ;        
        Database db = new Database();  
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */
        
        String sqlInsertQuery = "INSERT INTO "
                + "mydb.posts (content,title,topic,op) VALUES(\""+content+"\",\""+title+"\","+opCode+",\""+user+"\")";
        
        //String sqlInsertQuery = "INSERT INTO mydb.posts (content,title,op) VALUES(\"testContent\",\"Title\",25)";
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */

        try (Connection con = db.mySQLdbconnect()) {
            bufferContent = con.createBlob();
            bufferContent.setBytes(1,content.getBytes());
            statement = con.prepareStatement(sqlInsertQuery);
            if(statement.executeUpdate()==1){
            retv = true;
            }
            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            
                        System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            
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
    
    private static String getCurrentTimeStamp() {
 
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
 
	}
    
}
