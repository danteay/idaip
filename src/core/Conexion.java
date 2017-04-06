package core;

import java.sql.*;

public class Conexion {

    private final static String USER = "IDAIP";
    private final static String PASSWD = "idaip2014";
    private final static String URL = "jdbc:oracle:thin:@192.241.222.183:1521:XE";
    
    public Connection con;
    public Statement stm = null;
    
    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(this.URL, this.USER, this.PASSWD);
            this.stm = con.createStatement();

            System.out.println("Conexion Exitosa...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la conexion...");
        }
    }

    public void close(){
        try {
            this.stm.close();
            this.con.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Fail closing conection");
        }
    }
}
