<g:render template="../templates/header" />
<g:javascript>
	$(document).ready(function(){
		$('button#copy').zclip({
			path:'../js/ZeroClipboard.swf',
			copy:$('textarea#template_txt').text()
		});
	});
</g:javascript>
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
	    <g:form class="offset3 form-horizontal" name="generateTemplate" url="[controller: 'static', action: 'generateTemplate']">
			<div class="row control-group">
				<label class="control-label" for="targetedType">Targeted Type:</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="targetedType" name="targetedType" placeholder="e.g., http://schema.org/Person, http://xmlns.com/foaf/0.1/Person ..." value="http://xmlns.com/foaf/0.1/Person">
				</div>
			</div>
			<div class="row control-group">
				<label class="control-label" for="schema">Schema File (RDF):</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="schema" name="schema" placeholder="e.g., http://schema.rdfs.org/all.rdf, http://xmlns.com/foaf/spec/index.rdf ..." value="http://xmlns.com/foaf/spec/index.rdf">
				</div>
			</div>
			<div class="row control-group">
				<label class="control-label" for="baseURI">Base URI:</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="baseURI" name="baseURI" placeholder="e.g., http://schema.org/, http://xmlns.com/foaf/0.1/ ..." value="http://xmlns.com/foaf/0.1/">
				</div>
			</div>
			<div class="row control-group">
				<label class="control-label" for="prefix">Prefix:</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="prefix" name="prefix" placeholder="e.g., foaf (for HTML5 Microdata, this field will be ignored) ..." value="foaf">
				</div>
			</div>
			<div class="row offset1">
				<label class="radio">
					<input type="radio" name="format" id="microdata" value="Microdata" checked>
					HTML5 Microdata
				</label>
				<label class="radio">
					<input type="radio" name="format" id="rdfalite" value="RDFa Lite">
					RDFa Lite 1.1
				</label>
			</div>
			<p>
				<button class="offset6 btn btn-large btn-primary" type="submit">Get the Style Template</button>
			</p>
		</g:form>
		
		<g:if test="${ template }">
			<div class="row playground">
				<g:form class="offset2" id="template-generation" name="shareTempalte" url="[controller: 'user', action: 'shareTemplate']">
					<input type="hidden" name="tem_targetedType" value="${ tem_targetedType }">
					<input type="hidden" name="tem_schema" value="${ tem_schema }">
					<input type="hidden" name="tem_baseURI" value="${ tem_baseURI }">
					<input type="hidden" name="tem_prefix" value="${ tem_prefix }">
					<input type="hidden" name="tem_format" value="${ tem_format }">
					<button class="btn btn-primary" id="copy" type="button">Copy</button>
					<button class="btn btn-primary" type="submit">Share ...</button>
					<button class="offset6 btn btn-primary" id="apply" type="button" onclick="applyCSSTemplate()">Apply CSS</button>
					<div class="row">
						<div class="span6">
							<textarea id="template_txt" class="span8" name="tem_template" rows="30">${ template }</textarea>
						</div>
						<div class="span5 offset2">
							<textarea class="span5" id="testSnippet" name="testSnippet" rows="15" placeholder="Test Snippet Codes..."></textarea>
							<div class="preview-small">
								<iframe id="preview" class="span5" frameborder="0" title="preview"></iframe>
							</div>
						</div>
					</div>
				</g:form>
			</div>
		</g:if>
		<g:else>
		</g:else>
	</section>
<g:render template="../templates/footer"></g:render>