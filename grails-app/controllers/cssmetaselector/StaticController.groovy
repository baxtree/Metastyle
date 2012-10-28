package cssmetaselector

import css.annotation.CSSTemplate

class StaticController {

    def scaffold = Static
	
	def welcome = {
		render(view: "welcome")	
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
		render(view: "style-skeletons", model: [template: csst.getCSSSkeleton(params.targetedType, params.format, params.prefix, params.baseURI).trim()])
	}
}
