
public class Nodo {
	private int id;
	private double costo;
	private Estado estado;
	private Nodo padre;
	private Accion accion;
	private double profundidad;
	private double heuristica;
	private double valor;

	
	public Nodo(int id, double costo, Estado estado, Nodo padre, Accion accion, double profundidad, double heuristica,
			double valor) {
		super();
		this.id = id;
		this.costo = costo;
		this.estado = estado;
		this.padre = padre;
		this.accion = accion;
		this.profundidad = profundidad;
		this.heuristica = heuristica;
		this.valor = valor;

	}
	@Override
	public String toString() {
		if(this.accion!=null) {
			return "Nodo [id=" + id + ", costo=" + costo + ", estado=" + estado + ", nodo padre="+padre.getId()+", accion=( " + accion.getBotellaOrigen()
			+ accion.getBotellaDestino()+accion.getCantidad()+", profundidad=" + profundidad + ", heuristica=" + heuristica + ", valor=" + valor + "]";
		}else {
			return "Nodo [id=" + id + ", costo=" + costo + ", estado=" + estado +", profundidad=" + profundidad + ", heuristica=" + heuristica + ", valor=" + valor + "]";
		}
		
	}
	public Nodo() {
	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Nodo getPadre() {
		return padre;
	}
	public void setPadre(Nodo padre) {
		this.padre = padre;
	}
	public Accion getAccion() {
		return accion;
	}
	public void setAccion(Accion accion) {
		this.accion = accion;
	}
	public double getProfundidad() {
		return profundidad;
	}
	public void setProfundidad(double profundidad) {
		this.profundidad = profundidad;
	}
	public double getHeuristica() {
		return heuristica;
	}
	public void setHeuristica(double heuristica) {
		this.heuristica = heuristica;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

}