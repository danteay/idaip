
package models;

import java.sql.*;
import core.Conexion;

public class SujetosObligados {
    
    public int sujetoObligadoId;
    public String titular;
    public String enlace;
    public String direccion;
    public String telefonos;
    public String portalInternet;
    public String correWeb;
    public int tipoSujetoIdObligado;
    public String sujeto;
    
    public Conexion objconx;
    
    public SujetosObligados(Conexion conx){
        this.sujetoObligadoId = 0;
        this.titular = null;
        this.enlace = null;
        this.direccion = null;
        this.telefonos = null;
        this.portalInternet = null;
        this.correWeb = null;
        this.tipoSujetoIdObligado = 0;
        this.sujeto = null;
        
        this.objconx = conx;
    }
    
    public ResultSet getAllItems(){
        try{
            String query = "SELECT * FROM SUJETOS_OBLIGADOS";
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            return res;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean getItem(int id){
        try{
            String query = "SELECT * FROM SUJETOS_OBLIGADOS WHERE SUJETO_OBLIGADO_ID = "+id;
            ResultSet res = this.objconx.stm.executeQuery(query);
            
            if(res != null){
                res.next();
                
                this.sujetoObligadoId = Integer.parseInt(res.getString(1));
                this.titular = res.getString(2);
                this.enlace = res.getString(3);
                this.direccion = res.getString(4);
                this.telefonos = res.getString(5);
                this.portalInternet = res.getString(6);
                this.correWeb = res.getString(7);
                this.tipoSujetoIdObligado = Integer.parseInt(res.getString(8));
                this.sujeto = res.getString(9);

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
}
