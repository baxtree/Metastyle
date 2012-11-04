import cssmetaselector.User 
import org.codehaus.groovy.grails.commons.ApplicationHolder  
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
		//		elasticSearchService.index() 		
		def prefixNS = [:] 		
		def nSPrefix = [:] 		
		def prefixesPath = "resources/prefixes.txt" 		
		def text = ApplicationHolder.application.parentContext.getResource("classpath:$prefixesPath").inputStream.text 		
		text.eachLine{ 			
			if(it.toString().indexOf("???") == -1){ 				
				def key = it.split("\t")[0].trim() 				
				def value = it.split("\t")[1].trim() 				
				prefixNS[key] = value 				
				nSPrefix[value] = key 			
				} 		
			} 		
		servletContext.prefixNS = prefixNS 		
		servletContext.nSPrefix = nSPrefix 		
		print "$nSPrefix prefixes loaded ..."     
	}     
	def destroy = {     } 
}
