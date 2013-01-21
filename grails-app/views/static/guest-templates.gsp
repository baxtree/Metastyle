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
					              <li class="">
					                <g:link controller="static" action="welcome"><i class="icon-home"></i>Home</g:link>
					              </li>
					              <li class="">
					                <g:link controller="static" action="getSkeletons"><i class="icon-wrench"></i>Get Skeletons</g:link>
					              </li>
					              <li class="">
					                <g:link controller="user" action="showTemplates"><i class="icon-folder-close"></i>My Templates</g:link>
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
	<section id="main_content">
		<g:if test="${ flash.message }">
	   		<span class="warning span3">${ flash.message }</span>
	   	</g:if>	
	   	<div class="offset3">User <em>${ username }</em> has published following templates:</div>
		<g:each var="template" in="${ templates }">
				<div class="offset3"><g:link controller="template" action="showTemplate" id="${ template.id }">CSS template for ${ template.typeURI } in ${ template.format }</g:link></div>
		</g:each>
	</section>
<g:render template="../templates/footer"></g:render>
