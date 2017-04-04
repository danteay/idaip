
package models;


import java.sql.ResultSet;
import core.Conexion;

public class GetResp {
    
    public Conexion objconx;
    
    public GetResp(Conexion conx){
        this.objconx = conx;
    }
    
    public ResultSet getRespForQuestion(int eval){
        try{
            String query = "select * from ART_FRACC_RESPUESTAS where ARTICULO_FRACCION_ID = "+ eval +" order by orden";
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
