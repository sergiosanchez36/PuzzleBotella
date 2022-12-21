import java.util.List;


public class Frontera {
	private List<Nodo> frontera;

	public Frontera(List<Nodo> frontera) {
		super();
		this.frontera = frontera;
	}

	public List<Nodo> getFrontera() {
		return frontera;
	}

	public void setFrontera(List<Nodo> frontera) {
		this.frontera = frontera;
	}
	
	public void pushN(Nodo n) {
		frontera.add(n);
		sort(frontera);
	}
	
	public Nodo popN() {
		Nodo n=frontera.get(0);
		frontera.remove(0);
		return n;
	}
	public void sort(List<Nodo> f1) {
		for(int i=0;i<f1.size();i++) {
			for(int j=0;j<f1.size();j++) {
				if(i!=j) {
					if(f1.get(i).getValor()<f1.get(j).getValor() && i>j) {
						Nodo aux=f1.get(i);
						f1.remove(i);
						f1.add(j,aux);
					}
					if(f1.get(i).getValor()>f1.get(j).getValor() && i<j) {
						Nodo aux=f1.get(j);
						f1.remove(j);
						f1.add(i,aux);
					}
					if(f1.get(i).getValor()==f1.get(j).getValor()) {
						if(f1.get(i).getId()<f1.get(j).getId() && i>j) {
							Nodo aux=f1.get(i);
							f1.remove(i);
							f1.add(j,aux);
						}
						if(f1.get(i).getId()>f1.get(j).getId() && i<j) {
							Nodo aux=f1.get(j);
							f1.remove(j);
							f1.add(i,aux);
						}
					}
				}
			}
		}
	}
}