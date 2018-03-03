package com.dl.incose.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import com.tacstp.exceptions.TacsException;
/**
 * @author tacstp
 * 
 * Servicio que encapsula la logica HTTP.
 *
 */
public class HTTPService {
	
	final static Logger logger = Logger.getLogger(HTTPService.class.getName());
	/**
	 * Ejecuta el request HTTP. 
	 * @param urlString url sobre la que se hara el request
	 * @param API_KEY Parametro opcional, en caso que la llamada lo necesite, caso contrario enviar "".
	 * @param esJson TRUE si el content-type es Json.
	 * 
	 * @throws TacsException En caso que el server remoto devuelva un error o haya un problema con la conexion {@link TacsException}.
	 * @return StringBuilder Respuesta recibida por el server remoto. 
	 *
	 */
	public StringBuilder hacerRequestHTTP(String urlString,String API_KEY,boolean esJson) {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			urlConn = prepararRequest(urlString,API_KEY,esJson);
			obtenerResponse(sb, urlConn, in);
			logger.info("*************************Request exitoso************************");
		} catch (Exception e) {
			logger.severe("ERROR al Llamar a la siguiente URL: "+ urlString);
			throw new TacsException("Error al Llamar a la siguiente URL: "+ urlString);
		}
		return sb;
	}

	void obtenerResponse(StringBuilder sb, URLConnection urlConn,
			InputStreamReader in) throws IOException {
		//OBTENGO LA RESPUESTA
		logger.info("Obteniendo Respuesta...");
		if (urlConn != null && urlConn.getInputStream() != null) {
			in = new InputStreamReader(urlConn.getInputStream(),
					Charset.defaultCharset());
			BufferedReader bufferedReader = new BufferedReader(in);
			if (bufferedReader != null) {
				int cp;
				while ((cp = bufferedReader.read()) != -1) {
					sb.append((char) cp);
				}
				bufferedReader.close();
			}
		}
		in.close();
	}

	URLConnection prepararRequest(String urlString,String API_KEY,boolean esJson)
			throws MalformedURLException, IOException {
		logger.info("*****************Preparando Request*****************************");
		logger.info("URL:"+urlString);
		URLConnection urlConn;
		//ARMO LA URL
		URL url = new URL(urlString);
		//ABRO LA CONEXION
		urlConn = url.openConnection();
		//SETEO EL HEADER
		urlConn.setRequestProperty("Content-Type", (esJson)?"application/json":"application/xml");
		if (API_KEY!=""){
			urlConn.setRequestProperty("X-ApiKey",API_KEY);
			logger.info("Request con API_KEY:"+API_KEY);
		}
		//SETEO TIMEOUT
		if (urlConn != null)
			urlConn.setReadTimeout(60 * 1000);
		return urlConn;
	}
}
