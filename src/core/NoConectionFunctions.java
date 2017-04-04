/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author DANTE
 */
public class NoConectionFunctions {
    
    public String parsePercent(String percent){
        char[] aux = percent.toCharArray();
        String total = "";
        boolean flag = false;
        int cont = 0;
        
        for(int i = 0; i < aux.length; i++){
            if(aux[i] == '.'){
                flag = true;
                total += aux[i];
            }else{
                
                if(flag){
                    if(cont == 1){
                        break;
                    }else{
                        cont++;
                    }
                }
                
                total += aux[i];
            }
        }
        
        return total;
    }
    
}
