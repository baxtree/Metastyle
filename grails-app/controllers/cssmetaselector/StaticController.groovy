package cssmetaselector

import java.util.Scanner;

import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.JSONElement
import org.scribe.builder.ServiceBuilder
import org.scribe.builder.api.GoogleApi
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
		CSSTemplate csst
		try{
			csst = new CSSTemplate(params.schema)
		}
		catch(Exception e){
			flash.message = "File loading error. Please check the availability of the vocab and its syntax"
			redirect(controller : "static", action : "getSkeleton")	
		}
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
	
	//OAuth2
	
	OAuthService github_service = new ServiceBuilder()
		.provider(GithubApi.class)
		.apiKey("8f0c28fd94cf6351ff84")
		.apiSecret("eb1e81d6e0842e7ef7a0053744eaa9e170d7061f")
		.scope("user.email")
		.callback("http://127.0.0.1:8080/metastyle/static/oauthcallback")
		.build();
	
	def signInWithGithub = {
		def NETWORK_NAME = "Github";

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();
	
		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = github_service.getAuthorizationUrl(null);
		System.out.println("Got the Authorization URL!");
		System.out.println(authorizationUrl);
		System.out.println("Now go to the authorizing page ...");
		redirect(url: authorizationUrl);
	}
	
	
	//Github oauth callback
	def oauthcallback = {
		println "service : " + github_service.inspect();
		def PROTECTED_RESOURCE_URL = "https://api.github.com/user";
		if(params.code == null){
			redirect(controller:"static", action:"register");
		}
		else {
			System.out.println("Got the code in the callback: " + params.code.toString());
			Verifier verifier = new Verifier(params.code.toString());
			System.out.println("Verifier: " + verifier.inspect());
			
			// Trade the Request Token and Verfier for the Access Token
			System.out.println("Trading the Request Token for an Access Token...");
			Token accessToken = github_service.getAccessToken(null, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if your curious it looks like this: " + accessToken + " )");
			System.out.println();
		
			// Now let's go and ask for a protected resource!
			System.out.println("Now we're going to access a protected resource...");
			OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			github_service.signRequest(accessToken, request);
			Response response = request.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println("Response Code: " + response.getCode());
			System.out.println("Response Body: " + response.getBody());
			if(response.getCode() != 200){
				flash.message = "Github authentication failed (code: $response.getCode()).";
				redirect(controller:"static", action:"register");
			}
			else{
				System.out.println();
				System.out.println("Authentication successful!");
				
				JSONElement profile = JSON.parse(response.getBody());
				def githubID = profile.id.toString() + "@github.com";
				def githubUsername = profile.login.toString();
				
				def user = User.findByUsernameAndEmail(githubUsername, githubID);
				if(user){
					println "number of templates: " + user.templates.size();
					flash.message = "Hello ${user.username}!";
					session.user = user;
					redirect(controller:"user", action:"showTemplates");
				}
				else{
					user = new User(
							username: githubUsername,
							fullName: null,
							email: githubID,
							password: null,
							templates: []
					);
					if(!user.save(flush:true)){
						user.errors.each{ print it; }
					}
					if(user.save(flush:true)){
						println "user created in MySQL!";
					}
					flash.message = "Hello ${user.username}!";
					session.user = user;
					redirect(controller:"user", action:"showTemplates");
				}
			}
		}
	}
	
	OAuthService google_service = new ServiceBuilder()
		.provider(GoogleApi.class)
		.apiKey("1087997739736-kq7tc9lc8q92bedh4s0v2l1lnbkdbu3u.apps.googleusercontent.com")
		.apiSecret("RH9NDZDQaUXTmxjBnXlL3Rtt")
		.scope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email")
		.callback("http://127.0.0.1:8080/metastyle/static/oauth2callback")
		.build();
	
	def signInWithGoogle = {
		def NETWORK_NAME = "Google";
		def AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();
		
		servletContext["google_requestToken"] = google_service.getRequestToken();
		// Obtain the Request Token
		System.out.println("Fetching the Request Token...");
		System.out.println("Got the Request Token!");
		System.out.println("(if your curious it looks like this: " + servletContext["google_requestToken"] + " )");
		System.out.println();
		
		System.out.println("Now go and authorize Scribe here:");
		System.out.println(AUTHORIZE_URL + servletContext["google_requestToken"].getToken());
		System.out.println("And paste the verifier here");
		redirect(url : AUTHORIZE_URL + servletContext["google_requestToken"].getToken());
		
//		String NETWORK_NAME = "Google";
//		String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
//		String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
//		String SCOPE = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
//		
//		OAuthService service = new ServiceBuilder()
//		.provider(GoogleApi.class)
//		.apiKey("1087997739736-kq7tc9lc8q92bedh4s0v2l1lnbkdbu3u.apps.googleusercontent.com")
//		.apiSecret("RH9NDZDQaUXTmxjBnXlL3Rtt")
//		.scope(SCOPE)
//		.build();
//		Scanner sysin = new Scanner(System.in);
//		
//		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
//		System.out.println();
//		
//		// Obtain the Request Token
//		System.out.println("Fetching the Request Token...");
//		Token requestToken = service.getRequestToken();
//		System.out.println("Got the Request Token!");
//		System.out.println("(if your curious it looks like this: " + requestToken + " )");
//		System.out.println();
//		
//		System.out.println("Now go and authorize Scribe here:");
//		System.out.println(AUTHORIZE_URL + requestToken.getToken());
//		System.out.println("And paste the verifier here");
//		System.out.print(">>");
//		Verifier verifier = new Verifier(sysin.nextLine());
//		System.out.println();
//		
//		// Trade the Request Token and Verfier for the Access Token
//		System.out.println("Trading the Request Token for an Access Token...");
//		Token accessToken = service.getAccessToken(requestToken, verifier);
//		System.out.println("Got the Access Token!");
//		System.out.println("(if your curious it looks like this: " + accessToken + " )");
//		System.out.println();
//		
//		// Now let's go and ask for a protected resource!
//		System.out.println("Now we're going to access a protected resource...");
//		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//		service.signRequest(accessToken, request);
//		request.addHeader("GData-Version", "3.0");
//		Response response = request.send();
//		System.out.println("Got it! Lets see what we found...");
//		System.out.println();
//		System.out.println(response.getCode());
//		System.out.println(response.getBody());
//		
//		System.out.println();
//		System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
	}
	
	
	//Google oauth callback
	def oauth2callback = {
		println "service : " + google_service.inspect();
		def PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
		if(params.oauth_verifier == null){
			redirect(controller:"static", action:"register");
		}
		else {
			Verifier verifier = new Verifier(params.oauth_verifier);
			System.out.println();
			
			// Trade the Request Token and Verfier for the Access Token
			System.out.println("Trading the Request Token for an Access Token...");
			println "Request Token: " + servletContext["google_requestToken"]	;
			println "google service: " + google_service;
			try{
				Token accessToken = google_service.getAccessToken(servletContext["google_requestToken"], verifier);
				System.out.println("Got the Access Token!");
				System.out.println("(if your curious it looks like this: " + accessToken + " )");
				System.out.println();
				
				// Now let's go and ask for a protected resource!
				System.out.println("Now we're going to access a protected resource...");
				OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
				google_service.signRequest(accessToken, request);
				request.addHeader("GData-Version", "3.0");
				Response response = request.send();
				System.out.println("Got it! Lets see what we found...");
				System.out.println();
				System.out.println(response.getCode());
				System.out.println(response.getBody());
				
				if(response.getCode() != 200){
					flash.message = "Google+ authentication failed (code: $response.getCode()).";
					redirect(controller:"static", action:"register");
				}
				else{
					System.out.println();
					System.out.println("Authentication successful!");
					
					JSONElement profile = JSON.parse(response.getBody());
					def email = profile.email.toString();
					def username = profile.given_name.toString();
					
					def user = User.findByUsernameAndEmail(username, email);
					if(user){
						println "number of templates: " + user.templates.size();
						flash.message = "Hello ${user.username}!";
						session.user = user;
						redirect(controller:"user", action:"showTemplates");
					}
					else{
						user = new User(
								username: username,
								fullName: profile.name.toString(),
								email: email,
								password: null,
								templates: []
						);
						if(!user.save(flush:true)){
							user.errors.each{ print it; }
						}
						if(user.save(flush:true)){
							println "user created in MySQL!";
						}
						flash.message = "Hello ${user.username}!";
						session.user = user;
						redirect(controller:"user", action:"showTemplates");
					}
				}
			}
			catch(Exception e){
				flash.message = "Google authentication denied.";
				redirect(controller:"static", action:"register");
			}
		}
	}
	
	def showGuestTemplate = {
		println "User ID: " + params.id
		def guestUser = User.get(params.id)
		render(view: "guest-templates", model: [templates : guestUser.templates, username : guestUser.username])
	}
}
