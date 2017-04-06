
package models;


import core.Conexion;
import java.sql.ResultSet;

public class GetRestrictForChoise {
    
    public Conexion conx;
    
    public GetRestrictForChoise(Conexion conx){
        try{
            this.conx = conx;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean checkStat(String evaluacionFraccionId, String artFraccionRespuestaId){
        try{
            String query = "SELECT * FROM DET_EVAL_FRACCIONES WHERE EVALUACION_FRACCION_ID = "+evaluacionFraccionId+" AND ART_FRACC_RESPUESTA_ID = "+artFraccionRespuestaId;
            
            ResultSet res = this.conx.stm.executeQuery(query);
            
            if(res != null){
                res.next();
                
                float value = Float.parseFloat(res.getString(4));
                res.close();
                return value > 0;
            }else{
                res.close();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
