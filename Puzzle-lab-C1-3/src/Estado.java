import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Estado {
	private ArrayList<Bottle> botellas;
	
	public Estado(ArrayList<Bottle> botellas) {
		this.botellas = botellas;
	}
	
	public Estado() {
	}

	public ArrayList<Bottle> getBotellas() {
		return botellas;
	}
	
	public void setBottles(ArrayList<Bottle> botellas) {
		this.botellas = botellas;
	} 
	public Estado Accion(int BotellaOrigen, int BotellaDestino, int cantidad) {		
		Estado nuevoEstado = new Estado();
		List<Bottle> aux=new ArrayList<>();
		cloneList(botellas,aux);
		nuevoEstado.setBottles((ArrayList<Bottle>) aux);		
				
		if(cantidad > 0 && ES_AccionPosible(nuevoEstado.getBotellas().get(BotellaOrigen), nuevoEstado.getBotellas().get(BotellaDestino), cantidad)) {
			nuevoEstado.getBotellas().get(BotellaOrigen).pasarLiquido(cantidad, nuevoEstado.getBotellas().get(BotellaDestino));
			return nuevoEstado;
		}else {
			ArrayList<Bottle> b1=new ArrayList<Bottle>();
			Estado estado = new Estado(b1);
		
			return estado;
		}	
	}
	
	public static boolean ES_AccionPosible(Bottle BotellaOrigen, Bottle BotellaDestino, int cantidad) {
		Boolean mismoColor=mismoColor(BotellaOrigen,BotellaDestino);
		int EspacioBotella = BotellaDestino.getcantidadaMax() - BotellaDestino.getCantidadLlenada();
		BotellaOrigen.getPorciones().peek().getColor();
				
		if(cantidad <= EspacioBotella && BotellaOrigen.getCantidadLlenada() >= cantidad && mismoColor) {
			return true;
			  }
		else 
			return false;
	}	
	
	private static Boolean mismoColor(Bottle BotellaOrigen, Bottle BotellaDestino) {
		boolean mismoColor=false;
		if(BotellaDestino.getCantidadLlenada()>0) {
			if(BotellaOrigen.getPorciones().peek().getColor()==BotellaDestino.getPorciones().peek().getColor()) {
				mismoColor=true;
			}
		}else
			mismoColor=true;
		return mismoColor;
	}

	public static void cloneList(List<Bottle> list1, List<Bottle> list2) {
		int maxSize = list1.get(0).getcantidadaMax();
		for (int i = 0; i < list1.size(); i++) {
			Stack<Portion> porciones = new Stack<Portion>();
			for (int j = list1.get(i).getPorciones().size()-1; j >= 0 ; j--) {
				int color = list1.get(i).getPorciones().get(j).getColor();
				int cantidad = list1.get(i).getPorciones().get(j).getQuantity();
				Portion p = new Portion(color, cantidad);
				porciones.push(p);
			}
			Bottle b = new Bottle(porciones,maxSize);
			list2.add(b);
		}
	}
}