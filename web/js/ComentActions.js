/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeComent(element){
    var question = $(element).attr("data-question");
    
    $("#coment-p-"+question).val($(element).val());
}
