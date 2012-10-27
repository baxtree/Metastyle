<g:render template="../templates/header" />
<g:render template="../templates/banner" />
	<section id="menu-bar">
		<div class="container">
			<div class="row">
				<div class="span6">
					<ul class="navbar navbar-inverse navbar-fixed-top">
						<div class="navbar-inner">
					        <div class="container">
					          <g:link class="brand" controller="static" action="welcome">CSSMetaSelector</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="">
					                <g:link controller="static" action="welcome">Home</g:link>
					              </li>
					              <li class="active">
					                <g:link controller="static" action="getSkeletons">Get Skeletons</g:link>
					              </li>
					              <li class="">
					                <g:link controller="user" action="showTemplates">My Templates</g:link>
					              </li>
					            </ul>
					            <g:render template="../templates/authentication" />
					          </div>
					        </div>
					    </div>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<section id="main-container">
		<div class="row">
		    <form class="offset3 form-horizontal">
				<div class="control-group">
					<label class="control-label" for="targetedType">Targeted Type:</label>
					<div class="controls">
						<input class="input-xxlarge" type="text" id="targetedType" placeholder="e.g., http://schema.org/Person, http://xmlns.com/foaf/0.1/Person ...">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="schema">Schema File (RDF):</label>
					<div class="controls">
						<input class="input-xxlarge" type="text" id="schema" placeholder="e.g., http://schema.rdfs.org/all.rdf, http://xmlns.com/foaf/spec/index.rdf ...">
					</div>
				</div>
				<p>
					<button class="offset2 btn btn-large btn-primary" type="button">Get the Style Template</button>
				</p>
			</form>
		</div>
		<div class="row offset4">
			<textarea class="span8" rows="30"></textarea>
			<button class="btn btn-primary" type="button">Copy</button>
			<button class="btn btn-primary" type="button">Share ...</button>
		</div>
	</section>
<g:render template="../templates/footer"></g:render>