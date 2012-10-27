package cssmetaselector

class UserController {

    def scaffold = User

    def index = {
        render(view: "home")
    }
	
	def showHome = {
		render(view: "home")	
	}
	
	def showTemplates = {
		render(view: "user-templates")
	}
		
}
