
package models;

import core.Conexion;
import java.sql.ResultSet;

public class Evaluaciones {
    
    public int evaluacion_id;
    public int periodo_id;
    public int sujeto_obligado_id;
    public int articulo_id;
    public String fecha_evaluacion;
    public String usuario_evalua;
    public int estatus;
    public String respuesta;
    public int tipo_evaluacion;
    public int cierre;
    public float resultado;
    
    
    public Conexion objconx;

    
    public Evaluaciones(Conexion conx){
        try{
            this.evaluacion_id = 0;
            this.periodo_id = 0;
            this.sujeto_obligado_id = 0;
            this.articulo_id = 0;
            this.fecha_evaluacion = null;
            this.usuario_evalua = null;
            this.estatus = 0;
            this.respuesta = null;
            this.tipo_evaluacion = 0;
            this.cierre = 0;
            this.resultado = 0;
            
            this.objconx = conx;            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean getItem(int id){
        try{
            String query = "SELECT * FROM EVALUACIONES WHERE EVALUACION_ID = "+id;
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            if(res != null){
                res.next();
                
                this.evaluacion_id = res.getString(1) != null ? Integer.parseInt(res.getString(1)):0;
                this.periodo_id = res.getString(2) != null ? Integer.parseInt(res.getString(2)):0;
                this.sujeto_obligado_id = res.getString(3) != null ? Integer.parseInt(res.getString(3)):0;
                this.articulo_id = res.getString(4) != null?Integer.parseInt(res.getString(4)):0;
                this.fecha_evaluacion = res.getString(5) != null ? res.getString(5):"";
                this.usuario_evalua = res.getString(6) != null ? res.getString(6):"";
                this.estatus = res.getString(7) != null ? Integer.parseInt(res.getString(7)):0;
                this.respuesta = res.getString(8) != null ? res.getString(8):"";
                this.tipo_evaluacion = res.getString(9) != null ? Integer.parseInt(res.getString(9)):0;
                this.cierre = res.getString(10) != null ? Integer.parseInt(res.getString(10)):0;
                this.resultado = res.getString(11) != null ? Float.parseFloat(res.getString(11)):0;

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
            String query = "SELECT * FROM EVALUACIONES";
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
