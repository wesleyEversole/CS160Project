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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wesley
 */
@WebServlet(name = "Threads", urlPatterns = {"/Threads"})
public class Threads extends HttpServlet {

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
        
        String id = request.getParameter("id");
        int idCode = Integer.parseInt(id);
        ForumPosts temp = getThread(idCode);
         //ForumPosts temp = getThread(1);
        request.setAttribute("title", temp.getTitle());
        request.setAttribute("title1", temp.getTitle());
        request.setAttribute("author", temp.getAuthor());
        request.setAttribute("message", "example data");
        request.getRequestDispatcher("thread.jsp").forward(request, response);                
        }
    }
    private ForumPosts getThread(int postId){
        ForumPosts retv = null ;
        int bufferId;
        Date bufferDate;
        int bufferTopic;
        String bufferTitle;
        Blob bufferContent;
        int bufferNumberOfReply;
        String bufferAuthor;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet rs = null;

        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */
           String sqlSelectQuery = "SELECT * FROM mydb.posts WHERE idPosts="+postId;
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */


        try (Connection con = db.mySQLdbconnect()) {
             statement = con.prepareStatement(sqlSelectQuery);            
            rs = statement.executeQuery();
           

            while (rs.next()) {
                bufferId = rs.getInt("idPosts");
                bufferDate = rs.getDate("date");
                bufferTopic = rs.getInt("topic");
                bufferContent = rs.getBlob("content");
                bufferTitle = rs.getString("title");                
                bufferAuthor = rs.getString("op");
                retv=new ForumPosts(bufferId, bufferDate,bufferTopic,bufferTitle, bufferContent,bufferAuthor);
                
                // bufferAcnName = rs.getString("userName");
                System.out.println("bufferId = " + bufferId);
                // System.out.println("bufferName = " + bufferAcnName);
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
