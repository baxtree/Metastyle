package cssmetaselector

import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.JSONElement
import org.scribe.builder.ServiceBuilder
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuthService

import css.annotation.CSSTemplate
import css.annotation.GithubApi


class StaticController {

    def scaffold = Static
	
	def welcome = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"tstamp", order: "desc")])	
	}
	
	def welcome2 = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"views", order: "desc")])		
	}
	
	def proxy2 = {
		redirect(controller: "static", action: "welcome2", fragment: "mostviews")	
	}
	
	def welcome3 = {
		render(view: "welcome", model: [templates: Template.list(max: 5), recent: Template.list(max: 9, sort:"likes", order: "desc")])
	}
	
	def proxy3 = {
		redirect(controller: "static", action: "welcome3", fragment: "popular")
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
	
	def generateTemplate = {
		CSSTemplate csst = new CSSTemplate(params.schema)
		def templateStr = csst.getCSSSkeleton(params.targetedType, params.format, params.prefix).trim()
		render(view: "style-skeletons", model: [
			template: templateStr,
			tem_targetedType: params.targetedType,
			tem_schema: params.schema,
			tem_format: params.format,
			tem_prefix: params.prefix,
			tem_baseURI: params.baseURI,
		])
	}
	
//	def searchTemplate = {
//		def query = params.query
//		if(query){
//			def searchResults = elasticSearchService.search(query)
//			render(view: "search-results", model: [templates: searchResults.results, total: searchResults.total])
//		}
//		else{
//			render(view: "welcome")
//		}
//	}
	
	def searchTemplate = {
		def query = params.query
		def format = params.format
		def max = Integer.parseInt(params.max)
		def offset = Integer.parseInt(params.offset)
		if(query){
			def searchResults = Template.findAllByFormatAndTypeURIIlike(format, "%" + query + "%")
			def searchResults1 = Template.findAllByFormatAndBaseURIIlike(format, "%" + query + "%")
			def searchResults2 = Template.findAllByFormatAndPrefixIlike(format, "%" + query + "%")
			searchResults1.addAll(searchResults2)
			searchResults.addAll(searchResults1)
			searchResults = searchResults.unique()
			def total = searchResults.size();
			def displayedResults
			if((offset + max) >= total){
				displayedResults = searchResults.subList(offset, total)
			}
			else{
				displayedResults = searchResults.subList(offset, offset + max)
			}
			render(view: "search-results", model: [templates: displayedResults, total: total, query: query, format: format])
		}
		else{
			render(view: "welcome")
		}
	}
	
	def lookUpPrefix = {
		def prefix = " "
		def targetURI = params.targetedType.trim()
		if(targetURI.indexOf("#") != -1){
			prefix = servletContext.nSPrefix.getAt(targetURI.split("#")[0] + "#")
//			println prefix
		}
		else{
			if(servletContext.nSPrefix.getAt(targetURI) != null){
				prefix = servletContext.nSPrefix.getAt(targetURI)	
			}
			else{
				def temp = targetURI.split("/") as List
				temp.pop()
				targetURI = temp.join("/") + "/"
				if(servletContext.nSPrefix.getAt(targetURI) != null){
					prefix = servletContext.nSPrefix.getAt(targetURI)
				}
				else{
					render(text: " ", status: 200)
				}
			}
		}
		if(prefix == null) prefix = ""
		render(text: prefix, status: 200)
	}
	
	def showSnippet = {
//		println "template id:" + params.id
		def template = Template.get(params.id)
		def snippet = "";
		snippet += 	"<style type='text/css'>" +
					template.cssTemplate +
					"</style>";
		snippet += template.testSnippet;
		render(text: snippet, status: 200)
	}
	
	def likeTemplate = {
		def template = Template.get(params.id)
		template.likes = template.likes + 1
		if(!template.save(flush: true)){
			template.errors.each{print it}
		}
		redirect(controller: "template", action: "showTemplate", id: params.id)
	}
	
	String NETWORK_NAME = "Github";
	String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
	Token EMPTY_TOKEN = null;
	// Replace these with your own api key and secret
	String apiKey = "8f0c28fd94cf6351ff84";
	String apiSecret = "eb1e81d6e0842e7ef7a0053744eaa9e170d7061f";
	OAuthService service = new ServiceBuilder()
	.provider(GithubApi.class)
	.apiKey(apiKey)
	.apiSecret(apiSecret)
	.scope("user.email")
	.callback("http://127.0.0.1:8080/metastyle/static/oauthcallback")
	.build();
	
	def signInWithGithub = {

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();
	
		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		System.out.println("Got the Authorization URL!");
		System.out.println(authorizationUrl);
		System.out.println("Now go to the authorizing page ...");
		redirect(url: authorizationUrl)
	}
	
	def oauthcallback = {
		if(params.code == null){
			redirect(controller:"static", action:"register")
		}
		else {
			System.out.println("Got the code in the callback: " + params.code.toString());
			Verifier verifier = new Verifier(params.code.toString());
			System.out.println("Verifier: " + verifier.inspect());
			
			// Trade the Request Token and Verfier for the Access Token
			System.out.println("Trading the Request Token for an Access Token...");
			Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if your curious it looks like this: " + accessToken + " )");
			System.out.println();
		
			// Now let's go and ask for a protected resource!
			System.out.println("Now we're going to access a protected resource...");
			OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, request);
			Response response = request.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println("Response Code: " + response.getCode());
			System.out.println("Response Body: " + response.getBody());
			if(response.getCode() != 200){
				flash.message = "Github authentication failed (code: $response.getCode())."
				redirect(controller:"static", action:"register")
			}
			else{
				System.out.println();
				System.out.println("Authentication successful!");
				
				JSONElement profile = JSON.parse(response.getBody())
				def githubID = profile.id.toString() + "@github.com"
				def githubUsername = profile.login.toString()
				
				def user = User.findByUsernameAndEmail(githubUsername, githubID)
				if(user){
					println "number of templates: " + user.templates.size()
					flash.message = "Hello ${user.username}!"
					session.user = user
					redirect(controller:"user", action:"showTemplates")
				}
				else{
					user = new User(
							username: githubUsername,
							fullName: null,
							email: githubID,
							password: null,
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
			}
		}
	}
}
