package css.annotation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CSSTemplate {

    private static final Log LOGGER = LogFactory.getLog(CSSTemplate.class);
	
	private Model model;
	private OntModel ontModel;
	
	public CSSTemplate(String url){
		try{
			String vocabDomain = (new URL(url)).getHost();
			model = RDFModelLoader.loadTriplesFromURL(url);
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF); //deal with owl:unionOf
			ontModel.add(model);
		}
		catch(MalformedURLException murle){
			murle.printStackTrace();
		}
	}
	
	public String getCSSSkeletonByClass(String flag, String prefix, OntClass c){
		String cSSSkeleton = "";
		String originFlag = flag;
		flag = flag.replaceAll("\\s+?", "");
		cSSSkeleton += 	"/* style for the " + c.getLocalName() + " (" + c.getURI() + ")"+ " as " + originFlag + " */\r\n" +
						"\r\n";
		cSSSkeleton += 	"/* style for the type " + c.getLocalName() + " */\r\n";
		if(flag.equalsIgnoreCase("microdata"))
			cSSSkeleton +=	"[itemscope][itemtype=\"" + c.getURI() + "\"] {\r\n" +
//							"[itemscope] [itemtype=\"" + c.getURI() + "\"] {\r\n" +
							"\r\n" +
							"}\r\n" +
							"\r\n";
		else if(flag.equalsIgnoreCase("rdfalite"))
			cSSSkeleton += 	"[typeof=\"" + c.getURI() + "\"],\r\n" +
							"[typeof=\"" + prefix + ":" + c.getLocalName() + "\"],\r\n" +
							"[typeof=\"" + c.getLocalName() + "\"] {\r\n" +
							"\r\n" +
							"}\r\n" +
							"\r\n";
		else 
			System.err.println("Unknown format!");
		Iterator properties = c.listDeclaredProperties();
		while(properties.hasNext()){
			OntProperty p = (OntProperty) properties.next();
			if(!p.getNameSpace().equalsIgnoreCase(c.getNameSpace())) continue; //get rid of properties coming from another name spaces
//			LOGGER.info(c.getURI() + " : " + p.getURI());
			cSSSkeleton += "/* style for the property " + p.getLocalName()  + " */\r\n";
			if(flag.equalsIgnoreCase("microdata"))
				cSSSkeleton +=	"[itemscope][itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
								"[itemscope][itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"] {\r\n" +
//								"[itemscope] [itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
//								"[itemscope] [itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"] {\r\n" +
								"\r\n" +
								"}\r\n" +
								"\r\n";
			else if(flag.equalsIgnoreCase("rdfalite"))
				cSSSkeleton +=	"[typeof=\"" + c.getURI() + "\"][property=\"" + p.getURI() + "\"],\r\n" +
								"[typeof=\"" + c.getURI() + "\"] [property=\"" + p.getURI() + "\"],\r\n" +
								"[typeof=\"" + prefix + ":" + c.getLocalName() + "\"][property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
								"[typeof=\"" + prefix + ":" + c.getLocalName() + "\"] [property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
								"[typeof=\"" + c.getLocalName() + "\"][property=\"" + p.getLocalName() + "\"],\r\n" +
								"[typeof=\"" + c.getLocalName() + "\"] [property=\"" + p.getLocalName() + "\"] {\r\n" +
								"\r\n" +
								"}\r\n" +
								"\r\n";
			else
				System.err.println("Unknown format!");
		}
		return cSSSkeleton.trim();
	}
	
	public String getLessCSSSkeletonByClass(String flag, String prefix, OntClass c){
		String cSSSkeleton = "";
		String interpolations = "";
		String originFlag = flag;
		flag = flag.replaceAll("\\s+?", "");
		interpolations += "/* style for the " + c.getLocalName() + " (" + c.getURI() + ")"+ " as " + originFlag + " */\r\n\r\n";
		if(flag.equalsIgnoreCase("microdata")) {
			interpolations += "@" + prefix + "_" + c.getLocalName() + ": ~'[itemscope][itemtype=\"" + c.getURI() + "\"]';\r\n";
			cSSSkeleton += 	"/* style for the type " + c.getLocalName() + " */\r\n";
		}
		else if(flag.equalsIgnoreCase("rdfalite")) {
			interpolations += "@" + prefix + "_" + c.getLocalName() + ": ~'[typeof=\"" + c.getURI() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"]';\r\n";
			cSSSkeleton += 	"/* style for the type " + c.getLocalName() + " */\r\n";
		}
		else 
			System.err.println("Unknown format!");
		cSSSkeleton +=	"@{" + prefix + "_" + c.getLocalName() + "} {\r\n" +
						"\r\n";
        cSSSkeleton +=  "}\r\n" +
                        "\r\n";
		Iterator properties = c.listDeclaredProperties();
		while(properties.hasNext()){
			OntProperty p = (OntProperty) properties.next();
			if(!p.getNameSpace().equalsIgnoreCase(c.getNameSpace())) continue; //get rid of properties coming from another name spaces
//			LOGGER.info(c.getURI() + " : " + p.getURI());
			if(flag.equalsIgnoreCase("microdata")) {
				interpolations += "@" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + ": ~'[itemscope][itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],[itemscope][itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"]';\r\n";
			}
			else if(flag.equalsIgnoreCase("rdfalite"))
				interpolations += "@" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + ": ~'[typeof=\"" + c.getURI() + "\"][property=\"" + p.getURI() + "\"],[typeof=\"" + c.getURI() + "\"] [property=\"" + p.getURI() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"][property=\"" + prefix + ":" + p.getLocalName() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"] [property=\"" + prefix + ":" + p.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"][property=\"" + p.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"] [property=\"" + p.getLocalName() + "\"]';\r\n";
			else
				System.err.println("Unknown format!");
			cSSSkeleton +=	"@{" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + "} {\r\n" +
                            "\t/* style for the property " + p.getLocalName()  + " */\r\n" +
							"}\r\n" +
							"\r\n";
		}
		return interpolations.trim() + "\r\n\r\n" + cSSSkeleton.trim();
	}
	
	/* Lots of duplicated code here compared with LESS CSS Skeleton generation due to more differences 
	 * will be brought in the future. Or these two methods will otherwise be refactored.
	 */
	private String getSassCSSSkeletonByClass(String flag, String prefix, OntClass c) {
		String cSSSkeleton = "";
		String interpolations = "";
		String originFlag = flag;
		flag = flag.replaceAll("\\s+?", "");
		interpolations += "/* style for the " + c.getLocalName() + " (" + c.getURI() + ")"+ " as " + originFlag + " */\r\n\r\n";
		if(flag.equalsIgnoreCase("microdata")) {
			interpolations += "$" + prefix + "_" + c.getLocalName() + ": '[itemscope][itemtype=\"" + c.getURI() + "\"]';\r\n";
			cSSSkeleton += 	"/* style for the type " + c.getLocalName() + " */\r\n";
		}
		else if(flag.equalsIgnoreCase("rdfalite")) {
			interpolations += "$" + prefix + "_" + c.getLocalName() + ": '[typeof=\"" + c.getURI() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"]';\r\n";
			cSSSkeleton += 	"/* style for the type " + c.getLocalName() + " */\r\n";
		}
		else 
			System.err.println("Unknown format!");
		Iterator properties = c.listDeclaredProperties();
        if(flag.equalsIgnoreCase("microdata")) {
            cSSSkeleton +=	"#{$" + prefix + "_" + c.getLocalName() + "} {\r\n" +
                    "\t\r\n";
            while(properties.hasNext()){
                OntProperty p = (OntProperty) properties.next();
                if(!p.getNameSpace().equalsIgnoreCase(c.getNameSpace())) continue; //get rid of properties coming from another name spaces
    //			LOGGER.info(c.getURI() + " : " + p.getURI());
                interpolations += "$" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + ": '&[itemprop=\"" + p.getLocalName() +"\"],[itemprop=\"" + p.getLocalName() +"\"]';\r\n";
                cSSSkeleton +=	"\t#{$" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + "} {\r\n" +
                                "\t\t/* style for the property " + p.getLocalName()  + " */\r\n" +
                                "\t}\r\n" +
                                "\t\r\n";
            }
            cSSSkeleton +=  "}\r\n" +
                            "\t\r\n";
        }
        else if(flag.equalsIgnoreCase("rdfalite")) {
            cSSSkeleton +=	"#{$" + prefix + "_" + c.getLocalName() + "} {\r\n" +
                    "\t\r\n" +
                    "}\r\n" +
                    "\r\n";
            while(properties.hasNext()){
                OntProperty p = (OntProperty) properties.next();
                if(!p.getNameSpace().equalsIgnoreCase(c.getNameSpace())) continue; //get rid of properties coming from another name spaces
                //			LOGGER.info(c.getURI() + " : " + p.getURI());
                interpolations += "$" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + ": '[typeof=\"" + c.getURI() + "\"][property=\"" + p.getURI() + "\"],[typeof=\"" + c.getURI() + "\"] [property=\"" + p.getURI() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"][property=\"" + prefix + ":" + p.getLocalName() + "\"],[typeof=\"" + prefix + ":" + c.getLocalName() + "\"] [property=\"" + prefix + ":" + p.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"][property=\"" + p.getLocalName() + "\"],[typeof=\"" + c.getLocalName() + "\"] [property=\"" + p.getLocalName() + "\"]';\r\n";
                cSSSkeleton +=	"#{$" + prefix + "_" + c.getLocalName() + "-" + p.getLocalName() + "} {\r\n" +
                        "\t/* style for the property " + p.getLocalName()  + " */\r\n" +
                        "}\r\n" +
                        "\r\n";
            }
        }
        else
            System.err.println("Unknown format!");
		return interpolations.trim() + "\r\n\r\n" + cSSSkeleton.trim();
	}
	
