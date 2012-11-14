<g:render template="../templates/header" />
<g:render template="../templates/banner" />
	<section id="menu-bar">
		<div class="container">
			<div class="row">
				<div class="span6">
					<ul class="navbar navbar-inverse navbar-fixed-top">
						<div class="navbar-inner">
					        <div class="container">
					          <g:link class="brand" controller="static" action="welcome">Metastyle</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="active">
					                <g:link controller="static" action="welcome">Home</g:link>
					              </li>
					              <li class="">
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
		<div id="search-panel" class="span10 offset2">
			<div class="row search-bar-label">
				<label class="span" for="form-search">Search existing templates:</label>
			</div>
			<div class="row">
			    <g:form id="form-search" class="form-search offset1" url="[controller: 'static', action: 'searchTemplate']">
			    	<div class="input-append search-bar">
			    		<input type="text" cols="100" name="query" class="input-medium search-query input-xxlarge" placeholder=".e.g, Person, schema.org, FOAF, http://schema.org/Person, http://xmlns.com/foaf/0.1/Person ..." />
			    		<button type="submit" class="btn">Search</button>
			    	</div>
			    	<div class="row offset2 format-radio">
						<label class="radio">
							<input type="radio" name="format" id="microdata" value="Microdata" checked>
							HTML5 Microdata
						</label>
						<label class="radio">
							<input type="radio" name="format" id="rdfalite" value="RDFa Lite">
							RDFa Lite 1.1
						</label>
					</div>
			    </g:form>
			</div>
		</div>
	</section>
	<section id="recommendation">
	 <div class="row-fluid">
      <div class="span4">
        <img src="">
        <h2>Templates</h2>
        <p>Check the most used templates:</p>
        <g:each var="template" in="${ templates }">
        	<p>[${ template.format }] <g:link controller="template" action="showTemplate" id="${ template.id }">${ template.typeURI }</g:link></p>
        </g:each>
      </div>
      <div class="span4">
        <img src="">
        <h2>Types</h2>
        <p>Popular vocabularies and types:</p>
        <g:each var="typeURI" in="${ templates.typeURI.unique() }">
        	<p><a href="${ typeURI }">${ typeURI }</a></p>
        </g:each>
      </div>
      <div class="span4">
        <img src="">
        <h2>Skeletons</h2>
        <p>Creativity starting from here:</p>
        <p><a class="template-link" href="#">Link 1</a></p>
        <p><a class="template-link" href="#">Link 2</a></p>
        <p><a class="template-link" href="#">Link 3</a></p>
      </div>
    </div>
	</section>
<g:render template="../templates/footer" />