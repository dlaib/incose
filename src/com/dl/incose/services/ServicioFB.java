package com.dl.incose.services;

import java.util.logging.Logger;

import com.dl.incose.model.Viaje;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.tacstp.exceptions.FacebookAuthException;
import com.tacstp.exceptions.FacebookException;
/**
 * @author tacstp
 * 
 * Servicio que interactua con facebook.
 *
 */
public class ServicioFB {
	private static final String APP_KEY = "824588610929979";
	private static final String APP_SECRET = "36daf49338f165cb21e40b5c6427f0f6";
	final static Logger logger = Logger.getLogger(ServicioFB.class.getName());
	/**
	 * Envia una notificacion al usuario. 
	 * @param externalUserId facebook id.
	 * @param message mensaje que se le notificara al usuario.
	 * 
	 * @throws En caso que facebook devuelva un error {@link FacebookException}.
	 *
	 */
	@SuppressWarnings("deprecation")
	public void enviarNotificacion(String externalUserId, String message) {
		logger.info("FACEBOOK--Creando notificacion al usuario...");
		AccessToken appAccessToken = new DefaultFacebookClient().obtainAppAccessToken(APP_KEY, APP_SECRET);
	    FacebookClient facebookClient = new DefaultFacebookClient(appAccessToken.getAccessToken());
	    try {
	        facebookClient.publish(externalUserId
	                + "/notifications", FacebookType.class,
	                Parameter.with("template", message),
	                Parameter.with("href", "fbWantsThisLink.html?butIDontNeedIt=123"));
	        logger.info("FACEBOOK--Notificacion exitosa!!");
	    } catch (FacebookOAuthException e) {
	    	logger.severe("Error al intentar crear una notificacion al usuario: "+e.getErrorMessage());
	    	throw new FacebookException("Error al intentar crear una notificacion al usuario: "+e.getErrorMessage());
	    }
	}
	/**
	 * Publicar en el muro del usuario. 
	 * @param externalUserId facebook id.
	 * @param viaje {@link Viaje} Informacion del viaje a publicar en el muro.
	 * 
	 * @throws En caso que Facebook devuelva un error {@link FacebookException}.
	 *
	 */
	@SuppressWarnings("deprecation")
	public void publicarEnMuro(String externalUserId, Viaje viaje) {
		logger.info("FACEBOOK--Publicando en el muro el viaje del usuario...");
		AccessToken appAccessToken = new DefaultFacebookClient().obtainAppAccessToken(APP_KEY, APP_SECRET);
	    FacebookClient facebookClient = new DefaultFacebookClient(appAccessToken.getAccessToken());
	    try {
	        facebookClient.publish(externalUserId
	                + "/feed", FacebookType.class,
	                Parameter.with("message", new ViajeParseadoParaFacebook(viaje).comoString()));
	        logger.info("FACEBOOK--Se Publico con exito!!");
	    } catch (FacebookOAuthException e) {
	    	logger.severe("Error al intentar publicar el viaje en el muro del usuario: "+e.getErrorMessage());
	        throw new FacebookException("Error al intentar publicar el viaje en el muro del usuario: "+e.getErrorMessage());
	    }
	}
	/**
	 * Validar identidad del usuario contra Facebook. 
	 * @param fbToken token devuelto por facebook.
	 * 
	 * @throws En caso que facebook devuelva un error {@link FacebookAuthException}.
	 *
	 */
	@SuppressWarnings("deprecation")
	public void validarIdentidad(String fbToken) {
		logger.info("FACEBOOK--Validando Token...");
		AccessToken appAccessToken = new DefaultFacebookClient().obtainAppAccessToken(APP_KEY, APP_SECRET);
	    FacebookClient facebookClient = new DefaultFacebookClient(appAccessToken.getAccessToken());
	    if(!facebookClient.debugToken(fbToken).isValid()){
	    	logger.severe("TokenInvalido");
	    	throw new FacebookAuthException("Token Invalido!!");
	    }
	    logger.info("FACEBOOK--Token Valido.");
	}
	
	@SuppressWarnings("deprecation")
	public String obtenerAccessToken() {
		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(APP_KEY, APP_SECRET);
		return accessToken.getAccessToken();
	}
}
