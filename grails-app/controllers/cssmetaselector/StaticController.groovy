package cssmetaselector

import java.util.regex.Matcher
import java.util.regex.Pattern

import css.annotation.CSSTemplate

class StaticController {

    def scaffold = Static
	
	def welcome = {
		render(view: "welcome", model: [templates: Template.list(max: 5)])	
	}
	
	def getSkeletons = {
		render(view: "style-skeletons")	
	}
	
	def login = {
		render(view: "login")
	}
	
	def register = {
		render(view: "registration")	
	}
	
	def generateTemplate = {
		CSSTemplate csst = new CSSTemplate(params.schema)
		render(view: "style-skeletons", model: [
			template: csst.getCSSSkeleton(params.targetedType, params.format, params.prefix).trim(),
			tem_targetedType: params.targetedType,
			tem_schema: params.schema,
			tem_format: params.format,
			tem_prefix: params.prefix,
			tem_baseURI: params.baseURI,
		])
	}
	
//	def searchTemplate = {
//		def query = params.query
//		if(query){
//			def searchResults = elasticSearchService.search(query)
//			render(view: "search-results", model: [templates: searchResults.results, total: searchResults.total])
//		}
//		else{
//			render(view: "welcome")
//		}
//	}
	
	def searchTemplate = {
		def query = params.query
		def format = params.format
		if(query){
			def searchResults = Template.findAllByFormatAndTypeURIIlike(format, "%" + query + "%")
			def searchResults1 = Template.findAllByFormatAndBaseURIIlike(format, "%" + query + "%")
			def searchResults2 = Template.findAllByFormatAndPrefixIlike(format, "%" + query + "%")
			searchResults1.addAll(searchResults2)
			searchResults.addAll(searchResults1)
			searchResults = searchResults.unique()
			render(view: "search-results", model: [templates: searchResults, total: searchResults.size()])
		}
		else{
			render(view: "welcome")
		}
	}
	
	def lookUpPrefix = {
		def prefix = " "
		def targetURI = params.targetedType.trim()
		if(targetURI.indexOf("#") != -1){
			prefix = servletContext.nSPrefix.getAt(targetURI.split("#")[0] + "#")
//			println prefix
		}
		else{
			if(servletContext.nSPrefix.getAt(targetURI) != null){
				prefix = servletContext.nSPrefix.getAt(targetURI)	
			}
			else{
				def temp = targetURI.split("/") as List
				temp.pop()
				targetURI = temp.join("/") + "/"
				if(servletContext.nSPrefix.getAt(targetURI) != null){
					prefix = servletContext.nSPrefix.getAt(targetURI)
				}
				else{
					render(text: " ", status: 200)
				}
			}
		}
		if(prefix == null) prefix = ""
		render(text: prefix, status: 200)
	}
	
	def transformMicrodataIntoRDFaLite = {
		def mCSSStr = params.cssContent
		def prefix = params.prefix
		def targetedType = params.targetedType
		def baseURI = ""
		def typeName = ""
		if(targetedType.indexOf("#")){
			baseURI = targetedType.split("#")[0] + "#"	
			typeName = targetedType.split("#")[1]
		}
		else{
			def temp = targetedType.split("/") as List
			typeName = temp.pop()
			baseURI = temp.join("/") + "/"	
		}
		def rCSSStr = mCSSStr.replaceAll("[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']]" + "[\t\r\n]*?,[\t\r\n]*?" + "[itemscope][^[]+?[itemtype=[\"\']"+ targetedType +"[\"\']]" 
						, "[typeof=[\"\']"+ targetedType +"[\"\']],\r\n[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']],\r\n[typeof=[\"\']" + typeName + "[\"\']]")
		Pattern pat = Patten.compile('[itemtype=[\"\'](http[^\"\']+?)[\"\']][itemprop=[\"\']([^\"\']+?)[\"\']]');
		Matcher m = pat.matcher(mCSSStr)
		while(m.matches()){
			def propertyName = m.group(1).toString()
			rCSSStr = rCSSStr.replaceAll("[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']][itemprop=[\"\']"+ propertyName + "[\"\']]"
						, "[typeof=[\"\']" + targetedType + "[\"\']][property=[\"\']" + baseURI + propertyName + "[\"\']]")
						.replaceAll("[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']][^[]+?[itemprop=[\"\']"+ propertyName + "[\"\']]"
						, "[typeof=[\"\']" + targetedType + "[\"\']] [property=[\"\']" + baseURI + propertyName + "[\"\']]")
						.replaceAll("[itemscope][^[]+?[itemtype=[\"\']"+ targetedType +"[\"\']][itemprop=[\"\']"+ propertyName + "[\"\']]"
						, "[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']][property=[\"\']" + prefix + ":" + propertyName + "[\"\']]")
						.replaceAll("[itemscope][^[]+?[itemtype=[\"\']"+ targetedType +"[\"\']][^[]+?[itemprop=[\"\']"+ propertyName + "[\"\']]"
						, "[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']] [property=[\"\']" + prefix + ":" + propertyName + "[\"\']],\r\n" + 
						  "[typeof=[\"\']" + typeName + "[\"\']][property=[\"\']" + propertyName + "[\"\']],\r\n" + 
						  "[typeof=[\"\']" + typeName + "[\"\']] [property=[\"\']" + propertyName + "[\"\']]");
		}
		render(text: rCSSStr, status: 200)
	}
	
