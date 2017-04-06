
package models;


import java.sql.ResultSet;
import java.util.ArrayList;

import core.Conexion;

public class GetResp {
    
    public Conexion objconx;
    
    public GetResp(Conexion conx){
        this.objconx = conx;
    }
    
    public ArrayList<Answer> getRespForQuestion(int eval){
        try{
            String query = "select * from ART_FRACC_RESPUESTAS where ARTICULO_FRACCION_ID = "+ eval +" order by orden";
            ResultSet res = this.objconx.stm.executeQuery(query);
            ArrayList<Answer> answers = new ArrayList<>();

            while (res.next()) {
                Answer obj = new Answer();
                obj.artFraccRespuestaId = res.getInt(1);
                obj.articuloFraccionId = res.getInt(2);
                obj.respuesta = res.getString(3);
                obj.valor = res.getDouble(4);
                obj.orden = res.getInt(5);
                obj.status = res.getInt(6);
                answers.add(obj);
            }
            
            return answers;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
