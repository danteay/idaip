/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.Conexion;

/**
 *
 * @author DANTE
 */
@WebServlet(name = "OpenEval", urlPatterns = {"/OpenEval"})
public class OpenEval extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Conexion conx = new Conexion();

        if(request.getParameter("evl") != null){
            String eval = request.getParameter("evl");
            String query = "UPDATE EVALUACIONES SET CIERRE = 0 WHERE EVALUACION_ID = "+eval;

            try {
                conx.stm.executeQuery(query);
                response.sendRedirect("index.jsp?evl="+eval);
            } catch (Exception e){
                e.printStackTrace();
                response.sendRedirect("error.html");
            }
        }else{
            response.sendRedirect("error.html");
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("error.html");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
