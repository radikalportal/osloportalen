<#import "masterTemplate.ftl" as layout /> <@layout.masterTemplate
title="Forside">
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container">
	</div>
</div>

<div class="main_content">
<div class="container">
	<h1>Velkommen</h1>
	<p></p>
	<div class="well col-lg-5">
		<h2>Interesseprofil</h2>
		<form class="form" action="/upload_questionaire_results" method="POST"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="csv_response">Last opp en CSV fil fra <a
					href="http://surveymonkey.com" target="new">SurveyMonkey</a></label> <input
					type="file" name="csv_response" />
			</div>
			<a class="btn btn-primary" href="/interests">Prosessér interesser</a>
		</form>
	</div>

	<div class="col-lg-1">
	</div>
	<div class="well col-lg-5">
		<h2>Preferanseprofil</h2>
		<form class="form" action="/upload_questionaire_results" method="POST"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="csv_response">Last opp en CSV fil fra <a
					href="http://surveymonkey.com" target="new">SurveyMonkey</a></label> <input
					type="file" name="csv_response" />
			</div>
			<a class="btn btn-primary" href="/preferences">Prosessér preferanser</a>
		</form>
	</div>

	<hr />
	
	
</div>
</div>
</@layout.masterTemplate>
