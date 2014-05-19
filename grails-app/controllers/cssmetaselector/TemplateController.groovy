package cssmetaselector

import java.util.regex.Matcher
import java.util.regex.Pattern

class TemplateController {

	def scaffold = Template
	
	def showTemplate = {
		def template = Template.get(params.id)
		def oneline = template.cssTemplate.toString().replaceAll("\r\n", "\\\\r\\\\n").replace("\"", "\\\"").replace("\'", "\\\'")
		template.views = template.views + 1
		if(!template.save(flush: true)){
			template.errors.each{print it}	
		}
		render(view: "template-view", model: [
			template: template.cssTemplate,
			tem_id: params.id,
			tem_oneline: oneline,
			tem_targetedType: template.typeURI,
			tem_schema: template.contextURL,
			tem_format: template.format,
			tem_prefix: template.prefix,
			tem_contributor: template.user.username,
			tem_views: template.views,
			tem_likes: template.likes,
			tem_testSnippet: template.testSnippet])	
	}

	def transformMicrodataIntoRDFaLite = {
		def mCSSStr = params.mcss
		def prefix = params.prefix
		def targetedType = params.targetedType
		def baseURI = ""
		def typeName = ""
		if(targetedType.indexOf("#") != -1){
			baseURI = targetedType.split("#")[0] + "#"
			typeName = targetedType.split("#")[1]
		}
		else{
			def temp = targetedType.split("/") as List
			typeName = temp.pop()
			baseURI = temp.join("/") + "/"
		}
		def rCSSStr = ""
		rCSSStr = mCSSStr.replaceAll("\\[itemscope\\]\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\]" + "[\\t\\r\\n]*?,[\\t\\r\\n]*?" + "\\[itemscope\\][^\\[]+?\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\]" 
			, "[typeof=\""+ targetedType +"\"],\r\n\t\t[typeof=\"" + prefix + ":" + typeName + "\"],\r\n\t\t[typeof=\"" + typeName + "\"]")
		Pattern pat = Pattern.compile('\\[itemscope\\]\\[itemtype=[\\\"\\\']http[^\\\"\\\']+?[\\\"\\\']\\]\\[itemprop=[\\\"\\\']([^\\\"\\\']+?)[\\\"\\\']\\]');
		Matcher m = pat.matcher(mCSSStr)
		while(m.find()){
			def propertyName = m.group(1).toString()
			println propertyName
			rCSSStr = rCSSStr.replaceAll("\\[itemscope\\]\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\]\\[itemprop=[\\\"\\\']"+ propertyName + "[\\\"\\\']\\]"
						, "[typeof=\"" + targetedType + "\"][property=\"" + baseURI + propertyName + "\"]")
						.replaceAll("\\[itemscope\\]\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\][^\\[]+?\\[itemprop=[\\\"\\\']"+ propertyName + "[\\\"\\\']\\]"
						, "[typeof=\"" + targetedType + "\"] [property=\"" + baseURI + propertyName + "\"]")
						.replaceAll("\\[itemscope\\][^\\[]+?\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\]\\[itemprop=[\\\"\\\']"+ propertyName + "[\\\"\\\']\\]"
						, "[typeof=\"" + prefix + ":" + typeName + "\"][property=\"" + prefix + ":" + propertyName + "\"]")
						.replaceAll("\\[itemscope\\][^\\[]+?\\[itemtype=[\\\"\\\']"+ targetedType +"[\\\"\\\']\\][^\\[]+?\\[itemprop=[\\\"\\\']"+ propertyName + "[\\\"\\\']\\]"
						, "[typeof=\"" + prefix + ":" + typeName + "\"] [property=\"" + prefix + ":" + propertyName + "\"],\r\n\t\t" +
						  "[typeof=\"" + typeName + "\"][property=\"" + propertyName + "\"],\r\n\t\t" +
						  "[typeof=\"" + typeName + "\"] [property=\"" + propertyName + "\"]");
		}
		render(text: rCSSStr, status: 200)
	}
	
