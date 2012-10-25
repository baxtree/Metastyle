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

public class CSSTemplate {
	
	private Model model;
	private OntModel ontModel;
	
	public String getCSSSkeleton(String url, String flag, String prefix){
		String cSSSkeleton = "";
		try{
			String vocabDomain = (new URL(url)).getHost();
			model = RDFModelLoader.loadTriplesFromURL(url);
			ontModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
			ontModel.add(model);
			Iterator classes = ontModel.listNamedClasses();
			while(classes.hasNext()){
				OntClass c = (OntClass) classes.next();
				//Presume that the namespace domain is the same with the domain of the vocabulary
				if(!(new URL(c.getURI())).getHost().equalsIgnoreCase(vocabDomain)) continue;
	//			System.out.println(((OntClass) classes.next()).getLocalName());
				cSSSkeleton += 	"		/*Style for the " + c.getLocalName() + "*/\r\n";
				if(flag.equalsIgnoreCase("microdata"))
					cSSSkeleton +=	"		[itemscope][itemtype=\"" + c.getURI() + "\"],\r\n" +
									"		[itemscope] [itemtype=\"" + c.getURI() + "\"] {\r\n" +
									"		\r\n" +
									"		}\r\n" +
									"		\r\n";
				else if(flag.equalsIgnoreCase("rdfalite"))
					cSSSkeleton += 	"		[typeof=\"" + c.getURI() + "\"],\r\n" +
									"		[typeof=\"" + prefix + ":" + c.getLocalName() + "\"],\r\n" +
									"		[typeof=\"" + c.getLocalName() + "\"] {\r\n" +
									"		\r\n" +
									"		}\r\n" +
									"		\r\n";
				else 
					System.err.println("Unknown format!");
				Iterator properties = c.listDeclaredProperties();
				while(properties.hasNext()){
					OntProperty p = (OntProperty) properties.next();
//					System.out.println(c.getURI() + " : " + p.getURI());
					cSSSkeleton += "		/*Style for the " + p.getLocalName() + " of the " + c.getLocalName() + "*/\r\n";
					if(flag.equalsIgnoreCase("microdata"))
						cSSSkeleton +=	"		[itemscope][itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
										"		[itemscope][itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"],\r\n" +
										"		[itemscope] [itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
										"		[itemscope] [itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"] {\r\n" +
										"		\r\n" +
										"		}\r\n" +
										"		\r\n";
					else if(flag.equalsIgnoreCase("rdfalite"))
						cSSSkeleton +=	"		[typeof=\"" + c.getURI() + "\"][property=\"" + p.getURI() + "\"],\r\n" +
										"		[typeof=\"" + c.getURI() + "\"] [property=\"" + p.getURI() + "\"],\r\n" +
										"		[typeof=\"" + prefix + ":" + c.getLocalName() + "\"][property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
										"		[typeof=\"" + prefix + ":" + c.getLocalName() + "\"] [property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
										"		[typeof=\"" + c.getLocalName() + "\"][property=\"" + p.getLocalName() + "\"],\r\n" +
										"		[typeof=\"" + c.getLocalName() + "\"] [property=\"" + p.getLocalName() + "\"] {\r\n" +
										"		\r\n" +
										"		}\r\n" +
										"		\r\n";
					else
						System.err.println("Unknown format!");
				}
			}
			return cSSSkeleton;
		}
		catch(MalformedURLException murle){
			murle.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args){
		CSSTemplate csst = new CSSTemplate();
		System.out.println(csst.getCSSSkeleton("http://xmlns.com/foaf/spec/index.rdf", "rdfalite", "foaf"));
	}

}
