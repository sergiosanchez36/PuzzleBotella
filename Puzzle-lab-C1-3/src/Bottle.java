
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Bottle {
	
	private Stack<Portion> porciones = new Stack<Portion>();
	private int cantidadMax;
	
	public Bottle(List<Portion> porciones, int cantidadMax) {
		this.porciones = generateStack(porciones);
		this.cantidadMax = cantidadMax;
	}
	
	public Bottle() {
		
	}

	public Stack<Portion> getPorciones() {
		return this.porciones;
	}

	public int getColorTop() {
		int colour = 0;
		if (porciones.empty()) { 
			colour = -1;
		} else {
			colour = porciones.peek().getColor();
		}
		return colour;
	}
	
	public int getCantidadTop() {
		if(porciones.isEmpty()==true) {
			return 0;
		}else
		return porciones.peek().getQuantity();
	}
	
	@Override
	public String toString() {
		return "Botella [porciones=" + porciones + " color = " + getColorTop() + " cantidad = " + getCantidadTop() + "]";
	}

	public int getcantidadaMax() {
		return cantidadMax;
	}
	
	public int getCantidadLlenada() {
		int llenado = 0;
		for (int i = 0; i < porciones.size(); i++) {
			llenado += porciones.get(i).getQuantity();
		}
		return llenado;
	}
	
	public void setPorciones(Stack<Portion> porciones) {
		this.porciones = porciones;
	}
	public void addPorcion(Portion port) {
		porciones.add(port);
	}
	
	public void clearBottle() {
		porciones.clear();
	}
	
	public Stack<Portion> generateStack(List<Portion> portions) {
		Stack<Portion> stackPorciones = new Stack<Portion>();
		
		for (int i = portions.size()-1; i >= 0; i--) {
			stackPorciones.push(portions.get(i));
		}
		
		return stackPorciones;
	}
	
	public void pasarLiquido(int cantidad, Bottle botellaDestino) {
		int sum = 0;
		int cantidadDestino = 0;
		int numPortions = porciones.size();

		for(int i=numPortions-1; i>=0; i--) {
			sum += porciones.peek().getQuantity(); 
			
			if(cantidad >= sum) {
				addPortion(porciones.peek().getColor(), porciones.peek().getQuantity(), botellaDestino);
				porciones.pop();				
			}
			else {
				cantidadDestino =  porciones.peek().getQuantity() - (sum-cantidad);
				if(cantidadDestino>0) {
					addPortion(porciones.peek().getColor(), cantidadDestino, botellaDestino);
					
					porciones.peek().setQuantity(sum-cantidad);
					break;
				}
			}
		}
	}
	public void addPortion(int color, int cantidad, Bottle botellaDestino) {
		Stack<Portion> porcionesDestino = botellaDestino.getPorciones();
		if(porcionesDestino.isEmpty()==true) {
			Portion porcionMovida = new Portion(color, cantidad);
			porcionesDestino.add(porcionMovida);
		}else {
			Portion ultimaPorcion =  porcionesDestino.peek();
			
			if(ultimaPorcion.getColor() == color) {
				ultimaPorcion.setQuantity(cantidad + ultimaPorcion.getQuantity());
			}
			else {
				Portion porcionMovida = new Portion(color, cantidad);
				porcionesDestino.add(porcionMovida);
			}
		}
		
		
		
	}
	public int getnColores() {
		int numero = 0;
		List<Integer> colores = new ArrayList<Integer>();
		for(int i =0; i< this.getPorciones().size(); i++) {
			colores.add(this.getPorciones().get(i).getColor());
		}
		numero = colores.size();
		return numero;
	}
	
}
