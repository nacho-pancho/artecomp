package artecomp.base;

public interface Catalogable extends Configurable,Cloneable {
	public Catalogable nuevaInstancia();
	public Object clone();
}
