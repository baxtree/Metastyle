<g:render template="../templates/header" />
<g:javascript>
$(document).ready(function(){
		$('button#copy').zclip({
			path:'../js/ZeroClipboard.swf',
			copy: function() { return cssta.getValue(); }
		});
		$('a#linkcopy').zclip({
			path:'../js/ZeroClipboard.swf',
			copy: function() { return cssta.getValue(); }
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
					          <g:link class="brand" controller="static" action="welcome">Metastyle</g:link>
					          <div class="nav-collapse collapse">
					            <ul class="nav">
					              <li class="">
					                <g:link controller="static" action="welcome"><i class="icon-home"></i>Home</g:link>
					              </li>
					              <li class="active">
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
	    <g:form id="generateTemplate" class="offset3 form-horizontal" name="generateTemplate" url="[controller: 'static', action: 'generateTemplate']">
			<div class="row control-group">
				<label class="control-label" for="targetedType">Target Type:</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="targetedType" name="targetedType" placeholder="e.g., http://schema.org/Person, http://xmlns.com/foaf/0.1/Person ..." value="" onBlur="lookUpPrefix(this.value);">
				</div>
			</div>
			<div class="row control-group">
				<label class="control-label" for="schema">Schema File (RDF):</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="schema" name="schema" placeholder="e.g., http://schema.rdfs.org/all.rdf, http://xmlns.com/foaf/spec/index.rdf ..." value="">
				</div>
			</div>
			<div class="row control-group" style="display: none;">
				<label class="control-label" for="baseURI">Base URI:</label>
				<div class="controls">
					<input class="input-xxlarge" type="hidden" id="baseURI" name="baseURI" placeholder="e.g., http://schema.org/, http://xmlns.com/foaf/0.1/ ..." value="">
				</div>
			</div>
			<div class="row control-group">
				<label class="control-label" for="prefix">Prefix:</label>
				<div class="controls">
					<input class="input-xxlarge" type="text" id="prefix" name="prefix" placeholder="e.g., schema, foaf (for HTML5 Microdata, this field will be ignored) ..." value="">
				</div>
			</div>
			<div class="row offset1 format">
				<label class="radio">
					<input type="radio" name="format" id="microdata" value="Microdata" checked>
					HTML5 Microdata
				</label>
				<label class="radio">
					<input type="radio" name="format" id="rdfalite" value="RDFa Lite">
					RDFa Lite 1.1
				</label>
			</div>
			<div class="row offset1 dsl">
				<label class="radio">
					<input type="radio" name="dsl" id="css3" value="CSS3" checked>
					CSS3
				</label>
				<label class="radio">
					<input type="radio" name="dsl" id="less" value="LESS">
					LESS
				</label>
				<label class="radio">
					<input type="radio" name="dsl" id="sass" value="SASS">
					SASS
				</label>
			</div>
			<div class="row offset1 gobtn">
				<button id="getStyleTemplate" class="btn btn-large btn-primary" type="button" onclick="generateSkeleton();">Get the Micro-Theme Skeleton</button>
				<g:if test="${ flash.message }">
	   				<span class="warning span3">${ flash.message }</span>
	   			</g:if>	
				<div id="loadingbar" class="span4 offset2 progress progress-info progress-striped active" style="display: none;">
    				<div class="bar" style="width: 100%"></div>
    			</div>
			</div>
		</g:form>
			<div id="playground" class="row playground">
				<g:form class="offset2" id="template-generation" name="shareTempalte" url="[controller: 'user', action: 'shareTemplate']">
					<input type="hidden" name="tem_targetedType" value="${ tem_targetedType }">
					<input type="hidden" name="tem_schema" value="${ tem_schema }">
					<input type="hidden" name="tem_baseURI" value="${ tem_baseURI }">
					<input type="hidden" name="tem_prefix" value="${ tem_prefix }">
					<input type="hidden" name="tem_format" value="${ tem_format }">
					<button class="btn btn-primary" id="copy" type="button">Copy to clipboard</button>
					<!-- <button class="btn btn-primary" id="trim" type="button" onclick="normaliseCSS();">Reduce size</button>
					<button class="btn btn-primary" id="important" type="button" onclick="overrideCSS();">Override</button>
					<button class="btn btn-primary" id="share" type="button" onclick="shareTemplate();">Share ...</button> -->
					<div id="options" class="dropdown">	
						<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-cog"></i>Options</a>
						<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
							<li><a id="linkcopy">Copy to clipboard</a></li>
							<li><a href="#" onclick="normaliseCSS();return false;">Reduce size</a></li>
							<li><a href="#" onclick="overrideCSS();return false;">Override</a></li>
							<!-- <li><a href="#" id="applyCSSbtn" onclick="applyCSSTemplate();return false;">Apply CSS</a></li> -->
							<li><a href="" id="applyCSSbtn">Apply CSS</a></li>
							<li><a href="#" onclick="getFullPreview();return false;">Full Preview</a></li>
							<!-- <li><a href="#" onclick="shareTemplate();return false;">Share ...</a></li> -->
							<li><a id="shareTemplatebtn">Share ...</a></li>							
						</ul>
					</div>
					<!--  <button class="offset4 btn btn-primary" id="savehtml" type="button" onclick="saveToTextarea('htmlta');">Save</button> -->
 					<!--  <button class="offset5 btn btn-primary" id="apply" type="button" onclick="applyCSSTemplate();">Apply CSS</button> -->
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
			<script type="text/javascript">
		      var cssta = CodeMirror.fromTextArea(document.getElementById("template_txt"), {
		        lineNumbers: true,
		        matchBrackets: true,
		        mode: "text/css"
		      });
		      cssta.setSize(630, 500);
		      var htmlta = CodeMirror.fromTextArea(document.getElementById("testSnippet"), {
		        lineNumbers: true,
		        mode: "text/html"
		      });
		      htmlta.setSize(380, 310);
		      recoverTemplate(cssta, htmlta);
		    </script>
		   	<g:if test="${ template }">
		   		<g:javascript>
		   			document.getElementById("playground").style.display = "block";
		   		</g:javascript>
		   	</g:if>
		   	<g:else>
		   		<g:javascript>
		   			document.getElementById("playground").style.display = "none";
		   		</g:javascript>
		   	</g:else>
	</section>
<g:render template="../templates/footer"></g:render>