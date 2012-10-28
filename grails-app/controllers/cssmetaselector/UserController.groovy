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
	
	def userLogin = {
		def user = User.findByUsernameAndPassword(params.username, params.password)
		if(user){
			session.user = user
			flash.message = "Hello ${user.username}!"
			render(view:"user-templates")
		}
		else{
			flash.message = "Sorry. User name or password were incorrect. Please try again."
			redirect(controller: "static", action: "login")
		}
	}
	
	def userRegister = {
		def user = User.findByUsername(params.username)
		if(user){
			flash.message = "User ${params.username} already exists."
			println "User ${params.username} already exists."
			redirect(controller:"static", action:"register")
		}
		else{
			if(params.password == params.repassword){
				user = new User(
						username: params.username,
						fullName: params.fullName,
						email: params.email,
						password: params.password,
						templates: []
				)
				if(!user.save(flush:true)){
					user.errors.each{ print it }
				}
				if(user.save(flush:true)){
					println "user created in MySQL!"
				}
				flash.message = "Hello ${user.username}!"
				session.user = user
				redirect(controller:"user", action:"showTemplates")
			}
			else{
				flash.message = "Passwords do not match. Try again."
				println "Passwords do not match. Try again."
				redirect(controller:"static", action:"register")
			}
		}
	}
	
	def userLogout = {
		session.user = null
		redirect(controller: "static", action: "welcome")
	}
		
}