	def transformRDFaLiteIntoMicrodata = {
		def rCSSStr = params.cssContent
		def prefix = params.prefix
		def targetedType = params.targetedType
		def baseURI = ""
		def typeName = ""
		if(targetedType.indexOf("#")){
			baseURI = targetedType.split("#")[0] + "#"
			typeName = targetedType.split("#")[1]
		}
		else{
			def temp = targetedType.split("/") as List
			typeName = temp.pop()
			baseURI = temp.join("/") + "/"
		}
		def mCSSStr = rCSSStr.replaceAll("[typeof=[\"\']" + targetedType + "[\"\']]" + "[\t\r\n]*?,[\t\r\n]*?" + "[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']]" + "[\t\r\n]*?,[\t\r\n]*?" + "[typeof=[\"\']" + typeName + "[\"\']]"
						, "[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']]" + ",\r\n" + "[itemscope] [itemtype=[\"\']"+ targetedType +"[\"\']]")
		Pattern pat = Patten.compile('[typeof=[\"\'][^]]+?[\"\']][property=[\"\']([^\"\']+?)[\"\']]');
		Matcher m = pat.matcher(rCSSStr)
		while(m.matches()){
			def propertyName = m.group(1).toString()
			mCSSStr = mCSSStr.replaceAll("[typeof=[\"\']" + targetedType + "[\"\']][property=[\"\']" + baseURI + propertyName + "[\"\']]"
						, "[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']][itemprop=[\"\']"+ propertyName + "[\"\']]")
						.replaceAll("[typeof=[\"\']" + targetedType + "[\"\']][^[]+?[property=[\"\']" + baseURI + propertyName + "[\"\']]"
						, "[itemscope][itemtype=[\"\']"+ targetedType +"[\"\']] [itemprop=[\"\']"+ propertyName + "[\"\']]")
						.replaceAll("[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']][property=[\"\']" + prefix + ":" + propertyName + "[\"\']]"
						, "[itemscope] [itemtype=[\"\']"+ targetedType +"[\"\']][itemprop=[\"\']"+ propertyName + "[\"\']]")
						.replaceAll("[typeof=[\"\']" + prefix + ":" + typeName + "[\"\']][^[]+?[property=[\"\']" + prefix + ":" + propertyName + "[\"\']][\t\r\n]*?,[\t\r\n]*?" + 
						  "[typeof=[\"\']" + typeName + "[\"\']][property=[\"\']" + propertyName + "[\t\r\n]*?,[\t\r\n]*?" + 
						  "[typeof=[\"\']" + typeName + "[\"\']][^[]+?[property=[\"\']" + propertyName + "[\"\']]"
						, "[itemscope] [itemtype=[\"\']"+ targetedType +"[\"\']] [itemprop=[\"\']"+ propertyName + "[\"\']]")
		}
		render(text: mCSSStr, status: 200)
	}
}
