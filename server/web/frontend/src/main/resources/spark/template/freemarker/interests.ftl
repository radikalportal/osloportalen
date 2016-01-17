<#import "masterTemplate.ftl" as layout /> <@layout.masterTemplate
title="Forside">
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron small-jumbo">
	<div class="container">
	</div>
</div>

<div class="main_content">
<div class="container">
	<h1>Interesseprofil</h1>
	<div class="well">
		<h3>Last opp en undersøkelse</h3>
		<form class="form" action="/upload_questionaire_results" method="POST"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="csv_response">Last opp en CSV fil fra <a
					href="http://surveymonkey.com" target="new">SurveyMonkey</a></label> <input
					type="file" name="csv_response" />
			</div>
			<button class="btn btn-primary">Start prosessering</button>
		</form>
	</div>
	<hr />
	
	<div class="row">
		<h1>Resultat</h1>
	
		<div class="col-sm-12">
			<#list scorecards as scorecard>
			<div class="row">
				<h3>${scorecard.respondent.email}</h3>
				
				<div class="col-lg-12">
					
				</div>
				
				<#list scorecard.arks as ark>
				<#assign colour_rgb = "000000">
				<#if ark.name == "Praktisk type">
					<#assign colour_rgb = "ffc501">
					<#assign colour_rgb_border = "f7a70d">
				</#if>
				<#if ark.name == "Utforskende type">
					<#assign colour_rgb = "fe5500">
					<#assign colour_rgb_border = "e5330d">
				</#if>
				<#if ark.name == "Kunstnerisk type">
					<#assign colour_rgb = "6da4f3">
					<#assign colour_rgb_border = "4f80df">
				</#if>
				<#if ark.name == "Sosial type">
					<#assign colour_rgb = "c908a3">
					<#assign colour_rgb_border = "9b2979">
				</#if>
				<#if ark.name == "Foretaksom type">
					<#assign colour_rgb = "0b5efa">
					<#assign colour_rgb_border = "0735d7">
				</#if>
				<#if ark.name == "Ordens type">
					<#assign colour_rgb = "5db221">
					<#assign colour_rgb_border = "548e37">
				</#if>
				
				<div class="col-lg-4 col-sm-6">
					<table class="table table-bordered table-condensed immutare_ark_table">
						<tr>
							<td colspan="2" bgcolor="#${ colour_rgb }" class="column_ark_header" style="border-bottom: 4px solid #${ colour_rgb_border }; border-left: 2px solid #${ colour_rgb }; border-right: 2px solid #${ colour_rgb }; border-top: 2px solid #${ colour_rgb };"><h4>${ ark.name } (${ ark.shortName })</h4></td>
						</tr>
						<#list ark.groups as group>
						<tr class="immutare_row_data" style="border-bottom: 0px; border-left: 0px; border-right: 0px; border-top: 0px;">
							<td style="text-align:center">${ group.groupName }</td>
							<td bgcolor="#f2f4df" style="text-align:center" class="column_value">${ group.groupScore }</td>
						</tr>
						</#list>
						<tr style="border-bottom: 4px solid #CECECE; border-left: 1px solid #CECECE; border-right: 1px solid #CECECE; border-top: 1px solid #CECECE;">
							<td style="text-align:center;">SUM</td>
							<td bgcolor="#f2f4df" style="text-align:center" class="column_value">${ ark.score }</td>
					</table>
				</div>
				<!-- /col-lg-4 col-sm-6 -->
				</#list>
			</div>
			<hr/>
			<!-- /row -->
			</#list>
			
		</div>
	</div>
	<hr/>	
	<div class="row">
		<h2>Oversikt</h2>
		<div class="col-sm-12">
			<canvas id="myChart" width="800" height="600"></canvas>
		</div>
	</div>
</div>
</div>
<script>
	$(document).ready(function() {
		
		// $('.table-template').DataTable();
		console.log("I'm ready....");
		console.log("Requesting data from server...");
		var headerForArks = [];
		var contestants = 0;
		$.ajax({
			contentType : "application/json;charset=utf-8",
			url : "http://localhost:4567/dummy",
			success : function(data) {
				console.log("Got data yo: " + data[0].respondent.email);
				contestants = data.length;
				for ( var i = 0; i < data.length; i++) {
					console.log(data[i].respondent.email);
					for ( var arkCount = 0; arkCount < data[i].arks.length; arkCount++) {
						console.log("Ark: " + data[i].arks[arkCount].name +  ", Score: " + data[i].arks[arkCount].score + "");
						headerForArks.push(data[i].arks[arkCount].name);
					}
				}
				initializeChart(headerForArks, data);
			},
			dataType : "json"
		});

	});

	function initializeChart(headerForArks, data) {
		console.log("Initializing chart..." + data);
		console
				.log("Converting personality result to a dataformat that is recognized by Chart.js");

		
		for ( var i = 0; i < data.length; i++) {
			console.log(data[i].respondent.email);
			for ( var arkCount = 0; arkCount < data[i].arks.length; arkCount++) {
				console.log("Ark: " + data[i].arks[arkCount].name +  ", Score: " + data[i].arks[arkCount].score + "");
				headerForArks.push(data[i].arks[arkCount].name);
				
			}
		}

		var data = {
			labels : headerForArks,
			
			datasets : [ {
				label : "Britt",
				fillColor : "rgba(220,220,220,0.2)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : [ 65, 59, 90, 81, 56, 55, 40 ]
			}, {
				label : "Tore",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 28, 48, 40, 19, 96, 27, 100 ]
			} ]
		};

		var ctx = document.getElementById("myChart").getContext("2d");
		var options = {

			//Number - Pixel width of dataset stroke
			datasetStrokeWidth : 2,

			//Boolean - Whether to fill the dataset with a colour
			datasetFill : true,

		};
		var myNewChart = new Chart(ctx).Radar(data, options);

	}
</script>

</@layout.masterTemplate>
