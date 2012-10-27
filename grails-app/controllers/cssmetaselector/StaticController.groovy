package cssmetaselector

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
}
