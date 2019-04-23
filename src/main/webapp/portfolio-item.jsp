<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Image Classifier</title>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="css/theme.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700,400italic,700italic" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">
<!-- Intro Header -->
<% String parameter = (String) request.getParameter("imageName"); 
String styleText = "width:auto;height:-webkit-fill-available;padding:100px 0 100px 0;background-image:url(images/"+parameter+".png);background-size:cover;-o-background-size:cover;";
%>
<header>
<div class="intro-body" style="<%= styleText %>">
	<div style="height: inherit;display: flex;align-items: center;justify-content: center;">
				<a href="#single-project" class="btn btn-circle page-scroll">
				<i class="fa fa-angle-double-down animated"></i>
				</a>
			</div>
		</div>
	</div>
</div>
</header>
<!-- Project Details Section -->
<section id="single-project">
<div class="container content-section text-center">
	<div class="row">
		<h2>CHOOSE CLASSIFICATION TYPE</h2>
		<div class="col-lg-8 col-lg-offset-2">
			<p>
				<a href="mostrar?imagen=<%= parameter %>" class="btnghost"><i class="fa fa-pencil"></i>Show me</a> 
				<i style="font-size:12px;margin-right:3px;"> or </i> 
				<a href="hablar?imagen=<%= parameter %>" class="btnghost"><i class="fa fa-play"></i> Tell me</a>
			</p>
			
			<p>
				<%@page import="asr.proyectoFinal.dao.Clasificado" %>
  				<%
  				Object clasificado = request.getAttribute("clasificado");
  			
  				if (clasificado != null) {
  		 		%>
  				This is a : ${clasificado.clase} <br/>
  				Precision: ${clasificado.score}
  			<%
  				}
  			%>
			</p>
			
			<audio name ="audio" autoplay preload="auto" autobuffer controls class="audio" ></audio>
			
			<a href="index.jsp" class="btn btn-circle page-scroll"  style="line-height:35px; color:rgb(51,51,51); border-top-color:rgb(51,51,51); border-bottom-color:rgb(51,51,51); border-left-color:rgb(51,51,51); border-right-color:rgb(51,51,51);">
				<i class="fa fa-angle-double-left animated"></i>
			</a>
		</div>
	</div>
</div>
</section>

<!-- Footer -->
<footer>
<div class="container text-center">
	<p class="credits">
		Copyright &copy; Image Classifier 2019<br/>
	</p>
</div>
</footer>
<!-- jQuery -->
<script src="js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<!-- Plugin JavaScript -->
<script src="js/jquery.easing.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="js/theme.js"></script>
</body>
</html>