<#import "masterTemplate.ftl" as layout /> <@layout.masterTemplate
title="Forside">
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron small-jumbo">
	<div class="container">
	</div>
</div>

<div class="container">
	<h1>Preferanser</h1>
	<div class="well">
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
		<h1>Respondenter</h1>

		<div class="col-sm-12">
			<#list scorecards as scorecard>
			<div class="row">
				<h3>${scorecard.respondent.email}</h3>

				<div class="col-lg-12"></div>
				<!-- /col-lg-4 col-sm-6 -->
			</div>
			<hr />
			<!-- /row -->
			</#list>

		</div>
	</div>
	<hr />
	<div class="row">
		<h2>Oversikt</h2>
		<div class="col-sm-12">
			<canvas id="myChart" width="800" height="600"></canvas>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {

		// $('.table-template').DataTable();
		console.log("I'm ready....");
		console.log("Requesting data from server...");
		initializeChart();

	});

	function initializeChart() {

		var data = {
			labels : ["A","A","A","A","A","A","A","A","A"],

			datasets : [ 
			<#list scorecards as scorecard>
			{
				label : "${scorecard.respondent.email}",
				fillColor : "rgba(220,220,220,0.2)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : [
					<#list scorecard.preferenceArksChosen as ark>
				         ${ ark.score },
					</#list>
				        ]
			},
			</#list>
			]
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
