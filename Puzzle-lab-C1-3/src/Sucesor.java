import java.util.Stack;

public class Sucesor {
	private Accion accion;
	private Estado nuevo_estado;
	private double coste;
	
	public Sucesor(Accion action, Estado nuevo_estado, double coste) {
		this.accion = action;
		this.nuevo_estado = nuevo_estado;
		this.coste = coste;
	}
	public Sucesor() {
		
	}

	public Accion getAccion() {
		return accion;
	}
	public void setAccion(Accion accion) {
		this.accion = accion;
	}
	public Estado getNuevoEstado() {
		return nuevo_estado;
	}
	public void setNuevoEstado(Estado nuevo_estado) {
		this.nuevo_estado = nuevo_estado;
	}
	public double getCoste() {
		return coste;
	}
	public void setCoste(double coste) {
		this.coste = coste;
	}

}