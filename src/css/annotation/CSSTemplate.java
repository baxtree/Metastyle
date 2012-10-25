package css.annotation;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class CSSTemplate {
	
	private Model model;
	private OntModel ontModel;
	
	public String getCSSSkeleton(String url, String flag, String prefix){
		String cSSSkeleton = "";
		model = RDFModelLoader.loadTriplesFromURL(url);
		ontModel = ModelFactory.createOntologyModel();
		ontModel.add(model);
//		String query_str = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
//				"			PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
//				"			PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
//				"			SELECT ?class ?property" +
//				"			WHERE {" +
//				"				OPTIONAL { ?property rdfs:domain ?class. }." +
//				"				OPTIONAL { ?class rdf:type rdfs:Class. }." +
//				"				OPTIONAL { ?class rdf:type owl:Class. }." +
//				"				OPTIONAL { ?property rdf:type rdf:Property. }." +
//				"				OPTIONAL { ?property rdf:type owl:DatatypeProperty. }." +
//				"				OPTIONAL { ?property rdf:type owl:ObjectProperty. }" +
//				"			}";
////		+
////				"			GROUP BY ?class";
//		Query query = QueryFactory.create(query_str);
//		QueryExecution qe = QueryExecutionFactory.create(query, this.infModel);
//		ResultSet results = qe.execSelect();
//		for(;results.hasNext();){
//			QuerySolution qs = results.nextSolution();
//			Resource c = (Resource) qs.get("class");
//			Resource p = (Resource) qs.get("property");
//			if(p != null)
//				System.out.println("Class: " + c.getURI() + " Property: " + p.getURI());
//			else
//				System.out.println("Class: " + c.getURI());
//		}
		Iterator classes = ontModel.listNamedClasses();
		while(classes.hasNext()){
			OntClass c = (OntClass) classes.next();
//			System.out.println(((OntClass) classes.next()).getLocalName());
			cSSSkeleton += 	"/*Style for the " + c.getLocalName() + "*/\r\n";
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
//				System.out.println(c.getURI() + " : " + p.getURI());
				cSSSkeleton += "/*Style for the " + p.getLocalName() + " of the " + c.getLocalName() + "*/\r\n";
				if(flag.equalsIgnoreCase("microdata"))
					cSSSkeleton +=	"		[itemscope][itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
									"		[itemscope][itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"],\r\n" +
									"		[itemscope] [itemtype=\"" + c.getURI() + "\"][itemprop=\"" + p.getLocalName() +"\"],\r\n" +
									"		[itemscope] [itemtype=\"" + c.getURI() + "\"] [itemprop=\"" + p.getLocalName() +"\"] {\r\n" +
									"		\r\n" +
									"		}\r\n" +
									"		\r\n";
				else if(flag.equalsIgnoreCase("rdfalite"))
					cSSSkeleton +=	"[typeof=\"" + c.getURI() + "\"][property=\"" + p.getURI() + "\"],\r\n" +
									"[typeof=\"" + c.getURI() + "\"] [property=\"" + p.getURI() + "\"],\r\n" +
									"[typeof=\"" + prefix + ":" + c.getLocalName() + "\"][property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
									"[typeof=\"" + prefix + ":" + c.getLocalName() + "\"] [property=\"" + prefix + ":" + p.getLocalName() + "\"],\r\n" +
									"[typeof=\"" + c.getLocalName() + "\"][property=\"" + p.getLocalName() + "\"],\r\n" +
									"[typeof=\"" + c.getLocalName() + "\"] [property=\"" + p.getLocalName() + "\"] {\r\n" +
									"		\r\n" +
									"		}\r\n" +
									"		\r\n";
				else
					System.err.println("Unknown format!");
			}
		}
		return cSSSkeleton;
	}
	
	public static void main(String[] args){
		CSSTemplate csst = new CSSTemplate();
		System.out.println(csst.getCSSSkeleton("http://xmlns.com/foaf/spec/index.rdf", "microdata", "foaf"));
	}

}