//	public String getVocabCSSSkeleton(String flag, String prefix, String baseURI){
//		String cSSSkeleton = "";	
//		Iterator classes = ontModel.listNamedClasses();
//		while(classes.hasNext()){
//			OntClass c = (OntClass) classes.next();
//			//Presume that the namespace domain is the same with the domain of the vocabulary
//			if(!c.getURI().startsWith(baseURI)) continue;
////				if(!(new URL(c.getURI())).getHost().equalsIgnoreCase(vocabDomain)) continue;
////					LOGGER.info(((OntClass) classes.next()).getLocalName());
//			cSSSkeleton += getCSSSkeletonByClass(flag, prefix, c);
//		}
//		return cSSSkeleton.trim();
//		
//	}
	
	public String getCSSSkeleton(String targetURL, String flag, String prefix){
		OntClass c = ontModel.getOntClass(targetURL);
		String cSSSkeleton = getCSSSkeletonByClass(flag, prefix, c);
		return cSSSkeleton.trim();
	}
	
	public String getLessCSSSkeleton(String targetURL, String flag, String prefix){
		OntClass c = ontModel.getOntClass(targetURL);
		String cSSSkeleton = getLessCSSSkeletonByClass(flag, prefix, c);
		return cSSSkeleton.trim();
	}
	
	public String getSassCSSSkeleton(String targetURL, String flag, String prefix) {
		OntClass c = ontModel.getOntClass(targetURL);
		String cSSSkeleton = getSassCSSSkeletonByClass(flag, prefix, c);
		return cSSSkeleton.trim();
	}
	
	public static void main(String[] args){
		CSSTemplate csst = new CSSTemplate("http://schema.rdfs.org/all.rdf");
		LOGGER.info(csst.getCSSSkeleton("http://xmlns.com/foaf/spec/index.rdf", "RDFa Lite", "foaf"));
//		LOGGER.info(csst.getCSSSkeleton("Microdata", "", "http://schema.org/"));
//		LOGGER.info(csst.getCSSSkeleton("http://schema.org/Person", "Microdata", "", "http://schema.org/"));
//		LOGGER.info(csst.getCSSSkeleton("http://schema.org/Person", "RDFa Lite", "foaf", "http://schema.org/"));
	}

}
