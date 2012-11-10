package cssmetaselector

class User {
	
	String username
	String fullname
	String email
	String password
	static hasMany = [templates:Template]
	
	static mapping = {
		table "users"
	}

    static constraints = {
		username(nullable: false)
		fullname(nullable: true)
		email(nullable: false, blank: false, email: true)
		password(nullable: false, blank: false, password: true)		
		templates()
    }
}
