package cssmetaselector

import css.annotation.CSSTemplate

class StaticController {

    def scaffold = Static
	
	def welcome = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"tstamp", order: "desc")])	
	}
	
	def welcome2 = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"views", order: "desc")])		
	}
	
	def proxy2 = {
		redirect(controller: "static", action: "welcome2", fragment: "mostviews")	
	}
	
	def welcome3 = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"likes", order: "desc")])
	}
	
	def proxy3 = {
		redirect(controller: "static", action: "welcome3", fragment: "popular")
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
		def templateStr = csst.getCSSSkeleton(params.targetedType, params.format, params.prefix).trim()
		render(view: "style-skeletons", model: [
			template: templateStr,
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
		def max = Integer.parseInt(params.max)
		def offset = Integer.parseInt(params.offset)
		if(query){
			def searchResults = Template.findAllByFormatAndTypeURIIlike(format, "%" + query + "%")
			def searchResults1 = Template.findAllByFormatAndBaseURIIlike(format, "%" + query + "%")
			def searchResults2 = Template.findAllByFormatAndPrefixIlike(format, "%" + query + "%")
			searchResults1.addAll(searchResults2)
			searchResults.addAll(searchResults1)
			searchResults = searchResults.unique()
			def total = searchResults.size();
			def displayedResults
			if((offset + max) >= total){
				displayedResults = searchResults.subList(offset, total)
			}
			else{
				displayedResults = searchResults.subList(offset, offset + max)
			}
			render(view: "search-results", model: [templates: displayedResults, total: total, query: query, format: format])
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
	
	def showSnippet = {
//		println "template id:" + params.id
		def template = Template.get(params.id)
		def snippet = "";
		snippet += 	"<style type='text/css'>" +
					template.cssTemplate +
					"</style>";
		snippet += template.testSnippet;
		render(text: snippet, status: 200)
	}
	
	def likeTemplate = {
		def template = Template.get(params.id)
		template.likes = template.likes + 1
		if(!template.save(flush: true)){
			template.errors.each{print it}
		}
		redirect(controller: "static", action: "welcome")
	}
}
