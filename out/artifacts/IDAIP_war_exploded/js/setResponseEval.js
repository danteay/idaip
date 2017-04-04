/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    $("#fpage").val(parseInt($("#fpage").val()));
    
    $("#sendEval").click(function(){        
        $("#mode").val("1");
        sendAction();
    });
    
    $("#saveAdvance").click(function(){
        $("#mode").val("0");
        sendAction();
    });
    
});


function checkQuestion(question){
    var self = $("[data-question="+question+"]");
    var flag = false;
    
    for(var i = 0; i < self.length; i++){
        if($(self[i]).is(':checked')){
            flag=true;
            break;
        }else{            
           if($("#coment-p-"+question).val() !== ""){
               flag=true;
           }else{
               flag=false;
           }
        }
    }
    
    return flag;
}

function sendAction(){
    var trespuestas = $("#trespuestas").val();
    var evaluacion_fraccion_id = null;
    var art_fracc_respuesta_id = null;
    var respuesta = null;        
    var query = "";
    var queryComent = "";
    var queryPoints = "";

    var tpreguntas = $("#tpreguntas").val();        

    for(var i = 0; i < trespuestas; i++){  
        evaluacion_fraccion_id = $("#"+i).attr('data-dbid-evl');
        art_fracc_respuesta_id = $("#"+i).attr('data-dbid-art');

        if($("#"+i).is(':checked')){
            respuesta = $("#"+i).val();                
            query += "UPDATE DET_EVAL_FRACCIONES SET RESPUESTA = "+respuesta+" WHERE EVALUACION_FRACCION_ID = "+evaluacion_fraccion_id+" AND ART_FRACC_RESPUESTA_ID = "+art_fracc_respuesta_id+";";
        }else{
            query += "UPDATE DET_EVAL_FRACCIONES SET RESPUESTA = 0 WHERE EVALUACION_FRACCION_ID = "+evaluacion_fraccion_id+" AND ART_FRACC_RESPUESTA_ID = "+art_fracc_respuesta_id+";";
        }
    }
    
    for(var i = 0; i < tpreguntas; i++){
        evaluacion_fraccion_id = $("#coment-p-"+i).attr('data-dbid-evl');
        
        queryComent += "UPDATE EVALUACIONES_FRACCIONES SET COMENTARIO = '"+$("#coment-p-"+i).val()+"' WHERE EVALUACION_FRACCION_ID = "+evaluacion_fraccion_id+";";
        queryPoints += "UPDATE EVALUACIONES_FRACCIONES SET RESPUESTA = "+$("#points-p-"+i).val()+" WHERE EVALUACION_FRACCION_ID = "+evaluacion_fraccion_id+";";
    }
        
    var flag = null;
    
    if($("#mode").val() === "1"){
        for(var x = 0; x < tpreguntas; x++){
            

            if(checkQuestion(x)){
                flag = true;
            }else{
                flag = false;
                break;
            }
        }
    }else if($("#mode").val() === "0"){
        flag = true;
        
    }
    

    if(flag){
        $("#fquery").val(query);   
        $("#queryComent").val(queryComent);
        $("#queryPoints").val(queryPoints);
        document.form.submit();
    }else{
        $("#alertbox").css('display','block');
    }
}