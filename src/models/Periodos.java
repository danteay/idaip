
package models;

import java.sql.ResultSet;
import core.Conexion;

public class Periodos {
    
    public int periodoId;
    public String periodoClave;
    public String descripcion;
    public int tipo;
    public int annio;
    
    public Conexion objconx;
    
    public Periodos(Conexion conx){
        this.periodoId = 0;
        this.periodoClave = null;
        this.descripcion = null;
        this.tipo = 0;
        this.annio = 0;
        
        this.objconx = conx;
    }
    
    public ResultSet getAllItems() {
        try {
            String query = "SELECT * FROM PERIODOS";
            ResultSet res = this.objconx.stm.executeQuery(query);

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
