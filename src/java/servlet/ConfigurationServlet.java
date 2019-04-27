/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.ConfigurationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author ADMIN
 */
public class ConfigurationServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConfigurationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfigurationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        try {
            String type = request.getParameter("type");
            String typestk = request.getParameter("typestk");
            String typeofmoney = request.getParameter("typeofmoney");
            try {
                float interestrate = Float.parseFloat(request.getParameter("interestrate"));
                System.out.println(typestk + "" + typeofmoney + "" + interestrate);
                float f = ConfigurationDAO.SetInterestrate(typestk, typeofmoney, interestrate);
                //response.getOutputStream().println("<script> alert(\"Cau hinh thanh cong\"); window.location = 'main.jsp';</script>");
                if (f >= 0) {
                    session.setAttribute("thongbao", "Cấu hình thành công");
                } else {
                    session.setAttribute("thongbao", "Tỉ giá không thế nhỏ hơn không, cho người dùng nhập liệu lại");
                }
                response.sendRedirect("main.jsp");
            } catch (NumberFormatException e) {
                session.setAttribute("thongbao", "Tỉ giá không thể bao gồm các chữ cái và các kí tự đặc biết, cho người dùng nhập liệu lại");
                response.sendRedirect("main.jsp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConfigurationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
