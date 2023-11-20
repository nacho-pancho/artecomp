package artecomp.base;
/**
 * Implementada por toda clase que desee utilizar el mecanismo de configuracion 
 * de esta plataforma.
 * 
 * @see Configuracion
 * @see Opcion 
 * @author nacho
 *
 */
public interface Configurable {
	/** @return una nueva instancia de la Configuracion definida para la clase que implemente este método. */
	public Configuracion getConfiguracion();
	
	/** redefine la Configuracion del objeto. 
	 */
	public void setConfiguracion(Configuracion conf);

}
