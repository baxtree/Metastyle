import cssmetaselector.User;
class BootStrap {

    def init = { servletContext ->
		if(User.list().size() == 0){
			def test = new User(
				username: "test",
				fullname: "test",
				email: "test@tt",
				password: "test")
			test.save(flush: true)
		}
    }
    def destroy = {
    }
}
