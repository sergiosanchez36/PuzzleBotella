
public class Portion {
	
	private int color;
	private int cantidad;
	
	public Portion(int color, int cantidad) {
		this.color = color;
		this.cantidad = cantidad;
	}
	public Portion() {
		// TODO Auto-generated constructor stub
	}
	public int getColor() {
		return color;
	}
	
	public int getQuantity() {
		return cantidad;
	}
	
	public void setQuantity(int newCantidad) {
		cantidad = newCantidad;
	}
	
	public void setColor(int newColor) {
		color = newColor;
	}
	
	public void decrease() {
		this.cantidad--;
	}
	
	public void increase() {
		this.cantidad++;
	}
}
