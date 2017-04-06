
package models;

import java.sql.ResultSet;
import core.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelperFunctions {
    
    public Conexion objconx;
    
    public HelperFunctions(Conexion conx){
        this.objconx = conx;
    }
    
    public ArrayList<Question> getQuestiosForEvaluation(int eval, int art){
        try{
            String query = "select numero || '. ' || descripcion Descripcion,af.articulo_fraccion_id, ef.EVALUACION_FRACCION_ID, ef.RESPUESTA, ef.COMENTARIO, af.estatus from EVALUACIONES_FRACCIONES ef, articulos_fracciones af where ef.articulo_fraccion_id = af.articulo_fraccion_id And EVALUACION_ID  = "+eval+" And af.Articulo_Id = "+art+" order by af.articulo_id, af.numero";
            ResultSet res = this.objconx.stm.executeQuery(query);
            ArrayList<Question> questions = new ArrayList<>();

            while (res.next()) {
                Question obj = new Question();
                obj.descripcion = res.getString(1);
                obj.articuloFraccionId = res.getInt(2);
                obj.evaluacionFraccionId = res.getInt(3);
                obj.respuesta = res.getDouble(4);
                obj.comentario = res.getString(5);
                obj.estatus = res.getInt(6);
                questions.add(obj);
            }
            
            return questions;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
