package css.annotation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.arp.JenaReader;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class RDFModelLoader {

	public static Model loadTriplesFromURL(String rdf_url) {
		Model model = ModelFactory.createDefaultModel();
		try {
			URL url = new URL(rdf_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Accept", "application/rdf+xml");
			con.connect();
			// String accept = con.getRequestProperty("Accept");
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			// if(accept.equalsIgnoreCase("application/rdf+xml")){
			// con.setRequestProperty("Accept", "application/rdf+xml");
			try {
				Class.forName("net.rootdev.javardfa.RDFaReader");
				model.read(isr, "XHTML");
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			if (model.isEmpty()) {
				try {
					Class.forName("com.hp.hpl.jena.rdf.arp.JenaReader");
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				JenaReader jreader = new JenaReader();
				jreader.read(model, rdf_url);
//				FileManager fm = new FileManager();
//				model = fm.loadModel(rdf_url, "RDF/XML");
			}
			model = savePrefixForFreemarker(model);
//			model.write(new PrintWriter(System.out), "RDF/XML");
			return model;
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
			return null;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	public static Model loadTriplesFromString(String rdfxml) {
		Model model = ModelFactory.createDefaultModel();
		StringReader sr = new StringReader(rdfxml);
		try {
			Class.forName("net.rootdev.javardfa.RDFaReader");
			model.read(sr, "XHTML");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		if (model.isEmpty()) {
			try {
				Class.forName("com.hp.hpl.jena.rdf.arp.JenaReader");
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			JenaReader jreader = new JenaReader();
			jreader.read(model, sr, "");
//			FileManager fm = new FileManager();
//			model = fm.loadModel(rdf_url, "RDF/XML");
		}
		model = savePrefixForFreemarker(model);
		// model.write(new PrintWriter(System.out), "N3");
		return model;
	}

	public static Model savePrefixForFreemarker(Model model) {
		Map map = model.getNsPrefixMap();
		Set<String> prefixes = map.keySet();
		for (String prefix : prefixes) {
			if (prefix.indexOf(".") != -1) {
				String uri = model.getNsPrefixURI(prefix);
				model.removeNsPrefix(prefix);
				model.setNsPrefix(prefix.replace(".", "dot"), uri);
			}
		}
		return model;
	}

	public static boolean isValidURL(String sample) {
		try {
			URL url = new URL(sample);
			// URLConnection con = url.openConnection();
			return true;
		} catch (MalformedURLException murle) {
			return false;
		}
		// catch(IOException ioe){
		// return false;
		// }
	}

	public static void main(String[] args) {
		RDFModelLoader loader = new RDFModelLoader();
		loader.loadTriplesFromURL("http://www.g1o.net/foaf.rdf");
//		loader.loadTriplesFromURL("http://homepages.inf.ed.ac.uk/s0896253/index.html");
	}
}
