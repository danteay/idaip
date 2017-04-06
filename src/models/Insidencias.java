
package models;

import core.Conexion;
import java.sql.ResultSet;

public class Insidencias {
    
    private int incidencia_id;
    private String descripcion;
    
    private Conexion conx;

    public Insidencias(Conexion conx){
        try{
            this.incidencia_id = 0;
            this.descripcion = null;

            this.conx = conx;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean getItem(String id){
        try{
            String query = "SELECT * FROM INCIDENCIAS WHERE INCIDENCIA_ID = "+id;
            ResultSet res = this.conx.stm.executeQuery(query);
            
            if(res != null){
                this.incidencia_id = res.getString(1) != null ? Integer.parseInt(res.getString(1)):0;
                this.descripcion = res.getString(2) != null ? res.getString(2):"";

                res.close();
                return true;
            }else{
                res.close();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public ResultSet getAllItems(){
        try{
            String query = "SELECT * FROM INCIDENCIAS";
            ResultSet res = this.conx.stm.executeQuery(query);
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
