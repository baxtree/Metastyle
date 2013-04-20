package cssmetaselector

class ProfileController {
    def apiUrl = "https://api.github.com/user"
    def oauthService
     
    def index = {
  
        if (session.oauthToken == null) {
            redirect(uri:"/")
        }
  
        if (params?.apiUrl) apiUrl = params.apiUrl
         
        def response = oauthService.accessResource(
                apiUrl, 'github', [key:session.oauthToken.key, secret:session.oauthToken.secret], 'GET')
  
        render(view: 'index', model: [profileXML: response, apiUrl: apiUrl])
    }
  
    def change = {
        if (params?.apiUrl) {
            println("Setting api url to " + params.apiUrl)
            apiUrl = params.apiUrl
        }
         
        redirect(action:index,params:params)
    }
}
