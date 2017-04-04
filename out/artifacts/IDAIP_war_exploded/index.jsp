
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="models.GetResp"%>
<%@ page import="models.SujetosObligados"%>
<%@ page import="models.HelperFunctions"%>
<%@ page import="models.Evaluaciones"%>
<%@ page import="models.Articulos"%>
<%@ page import="models.GetRestrictForChoise" %>
<%@ page import="models.Insidencias"%>
<%@ page import="core.NoConectionFunctions"%>
<%@ page import="core.Conexion" %>


<%
    String eval = "0";
    Conexion conx = null;
    try{
        if(request.getParameter("evl") != null){
            eval = request.getParameter("evl");
        }else{
            response.sendRedirect("error.html");
        }
        conx = new Conexion();
    }catch(Exception e){
        e.printStackTrace();
        response.sendRedirect("error.html");
    }    

    NoConectionFunctions objncf = new NoConectionFunctions();

    Evaluaciones objevl = new Evaluaciones(conx);
    SujetosObligados objsub = new SujetosObligados(conx);
    HelperFunctions objhelp = new HelperFunctions(conx);
    GetResp objrep = new GetResp(conx);
    Articulos objart = new Articulos(conx);
    GetRestrictForChoise objgrc = new GetRestrictForChoise(conx);
    Insidencias objinc = new Insidencias(conx);

    ResultSet arts = objart.getArticulosFromEvaluacion(eval);
    ResultSet getInc = objinc.getAllItems();

    objevl.getItem(Integer.parseInt(eval));
    objsub.getItem(objevl.sujeto_obligado_id);

    int trespuestas = 0;
    int tpreguntas = 0;
    boolean setMode = false;
    boolean flag = false;
    String stat = "";
    String evaluacionFraccionId = "";
    String artFraccionEvaluacionId="";
    
    if(objevl.cierre == 1){
        stat = "disabled";            
    }
    
    String comentSelect = "<select "+stat+" onchange='changeComent(this)' id='select-coment' data-question='{{question}}'><option value=''>Seleccione un comentario predefinido</option>";

    if(getInc != null){
        while(getInc.next()){
            comentSelect += "<option value='"+ getInc.getString(2) +"'>"+ getInc.getString(2) +"</option>";                 
        }
    }

    comentSelect += "</select>";

    if(objevl.tipo_evaluacion == 1){
        setMode = true;
    }

    String option = "";
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Evaluaciones</title>
        
        <link rel="stylesheet" href="css/foundation.min.css"/>
        <link rel="stylesheet" href="css/normalize.css"/>
        <link rel="stylesheet" href="css/global.css"/>
        <link type="image/vnd.microsoft.icon" rel="shortcut icon" href="img/favicon.ico">
        
        <script src="js/jquery-2.1.1.min.js"></script>
        <script src="js/foundation.min.js"></script>
        <script src="js/setPoints.js"></script>
    </head>
    <body>
        
        <div class="fixed">
            <nav class="top-bar" data-topbar role="navigation"> 
                <ul class="title-area">
                    <li class="name hide-for-small"> 
                        <h1><a href="#">IDAIP</a></h1> 
                    </li>
                    
                </ul>
                
                <section class="top-bar-section">                    
                    
                    <ul class="right hide-for-small">
                        <li>
                            <a target="_blank" href="<% out.print(objsub.portalInternet); %>" style="font-weight: bold; font-size: 0.9125rem;"><% out.print(objsub.sujeto+" - "+objsub.portalInternet); %></a>                            
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">Porcentaje: <span class="label round" id="complete"><% out.print(""+objncf.parsePercent(String.valueOf(objevl.resultado))); %>%</span></a>                            
                        </li>
                        <% if(objevl.cierre == 1){ %><li class="active"><a href="OpenEval?evl=<% out.print(eval); %>">Reabrir Verificaci�n</a></li> <% } %>    
                        
                        <% if(objevl.cierre == 0){ %>
                        <li class="active has-dropdown">
                            <a href="#">Acciones</a>
                            
                            <ul class="dropdown">
                                <li class="active"><a href="#" id="select-all">Seleccionar todo</a></li>
                                <li class="active"><a href="#" id="reset">Limpiar verificacion</a></li>
                                <li class="active"><a href="#" id="noportal">No publica portal de internet</a></li>
                                <li class="active"><a href="#" id="recalcular-total">Recalcular total</a></li>
                            </ul>
                            
                        </li>
                        <% } %>
                    </ul>
                    
                </section>
            </nav> 
            
                        <div class="alert-box warning" style="display: none;" id="alertbox">
                            Si alguno de los reactivos quedara sin una o varias respuestas seleccionadas, asegurese de generar un comentario.<br>
                            Porfavor rectifique los reactivos e intente guardar nuevamente.
                            <a onclick="$('#alertbox').css('display','none')" class="close">&times;</a>
                        </div>
        </div>
                        
        <main>
            
            <div class="row">
                <div class="large-12 columns text-center">
                    <img src="img/logoIDAIP.jpg" style="height:150px; width:auto;">                    
                </div>
            </div>
            
            <br>
            <br>
            
            <div class="row">
                <div class="large-12 columns">
                    <div class="panel" style="overflow:hidden">
                        <h5>Datos generales</h5>
                        <hr>
                        
                        <p><b>Sujeto obligado:</b> <% out.print(objsub.sujeto); %></p>
                        <p><b>Portal de internet:</b> <% out.println(objsub.portalInternet); %></p>
                        <p><b>Fecha:</b> <%
                            if(objevl.fecha_evaluacion != null){
                                out.print(objevl.fecha_evaluacion);                            
                            }else{
                                Date now = new Date();
                                Calendar cal = Calendar.getInstance();
                                DateFormat df = DateFormat.getDateInstance();
                                
                                cal.setTime(now);
                                cal.add(Calendar.DAY_OF_YEAR, -1);
                                
                                String date = df.format(cal.getTime());

                                out.print(date);
                            }
                        %></p>
                    </div>                    
                </div>
            </div>
                        
            <div class="row">
                <div class="large-12 columns">
                    <div class="panel" style="overflow: hidden">                         
                        
                        <%
                            if(arts != null){
                                while(arts.next()){
                                    
                        %>
                        
                        <div class="row">
                            <div class="large-12 columns">
                                <%
                                    out.print("<h5>"+ arts.getString(1) +"</h5>");
                                    out.print("<hr>");
                                %>
                            </div>
                        </div>                        
                        
                        <div class='row'>
                            <div class='large-12 columns'>
                                <%                                              
                                    ResultSet quest = objhelp.getQuestiosForEvaluation(eval, arts.getString(2));
                                            
                                    while(quest.next()){ 
                                        
                                        int estatQuest = quest.getString(6) != null ? Integer.parseInt(quest.getString(6)) : 0;
                                        
                                        if(estatQuest == 1){
                                        
                                            String comentario = quest.getString(5) != null ? quest.getString(5):"";
                                            float points = quest.getString(4) != null ? Float.parseFloat(quest.getString(4)): 0;
                                        
                                %>
                                    <div class='row'>
                                        <div class='large-12 columns'>
                                <%            
                                            out.println("<b style='color: #008CBA'>" + quest.getString(1) + "</b> <br>");

                                            ResultSet resp = objrep.getRespForQuestion(Integer.parseInt(quest.getString(2)));
                                            
                                            if(resp != null){
                                                while(resp.next()){
                                                    
                                                    int estatusAux = resp.getString(6) != null ? Integer.parseInt(resp.getString(6)) : 0;
                                                    
                                                    if(estatusAux == 1){
                                                        option = "";

                                                        if(objgrc.checkStat(quest.getString(3), resp.getString(1))){                                                        
                                                            option = "checked";
                                                        }

                                                        if(resp.getString(3) != null){
                                                            out.println("<label for='" + trespuestas + "'><input "+option+" data-dbid-evl='"+ quest.getString(3) +"' data-dbid-art='"+resp.getString(1)+"' onclick='setPoints($(this).attr(\"data-question\"),$(this).attr(\"id\"))' data-question='" + tpreguntas + "' type='checkbox' value='" + resp.getString(4) + "' id='"+ trespuestas +"' "+stat+"> " + resp.getString(3) + "</label>");

                                                            trespuestas++;
                                                        }
                                                    
                                                    }
                                                }
                                            }
                                            
                                %>
                                            
                                        </div>
                                    </div>
                                            
                                    <div class='row'>
                                        <div class='large-6 columns'>
                                                
                                <%
                                                
                                            out.println("<label for='coment-p-"+tpreguntas+"'>Comentario</label>");
                                            out.println("<textarea "+stat+" id='coment-p-"+tpreguntas+"' data-dbid-evl='"+ quest.getString(3) +"'>"+comentario+"</textarea>");
                                
                                %>
                                            
                                        </div>                                          
                                        <div class='large-6 columns'>
                                                
                                <%
                                            String aux = comentSelect.replace("{{question}}",""+tpreguntas);
                                            
                                            out.println("<label>Comentarios Incidentes</label>");
                                            out.print(aux); 
                                
                                %>
                                
                                           
                                        </div>
                                        <div class="large-12 columns">
                                            <br>
                                            <div class="row">
                                                <div class="large-1 columns">
                                                    <% out.println("<label for='points-p-"+tpreguntas+"' class='right inline'>Puntaje</label>"); %>
                                                </div>
                                                <div class="large-1 columns">
                                                    <%  out.println("<input "+stat+" data-dbid-evl='"+ quest.getString(3) +"' onchange='parseAction($(this).attr(\"id\"))' type='text' id='points-p-"+tpreguntas+"' value='"+points+"'>"); %>
                                                </div>
                                                <div class="large-10 columns"></div>
                                            </div>
                                            <br>
                                        </div>
                                    </div>
                                            
                                <%
                                                    tpreguntas++;
                                                }
                                            }
                                        }
                                    }
                                %>    
                            </div>
                        </div>
                        
                            <input type="hidden" id="trespuestas" value="<% out.print(trespuestas+""); %>">    
                            <input type="hidden" id="tpreguntas" value="<% out.print(tpreguntas+""); %>">
                            
                            <div class="row <% if(objevl.cierre == 1){ out.print("hide"); } %>">
                                <div class="large-12 columns text-center">
                                    <br>
                                    
                                    <input class="button tiny pull-right <% if(!setMode){ out.print("hide"); } %>" type="button" id="saveAdvance" value="Guardar Cambios">
                                    <input class="button tiny pull-right" type="button" id="sendEval" value="Guardar y Finalizar">
                                </div>
                            </div>
                    </div>
                </div>
            </div>
                    
                    <form action="SetResponseEval" method="post" id="form" name="form" >
                        <input type="hidden" name="query" id="fquery" value="">
                        <input type="hidden" name="queryComent" id="queryComent" value="">
                        <input type="hidden" name="queryPoints" id="queryPoints" value="">
                        <input type="hidden" name="pageSend" id="fpage" value="<% out.println(eval); %>">
                        <input type="hidden" name="mode" id="mode" value="">
                        <input type="hidden" name="percent" id="percentComplete" value="<% out.print(""+objevl.resultado); %>">
                        
                    </form>
                                
        </main>
        
        <footer></footer>
        
        <script src="js/setResponseEval.js"></script>
        <script src="js/ComentActions.js"></script>
        
        <script src="js/foundation/foundation.topbar.js"></script>
        
        <script>
            $(document).foundation();
        </script>
        
        
    </body>
</html>
