package cssmetaselector

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
}
