package cssmetaselector

import java.security.MessageDigest

class UserController {

    def scaffold = User

    def index = {
        redirect(controller: "static", action: "welcome")
    }
	
	def showHome = {
		redirect(controller: "static", action: "welcome")	
	}
	
	def showTemplates = {
        if(session.user == null){
            redirect(controller: "static", action: "login")
        }
        else{
            MessageDigest digest = MessageDigest.getInstance("MD5")
            digest.update(session.user.email.toString().bytes)
            def hash = new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')
            render(view: "user-templates", model: [emailHash: hash])
        }
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
		def user = User.findByEmail(params.email)
		if(user){
			flash.message = "User with the email ${params.email} already exists."
			println "User with the email ${params.email} already exists."
			redirect(controller:"static", action:"register")
		}
		else{
			if(params.password == params.repassword && params.password != null && params.password.size() >= 8){
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
				flash.message = "Passwords do not match or their lengths are less than 8. Try again."
				println "Passwords do not match. Try again."
				redirect(controller:"static", action:"register")
			}
		}
	}
	
	def shareTemplate = {
		if(session.user == null){
			redirect(controller: "static", action: "login")	
		}	
		else{
			def dt = new Date().getTime().toString()
			def cssTemplate = new Template(typeURI: params.tem_targetedType, contextURL: params.tem_schema, baseURI: "empty", prefix: params.tem_prefix, format: params.tem_format, cssTemplate: params.tem_template, testSnippet: params.testSnippet, views: 0, likes: 0, tstamp: dt, user: session.user)
//			if(!cssTemplate.save(flush: true)){
//				cssTemplate.errors.each{ println it }
//			}
			session.user.templates.add(cssTemplate)
			if(!session.user.save(flush: true)){
				session.user.errors.each{ println it }
			}
			println "no. of template: ${ Template.list().size() }"
			flash.message = "template saved";
			render(view: "user-templates")
		}
	}
	
	def userLogout = {
		session.user = null
		redirect(controller: "static", action: "welcome")
	}
		
}
