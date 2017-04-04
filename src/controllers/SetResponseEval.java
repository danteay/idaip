
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;

import core.Conexion;

@WebServlet(name = "SetResponseEval", urlPatterns = {"/SetResponseEval"})
public class SetResponseEval extends HttpServlet {

    private void executeQueryTokens(Conexion conx, String query) {
        try{
            conx.stm.executeQuery(query);
            System.out.println("Success: "+query);
        }catch(Exception e){
            System.out.println("Error: "+query);
        }
    }

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
       // TODO: Do nothing
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
        
        String querys = request.getParameter("query");
        String queryComent = request.getParameter("queryComent");
        String queryPoints = request.getParameter("queryPoints");
        String page = request.getParameter("pageSend");
        boolean mode = request.getParameter("mode") != null ? (request.getParameter("mode").equalsIgnoreCase("1") ? true:false):false;
        String percent = request.getParameter("percent");
        
        
        if(querys != null && queryComent != null && queryPoints != null){
            StringTokenizer tokens = new StringTokenizer(querys,";");
            StringTokenizer tokenComent = new StringTokenizer(queryComent,";");
            StringTokenizer tokenPoints = new StringTokenizer(queryPoints,";");
            
            Conexion conx = new Conexion();
            
            //Insercion de campos seleccionados
            while(tokens.hasMoreElements()){
                this.executeQueryTokens(conx, tokens.nextToken());
            }

            //insercion de comentarios
            while(tokenComent.hasMoreElements()){
                this.executeQueryTokens(conx, tokenComent.nextToken());
            }
            
            //insercion de puntaje
            while(tokenPoints.hasMoreElements()){
                this.executeQueryTokens(conx, tokenPoints.nextToken());
            }
            
            if(mode){
                this.executeQueryTokens(conx, "UPDATE EVALUACIONES SET CIERRE = 1 WHERE EVALUACION_ID = "+page);
            }
            
            String query = "UPDATE EVALUACIONES SET RESULTADO = "+percent+", FECHA_EVALUACION = CURRENT_DATE - 1 WHERE EVALUACION_ID = "+page;
            
            this.executeQueryTokens(conx, query);

            response.sendRedirect("response.jsp?evl="+page+"&success");
        }else {
            System.out.println("Updates Vacios....");
            response.sendRedirect("index.jsp?evl=" + page + "&errorUpdate");
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
    }
}
