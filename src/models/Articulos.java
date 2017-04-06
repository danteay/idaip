
package models;

import core.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Articulos {
    
    private Conexion objconx;

    public String articulo;
    public int articuloId;
    
    public Articulos(Conexion conx){
        try{
            this.objconx = conx;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Articulos> getArticulosFromEvaluacion(int eval){
        try{
            String query = "select 'Articulo ' || a.Articulo_Clave || ' ' || a.descripcion Articulo, a.articulo_id from Articulos a, sujetos_articulos sa, evaluaciones e Where sa.sujeto_obligado_id = e.Sujeto_Obligado_Id And a.Articulo_Id = sa.Articulo_Id And e.evaluacion_id = "+eval+" order by a.ARTICULO_ID";
            ResultSet res = this.objconx.stm.executeQuery(query);
            ArrayList<Articulos> arts = new ArrayList<>();

            while (res.next()) {
                Articulos obj = new Articulos(this.objconx);
                obj.articulo = res.getString(1);
                obj.articuloId = res.getInt(2);
                arts.add(obj);
            }
            
            return arts;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
