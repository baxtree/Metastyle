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
		<div id="search-panel" class="span10 offset2">
			<div class="row search-bar-label">
				<label class="span" for="form-search"><i class="icon-search"></i> Search existing micro-themes:</label>
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
					<input type="hidden" name="max" value="3" />
					<input type="hidden" name="offset" value="0" />
			    </g:form>
			</div>
		</div>
	</section>
	<section id="glance">
		<div class="container">
			<div class="row">
				<div class="span12">
					<ul class="navbar">
						<div class="navbar-inner">
					        <div class="container">
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li id="group-recent" class="active">
					                <a id="recent" href="welcome#recent">Recent</a>
					              </li>
					               <li id="group-most-views">
					                <a id="mostviews" href="proxy2">Views</a>
					              </li>
					              <li id="group-popular">
					                <a id="popular" href="proxy3">Popular</a>
					              </li>
					            </ul>
					            <g:javascript>
					            	if(document.location.hash == "#mostviews") {
					            		document.getElementById("group-recent").removeAttribute("class");
					            		document.getElementById("group-popular").removeAttribute("class");
					            		document.getElementById("group-most-views").setAttribute("class", "active");
					            	}
					            	else if(document.location.hash == "#popular") {
					            		document.getElementById("group-recent").removeAttribute("class");
					            		document.getElementById("group-most-views").removeAttribute("class");
					            		document.getElementById("group-popular").setAttribute("class", "active");
					            	}
					            </g:javascript>
					          </div>
					        </div>
					    </div>
					</ul>
				</div>
			</div>
		</div>
		<div class="template-thumbnails span12">
			<g:each var="rtempl" in="${ recent }">
				<div id="template-iframe" class="template-group span4">
					<iframe class="template-iframe" src="showSnippet/${ rtempl.id }" frameborder="0" scrolling="no"></iframe>
					<div class="statistic">
						<span class="stat-user"><i class="icon-user"></i><g:link controller="static" action="showGuestTemplate" id="${ rtempl.user.id }">${ rtempl.user.username }</g:link></span>
						<span class="stat-views"><i class="icon-eye-open"></i>${ rtempl.views }</span>
						<span class="stat-like"><g:link controller="static" action="likeTemplate" id="${ rtempl.id }"><i class="icon-star"></i></g:link>${ rtempl.likes }</span>
						<span class="see-the-page"><g:link controller="template" action="showTemplate" id="${ rtempl.id }">micro-theme</g:link></span>
					</div>
				</div>
			</g:each>
			<!-- <div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div>
			<div class="template-group span4">
				<iframe class="template-iframe" src="http://www.google.co.uk" frameborder="1" scrolling="no"></iframe>
				<div class="statistic">
					<span class="stat-user"><i class="icon-user"></i>test</span>
					<span class="stat-views"><i class="icon-eye-open"></i>10</span>
					<span class="stat-like"><g:link controller="static" action="like" id="template-id"><i class="icon-star"></i><img src="" alt="like" /></g:link></span>
				</div>
			</div> -->
		</div>
	</section>
	<section id="recommendation">
	 <div class="row-fluid">
      <div class="span4">
        <img src="">
        <h2><i class="icon-folder-open"></i>&nbsp;&nbsp;Templates</h2>
        <p>Check the most used micro-themes:</p>
        <g:each var="template" in="${ templates }">
        	<p>[${ template.format }] <g:link controller="template" action="showTemplate" id="${ template.id }">${ template.typeURI }</g:link></p>
        </g:each>
      </div>
      <div class="span4">
        <img src="">
        <h2><i class="icon-cogs"></i>&nbsp;&nbsp;Types</h2>
        <p>Popular vocabularies and types:</p>
        <g:each var="typeURI" in="${ templates.typeURI.unique() }">
        	<p><a href="${ typeURI }">${ typeURI }</a></p>
        </g:each>
      </div>
      <!-- <div class="span4">
        <img src="">
        <h2>Skeletons</h2>
        <p>Creativity starting from here:</p>
        <p><a class="template-link" href="#">Link 1</a></p>
        <p><a class="template-link" href="#">Link 2</a></p>
        <p><a class="template-link" href="#">Link 3</a></p>
      </div> -->
    </div>
	</section>
<g:render template="../templates/footer" />