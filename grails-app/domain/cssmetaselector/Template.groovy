package cssmetaselector

class Template {
	
//	static searchable = true
	String id
	String typeURI
	String contextURL
	String baseURI
	String prefix
	String format
	String cssTemplate
	String testSnippet
	static belongsTo = [user:User]
	
	static mapping = {
		cssTemplate type: "text"
		testSnippet type: "text"	
	}

    static constraints = {
		typeURI(nullable: false, blank: false)
		contextURL(nullable: false, blank: false)
		baseURI(nullable: false, blank: false)
		prefix(nullable: false, blank: false)
		format(nullable: false, blank: false)
		cssTemplate(nullable: false, blank: false)
		testSnippet(nullable: true, blank: true)
		user()
    }
}
