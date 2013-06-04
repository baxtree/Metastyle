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
	<section id="main-container" class="about span8 offset2">
		<p>Metastyle, which takes a specific vocabulary in RDF as a seed and generates a skeleton for them to create a sharable micro-theme dedicated to this vocabulary. Here, micro-themes can be shared as templates and discovered via our tool. Moreover, statistics and ranking are applied here, which can make well-designed micro-themes achievable to users more than those with low qualities. With respect to embeddable data format variations, Metastyle provides one-click transformation between micro-themes created based on different formats.</p>
	</section>
<g:render template="../templates/footer" />