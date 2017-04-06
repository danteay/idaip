
package models;

import core.Conexion;
import java.sql.ResultSet;


public class Articulos {
    
    public Conexion objconx;
    
    public Articulos(Conexion conx){
        try{
            this.objconx = conx;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getArticulosFromEvaluacion(String eval){
        try{
            ResultSet res = this.objconx.stm.executeQuery("select 'Articulo ' || a.Articulo_Clave || ' ' || a.descripcion Articulo, a.articulo_id from Articulos a, sujetos_articulos sa, evaluaciones e Where sa.sujeto_obligado_id = e.Sujeto_Obligado_Id And a.Articulo_Id = sa.Articulo_Id And e.evaluacion_id = 7581");
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
