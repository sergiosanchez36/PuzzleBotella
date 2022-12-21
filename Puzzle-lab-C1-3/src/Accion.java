public class Accion {
	private int BotellaOrigen;
	private int BotellaDestino;
	private int cantidad;
	
	public Accion(int BotellaOrigen, int BotellaDestino, int cantidad) {
		super();
		this.BotellaOrigen = BotellaOrigen;
		this.BotellaDestino = BotellaDestino;
		this.cantidad = cantidad;
	}
	public Accion() {
		// TODO Auto-generated constructor stub
	}
	public int getBotellaOrigen() {
		return BotellaOrigen;
	}
	public void setBotellaOrigen(int BotellaOrigen) {
		this.BotellaOrigen = BotellaOrigen;
	}
	public int getBotellaDestino() {
		return BotellaDestino;
	}
	public void setBotellaDestino(int BotellaDestino) {
		this.BotellaDestino = BotellaDestino;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}