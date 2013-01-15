<g:render template="../templates/header" />
<g:javascript>
	$(document).ready(function(){
		$('button#copy').zclip({
			path:'../../js/ZeroClipboard.swf',
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
<div class="row offset3 control-group">
	<label class="control-label" for="targetedType">Targeted Type:</label>
	<span class="offset1 controls">
		<span id="targetedType">${ tem_targetedType }</span>
	</span>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="schema">Schema File:</label>
	<div class="offset1 controls">
		<span id="schema">${ tem_schema }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="prefix">Prefix:</label>
	<div class="offset1 controls">
		<span id="prefix">${ tem_prefix }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="format">Format:</label>
	<div class="offset1 controls">
		<span id="format">${ tem_format }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="views">Views:</label>
	<div class="offset1 controls">
		<span id="views">${ tem_views }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="contributor">Contributor:</label>
	<div class="offset1 controls">
		<span id="contributor">${ tem_contributor }</span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="stars">Stars:</label>
	<div class="offset1 controls">
		<span class="stat-like"><g:link controller="static" action="likeTemplate" id="${ tem_id }"><i class="icon-star"></i></g:link><span id="stars">${ tem_likes }</span></span>
	</div>
</div>
<div class="row offset3 control-group">
	<label class="control-label" for="template_txt">CSS Template Content:</label>
	<div class="controls">
		<textarea class="span9" rows="30" id="template_txt" readonly>${ template }</textarea>
		<button class="btn btn-primary" id="copy" type="button">Copy to clipboard</button>
		<button class="btn btn-primary" id="transform" type="button" onclick='transform("${ tem_format }", "${ tem_oneline }", "${ tem_targetedType }", "${ tem_prefix }", "transform", "back");'>
			<g:if test="${ tem_format == 'Microdata' }">
				Get equivalent CSS for RDFa Lite		
			</g:if>
			<g:else>
				Get equivalent CSS for Microdata
			</g:else>
		</button>
		<button class="btn btn-primary" id="back" type="button" onclick="document.location.reload(false);" style="display:none;">
			<g:if test="${ tem_format == 'Microdata' }">
				Go back to CSS for Microdata		
			</g:if>
			<g:else>
				Go back to CSS for RDFa Lite
			</g:else>
		</button>
	</div>
</div>
<div id="snippetDiv" class="row offset3 control-group">
	<label class="control-label" for="testSnippet">HTML Body Snippet:</label>
	<div class="controls">
		<textarea class="span9" rows="30" id="testSnippet" readonly>${ tem_testSnippet }</textarea>
	</div>
</div>
<div id="previewDiv" class="row offset3 control-group">
	<label class="control-label" for="preview">Style Preview:</label>
	<div class="controls preview">
		<iframe id="preview" class="" width="700" height="300" frameborder="0" title="preview" onload="applyCSSTemplate()">
		</iframe>
	</div>
</div>
</section>
<script type="text/javascript">
      var cssta = CodeMirror.fromTextArea(document.getElementById("template_txt"), {
        lineNumbers: true,
        matchBrackets: true,
        readOnly: true,
        mode: "text/css"
      });
      cssta.setSize(700, 350);
      var htmlta = CodeMirror.fromTextArea(document.getElementById("testSnippet"), {
        lineNumbers: true,
        readOnly: true,
        mode: "text/html"
      });
      htmlta.setSize(700, 230);
    </script>
<g:render template="../templates/footer"></g:render>