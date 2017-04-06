
package controllers;

import java.sql.ResultSet;
import core.Conexion;

public class SuccessController {
    
    private int element;
    private Conexion conx;
    
    public SuccessController(Conexion conx, int element){
        this.element = element;
        this.conx = conx;
    }
    
    public ResultSet getSuccess(int respTo){
        ResultSet res = null;
        
        try{
            String query = "select descripcion Descripcion,(Select Sum ( nvl( de.Respuesta,0)) From Det_Eval_Fracciones de Where de.Evaluacion_Fraccion_Id = ef.Evaluacion_Fraccion_Id) Valor from EVALUACIONES_FRACCIONES ef, articulos_fracciones af where ef.articulo_fraccion_id = af.articulo_fraccion_id And EVALUACION_ID = "+ this.element + " And af.Articulo_id = "+ respTo +" order by numero";
            
            res = this.conx.stm.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return res;
    }
}