	def transformRDFaLiteIntoMicrodata = {
		def rCSSStr = params.rcss
		def prefix = params.prefix
		def targetedType = params.targetedType
		def baseURI = ""
		def typeName = ""
		if(targetedType.indexOf("#") != -1){
			baseURI = targetedType.split("#	")[0] + "#"
			typeName = targetedType.split("#")[1]
		}
		else{
			def temp = targetedType.split("/") as List
			typeName = temp.pop()
			baseURI = temp.join("/") + "/"
		}
		def mCSSStr = ""
		println "\\[typeof=[\\\"\\\']" + targetedType + "[\\\"\\\']\\]" + "[\\t\\r\\n]*?,[\\t\\r\\n]*?" + "\\[typeof=[\\\"\\\']" + prefix + ":" + typeName + "[\\\"\\\']\\]" + "[\\t\\r\\n]*?,[\\t\\r\\n]*?" + "\\[typeof=[\\\"\\\']" + typeName + "[\\\"\\\']\\]"
		mCSSStr = rCSSStr.replaceAll("\\[typeof=[\\\"\\\']" + targetedType + "[\\\"\\\']\\]" + "[\\t\\r\\n]*?,[\\t\\r\\n]*?" + "\\[typeof=[\\\"\\\']" + prefix + ":" + typeName + "[\\\"\\\']\\]" + "[\\t\\r\\n]*?,[\\t\\r\\n]*?" + "\\[typeof=[\\\"\\\']" + typeName + "[\\\"\\\']\\]"
						, "[itemscope][itemtype=\""+ targetedType +"\"]" + ",\r\n\t\t" + "[itemscope] [itemtype=\""+ targetedType +"\"]")
		Pattern pat = Pattern.compile('\\[typeof=[\\\"\\\'][^\\]]+?[\\\"\\\']\\]\\[property=[\\\"\\\']([^\\\"\\\']+?)[\\\"\\\']\\]');
		Matcher m = pat.matcher(rCSSStr)
		while(m.find()){
			def propertyName = m.group(1).toString()
			mCSSStr = mCSSStr.replaceAll("\\[typeof=[\\\"\\\']" + targetedType + "[\\\"\\\']\\]\\[property=[\\\"\\\']" + baseURI + propertyName + "[\\\"\\\']\\]"
						, "[itemscope][itemtype=\""+ targetedType +"\"][itemprop=\""+ propertyName + "\"]")
						.replaceAll("\\[typeof=[\\\"\\\']" + targetedType + "[\\\"\\\']\\][^\\[]+?\\[property=[\\\"\\\']" + baseURI + propertyName + "[\\\"\\\']\\]"
						, "[itemscope][itemtype=\""+ targetedType +"\"] [itemprop=\""+ propertyName + "\"]")
						.replaceAll("\\[typeof=[\\\"\\\']" + prefix + ":" + typeName + "[\\\"\\\']\\]\\[property=[\\\"\\\']" + prefix + ":" + propertyName + "[\\\"\\\']\\]"
						, "[itemscope] [itemtype=\""+ targetedType +"\"][itemprop=\""+ propertyName + "\"]")
						.replaceAll("\\[typeof=[\\\"\\\']" + prefix + ":" + typeName + "[\\\"\\\']\\][^\\[]+?\\[property=[\\\"\\\']" + prefix + ":" + propertyName + "[\\\"\\\']\\][\\t\\r\\n]*?,[\\t\\r\\n]*?" +
						  "\\[typeof=[\\\"\\\']" + typeName + "[\\\"\\\']\\]\\[property=[\\\"\\\']" + propertyName + "[\\\"\\\']\\][\\t\\r\\n]*?,[\\t\\r\\n]*?" +
						  "\\[typeof=[\\\"\\\']" + typeName + "[\\\"\\\']\\][^\\[]+?\\[property=[\\\"\\\']" + propertyName + "[\\\"\\\']\\]"
						, "[itemscope] [itemtype=\""+ targetedType +"\"] [itemprop=\""+ propertyName + "\"]")
		}
		render(text: mCSSStr, status: 200)
	}

    def forkTemplate = {
        if(session.user == null){
            redirect(controller: "static", action: "login")
        }
        else{
            if (Long.parseLong(params.id) in session.user.templates.id) {
                flash.message = "Template cannot be forked when belonging to you";
            }
            else{
                def template = Template.get(params.id)
                def dt = new Date().getTime().toString()
                def cssTemplate = new Template(typeURI: template.typeURI, contextURL: template.contextURL, baseURI: "empty", prefix: template.prefix, format: template.format, cssTemplate: template.cssTemplate, testSnippet: template.testSnippet, views: 0, likes: 0, tstamp: dt, user: session.user)
    //			if(!cssTemplate.save(flush: true)){
    //				cssTemplate.errors.each{ println it }
    //			}
                session.user.templates.add(cssTemplate)
                if(!session.user.save(flush: true)){
                    session.user.errors.each{ println it }
                }
                println "no. of template: ${ Template.list().size() }"
                flash.message = "Template forked";
            }
            redirect(controller: "user", action: "showTemplates")
        }
    }
}
