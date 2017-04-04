/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    //chargeInitialPoints();
    
    $("#reset").click(function(){
        resetValues();
    });
    
    $("#noportal").click(function(){
        setNoPortal();
    });
    
    $("#select-all").click(function(){
        selectAll();
    });
    
    $("#recalcular-total").click(function(){
        chargeTotal();
    });
    
    $("[id^=points-p-]").change(function(){
        chargeTotal();
    });
});

function setNoPortal(){
    var preguntas = $("#tpreguntas").val();
    
    for(var i=0; i < preguntas; i++){
        $("#coment-p-"+i).val("No publica la informacion");
    }
    
    $("#mode").val("1");
    sendAction();
}


function resetValues(){
    $(":checkbox").attr("checked",false);
    
    $("[id^=coment-p-]").val("");
    
    $("[id^=points-p-]").val("0.0");
    
    $("#complete").text("0%");
    $("#percentComplete").val(0);    
}

function selectAll(){
    $("[type=checkbox]").prop('checked', true);
    
    chargeInitialPoints();
    chargeTotal();  
}


function chargeTotal(){
    
    var totalp = $("#tpreguntas").val();
    var total  = 0;
    var pointsaux = 0;
    
    for(var i = 0; i < totalp; i++){
        pointsaux += parseFloat($("#points-p-"+i).val());
    }
    
    console.log("totalp    >> "+totalp);
    console.log("pointsaux >> "+pointsaux);
    
    total = ((pointsaux * 100) * (100 * 100) / (totalp * 100)) / 100;
    
    $("#percentComplete").val(total);
    
    if($("#percentComplete").val() > 100){
        $("#percentComplete").val(100);
    }else if($("#percentComplete").val() < 0){
        $("#percentComplete").val(0);
    }
    
    var set = truncateDecimals($("#percentComplete").val());
    
    $("#complete").text(set + " %");
    
}


function chargeInitialPoints(){
    
    var totalp = $("#tpreguntas").val();
    var content = null;
    
    var total = 0;
    var aux = 0;
    
    for(var i = 0; i < totalp; i++){
        content = $("[data-question="+i+"]");
        
        for(var j = 0; j < content.length; j++){
            if($(content[j]).is(':checked')){
                aux += parseFloat($(content[j]).val()) * 100;
            }            
        }
        
        total = aux / 100;
        
        $("#points-p-"+i).val(total);
        aux = 0;
    }
}


function setPoints(question, element){
    
    parseAction("points-p-"+question);
    
    var points = parseFloat($("#points-p-"+question).val()) * 100;
    var sum = parseFloat($("#"+element).val()) * 100;
    
    var total = 0;
    
    var flag = true;
    
    if($("#"+element).is(':checked')){   
        total = parseFloat(sum) + parseFloat(points);
        flag = true;
    }else{
        total = parseFloat(points) - parseFloat(sum);
        flag = false;
    }
    
    total = total / 100;
    
    if(total > 1){
        
        total = 1.0;
    }else if(total < 0){
        total = 0.0;
    }else if(isNaN(total)){        
        var self = $("[data-question="+question+"]");        
        total = 0;
        
        for(var i = 0; i < self.length; i++){
            if($(self[i]).is(':checked')){
                total = total + parseFloat($(self[i]).val());
            }
        }
    }
    
    $("#points-p-"+question).val(parseFloat(total));
    
    setGeneral(flag,$("#"+element).val());
    
}


function setGeneral(action, points){    
    var percent = 100 / parseInt($("#tpreguntas").val()); 
    
    var aux = parseFloat(points);
    aux = ((aux * 100) * (percent * 100) / (1 * 100)) / 100;
    
    var total = parseFloat($("#percentComplete").val());
    
    
    if(action){
        total = ((total * 100) + (aux * 100)) / 100;
    }else{
        total = ((total * 100) - (aux * 100)) / 100;
    }
    
    
    $("#percentComplete").val(total);
    
    if($("#percentComplete").val() > 100){
        $("#percentComplete").val(100);
    }else if($("#percentComplete").val() < 0){
        $("#percentComplete").val(0);
    }
    
    var set = truncateDecimals($("#percentComplete").val());
    
    $("#complete").text(set + " %");
    
}


function parseAction(element){
    if(!isNaN($("#"+element).val())){
        if(parseFloat($("#"+element).val()) >= 1){
            $("#"+element).val("1.0");
        }else if(parseFloat($("#"+element).val()) <= 0){
            $("#"+element).val("0.0");
        }
    }else if($("#"+element).val() === ""){
        $("#"+element).val("0.0");
    }else{
        $("#"+element).val("0.0");
    }
    
}


function truncateDecimals(numRecibe){
    var num = numRecibe.toString();
    var total = "";
    var cont = 0;
    var flag = false;
    
    for(var i = 0; i < num.length; i++){
        var caracter = num.charAt(i);
        
        if(caracter === "."){
            flag = true;
        }else{
            if(flag){
                cont ++;
            }
        }
        
        if(cont === 2){
            break;
        }else{
            total += caracter;
        }        
    }
    
    return total;
}
