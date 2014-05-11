<g:render template="../templates/header" />
<g:render template="../templates/banner" />
	<section id="menu-bar">
		<div class="container">
			<div class="row">
				<div class="span6">
					<ul class="navbar navbar-inverse navbar-fixed-top">
						<div class="navbar-inner">
					        <div class="container">
					          <g:link class="brand" url='[controller:"static", action:"welcome"]'>Metastyle</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="">
					                <g:link controller="static" action="welcome"><i class="icon-home"></i>Home</g:link>
					              </li>
					              <li class="">
					                <g:link controller="static" action="getSkeletons"><i class="icon-wrench"></i>Get Skeletons</g:link>
					              </li>
					              <li class="">
					                <g:link controller="user" action="showTemplates"><i class="icon-folder-close"></i>My Micro-Themes</g:link>
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
		<div class="offset3">${ total } micro-theme(s) found:</div>
		<g:each var="template" in="${ templates }">
			<div class="offset4">
				<p><g:link controller="template" action="showTemplate" id="${ template.id }">${ template.typeURI }</g:link></p>
				<p>Format: ${ template.format }</p>
				<p>Contributor: ${ template.user.username } </p>
			</div>
		</g:each>
		<div class="offset5">
			<g:paginate controller="static" action="searchTemplate" next="Next &rarr;" prev="&larr; Prev" max="5" total="${ total }" params="[query: query, format: format]" />
		</div>
	</section>
<g:render template="../templates/footer"></g:render>