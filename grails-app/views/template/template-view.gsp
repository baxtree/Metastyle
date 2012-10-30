<g:render template="../templates/header" />
<g:javascript>
	$(document).ready(function(){
		$('button#copy').zclip({
			path:'../../js/ZeroClipboard.swf',
			copy:$('textarea#template').text()
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
<section id="main_content">
<div class="row offset3 control-group">
	<label class="control-label" for="targetedType">Targeted Type:</label>
	<div class="controls">
		<span id="targetedType">${ tem_targetedType }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="schema">Schema File (RDF):</label>
	<div class="controls">
		<span id="schema">${ tem_schema }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="prefix">Prefix:</label>
	<div class="controls">
		<span id="prefix">${ tem_prefix }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="format">Format:</label>
	<div class="controls">
		<span id="format">${ tem_format }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="contributor">Contributor:</label>
	<div class="controls">
		<span id="contributor">${ tem_contributor }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="template_txt">Content:</label>
	<div class="controls">
		<textarea class="span9" rows="30" id="template_txt" readonly>${ template }</textarea>
		<button class="btn btn-primary" id="copy" type="button">Copy</button>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="testSnippet">Snippet:</label>
	<div class="controls">
		<textarea class="span9" rows="30" id="testSnippet" readonly>${ tem_testSnippet }</textarea>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="preview">Preview:</label>
	<div class="controls preview">
		<iframe id="preview" class="" width="700" height="300" frameborder="0" title="preview" onload="applyCSSTemplate()">
		</iframe>
	</div>
</div>
</section>
<g:render template="../templates/footer"></g:render>