package com.tacstp.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tacstp.exceptions.TacsException;
/**
 * @author tacstp
 * 
 * Servicio que interactua con Webservicex.
 *
 */
public class ServicioWebservicex extends HTTPService{
	
	final static Logger logger = Logger.getLogger(ServicioWebservicex.class.getName());
	/**
	 * Obtener Latitud y longitud de un aeropuerto. 
	 * @param codigo codigo del aeropuerto.
	 * 
	 * @throws En caso que webservicex devuelva un error o haya un problema con la conexion {@link TacsException}.
	 * @return String[] Array con la latitud y longitud ([0]-lat,[1]-long)
	 *
	 */
	public String[] obtenerLatitudYLongitudDeAeropuerto(String codigo){
		StringBuilder sb = super.hacerRequestHTTP(String.format("http://www.webservicex.com/airport.asmx/getAirportInformationByAirportCode?airportCode=%s",codigo),"",false);
		return obtenerLatYLongDeXML(formatearRespuesta(sb));
	}

	public String[] obtenerLatYLongDeXML(String xml){

		StringBuffer latitud = new StringBuffer();
		StringBuffer longitud = new StringBuffer();
		
		//Creo el DOC.
		DocumentBuilder db;
		Document doc = null;
		logger.info("++WEBSERVICEX--Comienzo armado XML con la respuesta Webservicex...++");
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.severe("Error Mientras se transforma la respuesta de webservicex a XML: "+e.getMessage());
			throw new TacsException("Error Mientras se transforma la respuesta de webservicex a XML: "+e.getMessage());
		}
		
		//Obtengo el nodo donde se encuentran los datos de la lat y long.
		Element newDataSet = (Element) doc.getElementsByTagName("NewDataSet").item(0);
		NodeList tableChilds = newDataSet.getElementsByTagName("Table").item(0).getChildNodes();
		
		for (int j = 0; j < tableChilds.getLength(); j++) {
			Element item = (Element) tableChilds.item(j);
			if ("LatitudeNpeerS".equalsIgnoreCase(item.getTagName())) {
				latitud.insert(0,("S".equalsIgnoreCase(item.getTextContent()) ? "-" : ""));
			}
			if ("LatitudeDegree".equalsIgnoreCase(item.getTagName())) {
				latitud.append(item.getTextContent() + ".");
			}
			if ("LatitudeMinute".equalsIgnoreCase(item.getTagName())) {
				latitud.append(item.getTextContent());
			}
			if ("LatitudeSecond".equalsIgnoreCase(item.getTagName())) {
				latitud.append(item.getTextContent());
			}
			if ("LongitudeEperW".equalsIgnoreCase(item.getTagName())) {
				longitud.insert(0,("W".equalsIgnoreCase(item.getTextContent()) ? "-" : ""));
			}
			if ("LongitudeDegree".equalsIgnoreCase(item.getTagName())) {
				longitud.append(item.getTextContent() + ".");
			}
			if ("LongitudeMinute".equalsIgnoreCase(item.getTagName())) {
				longitud.append(item.getTextContent());
			}
			if ("LongitudeSeconds".equalsIgnoreCase(item.getTagName())) {
				longitud.append(item.getTextContent());
			}
		}
		logger.info("++WEBSERVICEX--Se construyo la respuesta con exito...++");
		return new String[] { latitud.toString(), longitud.toString() };
	}

	private String formatearRespuesta(StringBuilder sb) {
		return StringEscapeUtils.unescapeXml(sb.toString()).
				replaceAll(" ", "").replaceAll("[\n\r]","").replace("xmlns", " xmlns").
				replace("version", " version").replace("encoding", " encoding");
	}

}
