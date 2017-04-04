
<%@ page import="controllers.SuccessController"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="models.Evaluaciones" %>
<%@ page import="models.Articulos"%>
<%@ page import="models.SujetosObligados"%>
<%@ page import="core.NoConectionFunctions"%>
<%@ page import="core.Conexion" %>

<%
    String element = "0";
    
    try{
        if(request.getParameter("evl") != null){
            element = request.getParameter("evl");
        }else{
            response.sendRedirect("error.html");
        } 
    }catch(Exception e){
        e.printStackTrace();
        response.sendRedirect("error.html");
    }

    Conexion conx = new Conexion();
    
    Evaluaciones objevl = new Evaluaciones(conx);
    SuccessController obj = new SuccessController(conx, element);
    Articulos objart = new Articulos(conx);
    SujetosObligados objsub = new SujetosObligados(conx);
    
    NoConectionFunctions objncf = new NoConectionFunctions();
    
    objevl.getItem(Integer.parseInt(element));
    objsub.getItem(objevl.sujeto_obligado_id);
    
    String parcePercent = objncf.parsePercent(String.valueOf(objevl.resultado));    
    
    ResultSet arts = objart.getArticulosFromEvaluacion(element);
    ResultSet res = null;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Resumen</title>
        
        <link rel="stylesheet" href="css/foundation.min.css"/>
        <link rel="stylesheet" href="css/normalize.css"/>
        <link rel="stylesheet" href="css/global.css"/>
        <link type="image/vnd.microsoft.icon" rel="shortcut icon" href="img/favicon.ico">
        
        <script src="js/jquery-2.1.1.min.js"></script>
        <script src="js/foundation.min.js"></script>
        <script src="js/setPoints.js"></script>
    </head>
    <body>
        <nav class="top-bar" data-topbar role="navigation"> 
            <ul class="title-area"> 
                <li class="name"> 
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
                            <a href="#">Porcentaje: <span class="label round" id="complete"><% out.print(parcePercent); %>%</span></a>                            
                        </li>
                    </ul>
                </section>
        </nav> 
        
        <main>
            <div class="row">
                <div class="large-12 columns text-center">
                    <img src="img/logoIDAIP.jpg" style="height:150px; width:auto;">                    
                </div>
            </div>
            
            <br>
            
            <div class="row">
                
                    <%
                        if(objevl.resultado == 100){
                    %>
                        <div data-alert class="alert-box large-12 columns" id="alert1" style="background: #00BEFA; border: none; font-size: 0.9125rem;">
                             Lo felicitamos por haber logrado el cien por ciento de cumplimiento de la informaci�n m�nima de oficio, lo invitamos a seguir completando y actualizando la informaci�n en su portal de transparencia.
                            <a onclick="$('#alert1').fadeOut();" class="close">&times;</a>
                        </div>
                    <%
                        }else if(objevl.resultado < 100 && objevl.resultado >= 80){
                    %>
                        <div data-alert class="alert-box large-12 columns" id="alert2" style="background: #3BC829; border: none; font-size: 0.9125rem;">
                            Su nivel de cumplimiento es aceptable, sin embargo puede  aumentar el nivel del mismo por lo que lo invitamos a seguir completando y actualizando la informaci�n en su portal de transparencia.
                            <a onclick="$('#alert2').fadeOut();" class="close">&times;</a>
                        </div>
                    <%
                        }else if(objevl.resultado < 80 && objevl.resultado >= 60){
                    %>
                        <div data-alert class="alert-box large-12 columns" id="alert3" style="background: #F3BD24; border: none; font-size: 0.9125rem;">
                            Lo invitamos a seguir completando y actualizando la informaci�n en su portal de transparencia, evitando as� futuras sanciones por incumplimiento en la publicaci�n de la informaci�n m�nima de oficio.
                            <a onclick="$('#alert3').fadeOut();" class="close">&times;</a>
                        </div>
                    <%
                        }else if(objevl.resultado < 60){
                    %>
                        <div data-alert class="alert-box large-12 columns" id="alert4" style="background: #E73F24; border: none; font-size: 0.9125rem;">
                            Su nivel de cumplimiento es demasiado bajo, lo exhortamos para que complete y actualice la informaci�n m�nima de oficio en su portal de internet, evitando futuras sanciones.
                            <a onclick="$('#alert4').fadeOut();" class="close">&times;</a>
                        </div>
                    <%
                        }
                    %>
                
            </div>
            
            <br>
            
            <div class="row panel">
                
                <div class="large-12 columns">
                    <section class="left">
                        <h6>Resumen de verificaci�n</h6>
                    </section>

                    <section class="right">                                    
                        <% if(request.getParameter("success") != null){ %>
                            <span>Guardado <img style="width: 20px; height: auto;" src="img/check_success.svg" alt="success"></span>
                        <% }else if(request.getParameter("errorUpdate") != null){ %>
                            <span>Error al guardar <img style="width: 20px; height: auto;" src="img/check_error.svg" alt="Error"></span>                                    
                        <% } %>
                            
                            <% if(objevl.cierre == 0){ %><a class="button tiny" href="index.jsp?evl=<% out.print(""+element); %>">Continuar Verificaci�n</a><% } %>                            
                            
                    </section>
                    <hr>
                </div>
                
                <div class="large-12 columns">
                    
                    <%
                    if(arts != null){
                        while(arts.next()){
                            
                            res = obj.getSuccess(arts.getString(2));
                            
                            %>
                            <div class="row">
                                <div class="large-12 columns">
                                    <h5><% out.print(""+arts.getString(1)); %></h5>
                                    <hr>
                                </div>
                            </div>
                            <%
                            
                            if(res != null){
                                while(res.next()){
                    %>
                    
                    <div class="row">
                        <div class="large-12 columns" style='color: #008CBA'>
                            <b><% out.print(res.getString(1)+""); %></b>
                        </div>
                        <div class="large-12 columns">
                            <div class="row collapse">    
                                <div class="small-3 columns">
                                    <span class="prefix">Puntaje</span>
                                </div>
                                <div class="small-9 columns">
                                    <input type="text" value="<% out.print(res.getString(2)); %>" disabled/>
                                </div>                                
                            </div>
                        </div>
                    </div>
                    
                    <%
                                }
                            }
                        }
                    }
                    %>
                    
                </div>
            </div>
        </main>
    </body>
</html>