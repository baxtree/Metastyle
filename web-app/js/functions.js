function generateSkeleton() {
	if(document.getElementById("playground") != null)
		document.getElementById("playground").style.display = "none";
	document.getElementById("getStyleTemplate").style.display = "none";
	document.getElementById("loadingbar").style.display = "block";
	document.forms["generateTemplate"].submit();
}

function applyCSSTemplate() {
	cssta.save();
	htmlta.save();
	var html = '<!DOCTYPE html><html lang="en"><head><style type="text/css">';
	html += $("#template_txt").val();
	html += "</style></head><body>";
	html += $("#testSnippet").val();
	html += "</body></html>";
	var ifrm = document.getElementById("preview");
	var doc;
	if(ifrm.contentWindow)
		doc = ifrm.contentWindow.document;
	else if(ifrm.contentDocument)
		doc = ifrm.contentDocument;
	doc.open();
//	alert(html);
	doc.write(html);
	doc.close();
}

function getFullPreview(){
	cssta.save();
	htmlta.save();
	var html = '<!DOCTYPE html><html lang="en"><head><style type="text/css">';
	html += $("#template_txt").val();
	html += "</style></head><body>";
	html += $("#testSnippet").val();
	html += "</body></html>";
	var ifrm = document.getElementById("preview");
	var doc;
	if(ifrm.contentWindow)
		doc = ifrm.contentWindow.document;
	else if(ifrm.contentDocument)
		doc = ifrm.contentDocument;
	doc.open();
//	alert(html);
	doc.write(html);
	var fullview = window.open(null, "_blank");
	fullview.document.write(html);
	doc.close();
	fullview.document.close();
}

function shareTemplate() {
	cssta.save();
	htmlta.save();
	if(localStorage){
		localStorage.setItem("metastyle", "set");
		var data = $("#template-generation").serializeArray();
		$.each(data, function(i, obj) {
			localStorage.setItem(obj.name, obj.value);
		});
	}
	document.forms["shareTempalte"].submit();
}

function recoverTemplate(cssta, htmlta) {
	if(localStorage){
		if(localStorage.getItem("metastyle") == "set"){
			if(document.getElementById("playground") != null){
				document.getElementById("playground").style.display = "block";
			}
			var data = $("#template-generation").serializeArray();
			$.each(data, function(i, obj) {
				$("[name='" + obj.name + "']").val(localStorage.getItem(obj.name));
				localStorage.removeItem(obj.name)
			});
			cssta.setValue(document.getElementById("template_txt").value);
			htmlta.setValue(document.getElementById("testSnippet").value);
			applyCSSTemplate();
			localStorage.setItem("metastyle", "reset");
		}
	}
}

function resetLocalStorage() {
	if(localStorage){
		localStorage.setItem("metastyle", "reset");
	}
}

function normaliseCSS() {	
	var css = cssta.getValue();
	css = css.replace(/(\/\*[^(\/\*)]*?\*\/[\t\r\n\s]*?)?\[[^{]+\][\t\r\n\s]*?{[\t\r\n\s]*?}/g, "");
	css = css.replace("/^\t\t(\r)?\n/g", ""); //this does not work
	cssta.setValue(css);
	cssta.save();
}

function saveCSS(cme) {
	cme.save();
	document.getElementById("copy").disabled = false;
	document.getElementById("trim").disabled = false;
	document.getElementById("share").disabled = false;
}

function saveHTML(cme) {
	cme.save();
	document.getElementById("apply").diabled = false;
}

function lookUpPrefix(targetURI) {
	$.get("lookUpPrefix", 
		{targetedType : targetURI},
		function(prefix) {
			$("#prefix").val(prefix);
		}, 
	"text");
}

function transform(originFormat, template, targetedType, prefix, eid, bid){
//	alert(template);
	if(originFormat == "Microdata"){
		$.post("../transformMicrodataIntoRDFaLite",
				{mcss : template, targetedType : targetedType, prefix : prefix},
				function(rcss) {
					cssta.setValue(rcss);
					cssta.save();
					$("#" + eid).hide();
					$("#" + bid).show();
					$("#snippetDiv").hide();
					$("#previewDiv").hide();
				},
		"text");
	}
	else{
		$.post("../transformRDFaLiteIntoMicrodata",
				{rcss: template, targetedType : targetedType, prefix : prefix},
				function(mcss) {
					cssta.setValue(mcss);
					cssta.save();
					$("#" + eid).hide;
					$("#" + bid).show();
					$("#snippetDiv").hide();
					$("#previewDiv").hide();
				},
		"text");
	}
}

function overrideCSS (cssStr) {
	var css = cssta.getValue();
	css = css.replace(/[\t\s]+?\!important;/g, ";");
	css = css.replace(/[\t\s]*;/g, " !important;");
	cssta.setValue(css);
	cssta.save();
}