package cssmetaselector

class Template {
	
	String typeURI
	String contextURL
	String baseURI
	String prefix
	String format
	String cssTemplate
	static belongsTo = [user:User]
	
	static mapping = {
		cssTemplate type: "text"	
	}

    static constraints = {
		typeURI(nullable: false, blank: false)
		contextURL(nullable: false, blank: false)
		baseURI(nullable: false, blank: false)
		prefix(nullable: false, blank: false)
		format(nullable: false, blank: false)
		cssTemplate(nullable: false, blank: false)
		user()
    }
}
