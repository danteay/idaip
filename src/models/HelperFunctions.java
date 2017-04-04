
package models;

import java.sql.ResultSet;
import core.Conexion;
import java.sql.SQLException;

public class HelperFunctions {
    
    public Conexion objconx;
    
    public HelperFunctions(Conexion conx){
        this.objconx = conx;
    }
    
    public ResultSet getQuestiosForEvaluation(String eval, String art){
        try{
            String query = "select numero || '. ' || descripcion Descripcion,af.articulo_fraccion_id, ef.EVALUACION_FRACCION_ID, ef.RESPUESTA, ef.COMENTARIO, af.estatus from EVALUACIONES_FRACCIONES ef, articulos_fracciones af where ef.articulo_fraccion_id = af.articulo_fraccion_id And EVALUACION_ID  = "+eval+" And af.Articulo_Id = "+art+" order by af.articulo_id, af.numero";
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
