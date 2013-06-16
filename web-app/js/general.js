$(document).ready(function() {
	
	var parser = new less.Parser({});
	
	$("#applyCSSbtn").click(function() {
		cssta.save();
		htmlta.save();
		var html = '<!DOCTYPE html><html lang="en"><head><style type="text/css">';
		parser.parse($("#template_txt").val(), function (err, root) {
			if (err) {alert(err);}
			else {
				html += root.toCSS();
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
//				alert(html);
				doc.write(html);
				doc.close();
			}
			return false;
		});
		return false;
	});
	
	$("#shareTemplatebtn").click(function() {
		parser.parse($("#template_txt").val(), function (err, root) {
			$("#template_txt").val(root.toCSS());
			cssta.setValue($("#template_txt").val());
			cssta.save();
			htmlta.setValue($("#testSnippet").val());
			htmlta.save();
			if(localStorage){
				localStorage.setItem("metastyle", "set");
				var data = $("#template-generation").serializeArray();
				$.each(data, function(i, obj) {
					localStorage.setItem(obj.name, obj.value);
				});
			}
			alert("submitting");
			document.forms["shareTempalte"].submit();
			return false;
		});
		return false;
	});
});