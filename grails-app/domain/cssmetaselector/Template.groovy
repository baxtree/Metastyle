package cssmetaselector

class Template {
	
	String typeURI
	String contextURL
	String rawHTML
	static belongsTo = [user:User]

    static constraints = {
		typeURI(nullable: false, blank: false)
		contextURL(nullable: false, blank: false)
		rawHTML(nullable: false, blank: false)
		user()
    }
}
