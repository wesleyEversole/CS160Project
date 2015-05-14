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
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jason
 */
@WebServlet("/topic")
public class Topic extends HttpServlet {

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
            String op = request.getParameter("op");
            String title ="";
             if (op.equals("0")) {

            } else if (op.equals("1")) {
                title = "General Discussion";
            } else if (op.equals("2")) {
                title = "SJSU News";
            } else if (op.equals("3")) {
                title = "Non-SJSU News";
            } else if (op.equals("4")) {
                title = "Need To Know";
            } else if (op.equals("5")) {
                title = "Internship";
            }
             
             /* suppose to handle generate
              ArrayList<ForumPosts> temp = getForumPosts(op);              
              request.setAttribute("ForumPosts", temp);
              request.setAttribute("no",temp.size());
              */
              ArrayList<ForumPosts> temp = getForumPosts(op);
              System.out.println(temp.size());              
              
              request.setAttribute("row", temp);
              request.setAttribute("title", title);
              request.setAttribute("title1", title);
              request.getRequestDispatcher("topic.jsp").forward(request, response);
        }
    }


   private ArrayList<ForumPosts> getForumPosts(String topic) {
        ArrayList retv = new ArrayList<>();
        int bufferId;
        Date bufferDate;
        int bufferTopic;
        String bufferTitle;
        Blob bufferContent;
        String bufferAuthor;
        int bufferNumberOfReply;
        Database db = new Database();
        PreparedStatement statement = null;
        ResultSet rs = null;

        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */
           
        /*
         +++++++++++SQL QUERY STRINGS+++++++++++
         */


        try (Connection con = db.mySQLdbconnect()) {
            String sqlSelectQuery = "SELECT * FROM mydb.posts WHERE topic="+topic;
            statement = con.prepareStatement(sqlSelectQuery);
           // int temp = Integer.parseInt(topic);
           // System.out.println("topic code: "+temp);
           // statement.setInt(1, temp);
            //statement.setString(2, email);

            statement = con.prepareStatement(sqlSelectQuery);
            //statement.setString(1, email);
            rs = statement.executeQuery();

            while (rs.next()) {
                bufferId = rs.getInt("idPosts");
                bufferDate = rs.getDate("date");
                bufferTopic = rs.getInt("topic");
                bufferContent = rs.getBlob("content");
                bufferTitle = rs.getString("title");                
                bufferAuthor = rs.getString("op");
                retv.add(new ForumPosts(bufferId, bufferDate,bufferTopic,bufferTitle, bufferContent,bufferAuthor));
                
                // bufferAcnName = rs.getString("userName");
                System.out.println("bufferId = " + bufferId);
                // System.out.println("bufferName = " + bufferAcnName);
            }

            con.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
            System.err.println(Arrays.toString(ex.getStackTrace()));

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
