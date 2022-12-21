
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.io.BufferedWriter;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

	final static String FILE = "data/p0.json";
	final static Scanner TECLADO = new Scanner (System.in);
	
	public static void main(String[] args) {
	
		Problem p =importProblem(FILE);
		Estado e=generarEstado(p.getInitState(),p.getBottleSize());
		menu(e);
		
	}
	public static float calcularHeuristica(Nodo nodo) {
		float heuristica = 0;
		ArrayList<Bottle> listaBotellas = nodo.getEstado().getBotellas();
		ArrayList<Integer> listaVisitados = new ArrayList<Integer>();
		
		for(int i=0; i<listaBotellas.size();i++) {
			boolean introducirBotella = true;
			Stack<Portion> liquidos = listaBotellas.get(i).getPorciones();
			int numLiquidos = liquidos.size();
			if(i==0) {
				if(numLiquidos == 0) {
					heuristica += 1;
				}
				else{
					heuristica += numLiquidos;
					listaVisitados.add(i);
				}
				
			}
			else if(liquidos.isEmpty()) {
				heuristica += 1; 
			}
			else {
				int colorTopPila = liquidos.peek().getColor();
				boolean seguir = true;
				for(int j=0;j<listaVisitados.size() && seguir;j++) {
					if(!listaBotellas.get(listaVisitados.get(j)).getPorciones().isEmpty()) {
					int colorTopVisitada = listaBotellas.get(listaVisitados.get(j)).getPorciones().peek().getColor();
						if(colorTopPila == colorTopVisitada) {
							heuristica += 1;
							seguir = false;
							introducirBotella = false;
						}
					}
				}
				heuristica += numLiquidos;
				if(introducirBotella) {
					listaVisitados.add(i);
				}
			}
		}
		heuristica = heuristica - listaBotellas.size();
		return heuristica;
	}
	public static void menu(Estado e) {
		
		int opcion;
			
		List<Nodo> solucion;
		Nodo s;
		List <String> solucionS;
		opcion=pedirOpcionMenu();		
			
		switch (opcion) {
		case 1:
			s=busquedaAnchura(e);
			solucion=generarSolucion(s);
			solucionS=stringSolucion(solucion);
			generarTxt(solucionS);
			System.out.println("Solución generada");
			break;
		case 2:
			s=busquedaProfundidad(e);
			solucion=generarSolucion(s);
			solucionS=stringSolucion(solucion);
			generarTxt(solucionS);
			System.out.println("Solución generada");
			break;
		case 3:
			s=busquedaUniforme(e);
			solucion=generarSolucion(s);
			solucionS=stringSolucion(solucion);
			generarTxt(solucionS);
			System.out.println("Solución generada");
			break;
		case 4:
			s=busquedaGreedy(e);
			solucion=generarSolucion(s);
			solucionS=stringSolucion(solucion);
			generarTxt(solucionS);
			System.out.println("Solución generada");
			break;
		case 5:
			
			s=busquedaA(e);
			solucion=generarSolucion(s);
			solucionS=stringSolucion(solucion);
			generarTxt(solucionS);
			System.out.println("Solución generada");
			break;
		
		}
		System.out.println("Gracias por usar nuestro programa...");	
	}
	public static int pedirOpcionMenu() {
		
		int opcion=0;

		System.out.println("\nIndique la estrategia a seguir:\n");
		System.out.println("1. BREADTH");
		System.out.println("2. DEPTH");
		System.out.println("3. UNIFORM");
		System.out.println("4. GREEDY");
		System.out.println("5. A");
		System.out.println("6. Salir");
	     
		opcion=leerEnteroValido(1,6);      	   
		
		return opcion;
	}

	static int leerEnteroValido(int desde, int hasta){
		System.out.println("Introduzca un entero entre "+desde+" y "+hasta);
		int numero=TECLADO.nextInt();
		while (numero<desde || numero>hasta){
			System.out.println("Número incorrecto. Introduzca un número entre "+desde+
					" y "+hasta);
			numero=TECLADO.nextInt();
		}
		return numero;
	}

	private static void generarTxt(List<String> solucion) {
		 try {
	            String ruta = "data/solucion.txt";
	            
	            File archivo = new File(ruta);
	            if (!archivo.exists()) {
	                archivo.createNewFile();
	            }
	            FileWriter fw = new FileWriter(archivo);
	            BufferedWriter bw = new BufferedWriter(fw);
	            for(int i=0;i<solucion.size();i++){
	            	
	                bw.write(solucion.get(i));
	                bw.newLine();

	            }
	            bw.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	private static List<String> stringSolucion(List<Nodo> solucion) {
		List<String> nodos= new ArrayList<String>();
		String txt;
		Nodo aux;
		for(int i=solucion.size()-1;i>=0;i--) {
			aux=solucion.get(i);
			if(aux.getPadre()==null) {
				txt="["+aux.getId()+"]["+aux.getCosto()+","+getEstadoHash(aux.getEstado().getBotellas())+",None,None,"
						+aux.getProfundidad()+","+aux.getHeuristica()+","+aux.getValor()+"]";
			}else
			txt="["+aux.getId()+"]["+aux.getCosto()+","+getEstadoHash(aux.getEstado().getBotellas())+","+aux.getPadre().getId()+",("
					+aux.getAccion().getBotellaOrigen()+","+aux.getAccion().getBotellaDestino()+","+aux.getAccion().getCantidad()+"),"
					+aux.getProfundidad()+","+aux.getHeuristica()+","+aux.getValor()+"]";
			nodos.add(txt);
		}
		return nodos;
	}
	private static List<Nodo> generarSolucion(Nodo s) {
		Nodo aux=s;
		List<Nodo> solucion=new ArrayList<Nodo>();
		solucion.add(aux);
		
		do {		
			aux=aux.getPadre();
			solucion.add(aux);
			
		}while(aux.getId()!=0);
		
		return solucion;
	}
	public static Problem importProblem(String filename) {
		BufferedReader br;
		Problem problema=null;
		try {
			br = new BufferedReader(new FileReader(filename));
			problema= new Gson().fromJson(br,Problem.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return problema;
	}
	public static Estado generarEstado(String botellas,int maximo){
		
		String array;
		JsonParser jsonParser = new JsonParser();
		JsonArray arrayBottles;
		JsonArray jsonBottle;
		JsonArray attr;
		
		ArrayList<Bottle> bottleList = new ArrayList<Bottle>();
		array = botellas;

		arrayBottles = (JsonArray) jsonParser.parse(array);
		for (Object obj1 : arrayBottles) {
			jsonBottle = (JsonArray) obj1;

			List<Portion> portions = new ArrayList<Portion>();
			
			for (Object obj2 : jsonBottle) {
				attr = (JsonArray) obj2; 
				Portion auxPortion = new Portion(attr.get(0).getAsInt(), attr.get(1).getAsInt());
				portions.add(auxPortion);						
			}
			
			Bottle b = new Bottle(portions,maximo);
			bottleList.add(b);

		}
		Estado state = new Estado(bottleList);		
		
		return state;
	}

	
	public static ArrayList<Sucesor> sucesores(Estado estadoInic) {
		ArrayList<Sucesor> nAcciones = new ArrayList<Sucesor>();
		Estado nEstado = new Estado();	
		Estado clon = new Estado();
		List<Bottle> original =new ArrayList<>() ;
		Sucesor succesor;
		List<Bottle> aux=new ArrayList<>();
		List<Bottle> aux2=new ArrayList<>();
		List<Bottle> aux3=new ArrayList<>();
		original=estadoInic.getBotellas();
		
	    clonarLista(original,aux);
	    clon.setBottles((ArrayList<Bottle>) aux);
	

		for(int i=0; i<estadoInic.getBotellas().size(); i++) {
			int cantidad = estadoInic.getBotellas().get(i).getCantidadTop();
			
			for(int j=0; j<estadoInic.getBotellas().size(); j++) {
				if(i!=j) {
						
					nEstado = clon.Accion(i, j, cantidad);
					
					    if(!nEstado.getBotellas().isEmpty()) {
					    	aux2=nEstado.getBotellas();
							clonarLista(aux2,aux3);
							
					    	
							succesor = generarSucesor(aux3,i,j,cantidad);
							//clonarSucesor(succesor,clonar);
							nAcciones.add(succesor);
							
					    }
					    aux3.clear();
				}
			}
		}
					
		return nAcciones;	
	}
	public static Sucesor generarSucesor(List<Bottle> newEstado, int botellaOrigen, int botellaDestino, int cantidad) {
		List<Bottle> copiaEstado = new ArrayList<Bottle>();
		clonarLista(newEstado, copiaEstado);
		Estado estado=new Estado((ArrayList<Bottle>) copiaEstado);
		Accion action = new Accion(botellaOrigen, botellaDestino, cantidad);
		Sucesor sucesor = new Sucesor( action,estado, 1.0);

		return sucesor;
	}


	public static void clonarLista(List<Bottle> list1, List<Bottle> list2) {
		int maxSize = list1.get(0).getcantidadaMax();
		for (int i = 0; i < list1.size(); i++) {
			Stack<Portion> porciones = new Stack<Portion>();
			for (int j = list1.get(i).getPorciones().size()-1; j >= 0 ; j--) {
				int color = list1.get(i).getPorciones().get(j).getColor();
				int cantidad = list1.get(i).getPorciones().get(j).getQuantity();
				Portion porcion = new Portion(color, cantidad);
				porciones.push(porcion);
			}
			Bottle botella = new Bottle(porciones,maxSize);
			list2.add(botella);
		}
	}
	public static int expandir (Nodo nodo,Frontera frontera,int ultimoNodo,String estrategia){
		ArrayList<Sucesor> nuevas_acciones = sucesores(nodo.getEstado());
		for(int i=0; i<nuevas_acciones.size(); i++) {
			ultimoNodo++;
			Nodo n =new Nodo();
			
			n.setId(ultimoNodo); //el metodo expand tiene una variable que llama, tambien la devuelve despues
			n.setPadre(nodo);
			n.setEstado(nuevas_acciones.get(i).getNuevoEstado());
			n.setAccion(nuevas_acciones.get(i).getAccion());
			n.setProfundidad(nodo.getProfundidad()+1);
			n.setHeuristica(calcularHeuristica(n));
			n.setCosto(nodo.getCosto()+1.0);
			
			// para mostrar todos
			//se pone el valor en funcion de la estrategia de busqueda que se esta aplicando
			if(estrategia.equals("BREADTH")) {
				n.setValor(n.getProfundidad());
			}
			if(estrategia.equals("DEPTH")) {
				n.setValor(1/(n.getProfundidad()+1));
			}
			if(estrategia.equals("UNIFORM")) {
				n.setValor(n.getCosto());
			}
			if(estrategia.equals("A")) {
				
				n.setValor(n.getHeuristica()+n.getCosto());
				
			}
			if(estrategia.equals("GREEDY")) {
				
				n.setValor(n.getHeuristica());
			}
			
			frontera.pushN(n);
			System.out.println(n.toString());
			
			/*if(n.getId() == 685) {
				System.out.println(n.getId());
				System.out.println(n.getEstado());
				System.out.println(getEstadoHash(n.getEstado().getBotellas()));
				
			}*/
		}
		
		return ultimoNodo;
	}//16f2
	
	public static boolean esObjetivo(Estado estado) {
		boolean esObjetivo = true;
		
		List<Integer>colores = new ArrayList<Integer>();
		for(int i=0; i < estado.getBotellas().size() && esObjetivo; i++) {
			
			
			if(!estado.getBotellas().get(i).getPorciones().isEmpty()) {
				if(estado.getBotellas().get(i).getnColores()!=1) {
					esObjetivo = false;
					
					
				}else {
					boolean lista=comprobarColor(colores,estado.getBotellas().get(i).getPorciones().peek().getColor());
					if(lista) {
						esObjetivo = false;
					}else
						colores.add(estado.getBotellas().get(i).getPorciones().peek().getColor());
				}
					
				
			}
		}
		
		return esObjetivo;
	}
	
	private static boolean comprobarColor(List<Integer> lista, int color) {
		boolean existe=false;
		
		for(int i=0;i<lista.size();i++) {
			if(color==lista.get(i)) {
				existe = true;
			}
		}
		return existe;
		
	}
	public static Nodo busquedaAnchura(Estado e){
		Nodo solucion = null;
		Nodo aux;
		Estado estadoAux;
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(nodos);
		List<Bottle> estado;
		List<List<Bottle>> listaVisitados=new ArrayList<List<Bottle>>();
		
		int idNodo=0;
		frontera.pushN(new Nodo(0,0.0, e, null, null, 0,0,0));
		while(!frontera.getFrontera().isEmpty()){
			aux = frontera.getFrontera().get(0);
			aux.setHeuristica(calcularHeuristica(aux));
			
			frontera.getFrontera().remove(0);
			estadoAux = aux.getEstado();
			estado=estadoAux.getBotellas();
			List<Bottle> clone = new ArrayList<>();
			clonarLista(estado,clone);
				if(esObjetivo(estadoAux)) {
					solucion = aux;
					
					break;
				}else {
					if(listaVisitados.isEmpty()) {
						listaVisitados.add(clone);
						idNodo=expandir(aux,frontera,idNodo,"BREADTH");
					}else {
						if(isVisited(listaVisitados,clone,0)==false) {
							listaVisitados.add(clone);
							idNodo=expandir(aux,frontera,idNodo,"BREADTH");
						}
					}
				}
			}
	
			return solucion;
	}	
	
	public static Nodo busquedaProfundidad(Estado e){
		Nodo solucion = null;
		Nodo aux;
		Estado estadoAux;
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(nodos);
		List<Bottle> estado;
		List<List<Bottle>> listaVisitados=new ArrayList<List<Bottle>>();
		int Fmax = frontera.getFrontera().size();
		int idNodo=0;
		frontera.pushN(new Nodo(0,0.0, e, null, null, 0,0,1));
		while(!frontera.getFrontera().isEmpty()){
			aux = frontera.getFrontera().get(0);
			aux.setHeuristica(calcularHeuristica(aux));
			//para buscar por informacion exacta dentro del nodo
			if(aux.getId() == 1) {
			  System.out.println(aux.toString());
			  //para saber el color y cantidad de la porcion peek de cada botella 
			  System.out.println(aux.getEstado().getBotellas().toString());
			  //para saber el color y cantidad de las porciones que no sean peek(funciona como un index)
			  System.out.println(aux.getEstado().getBotellas().get(2).getPorciones().get(2).getColor() +"/"+ aux.getEstado().getBotellas().get(2).getPorciones().get(2).getQuantity());
			}
			//System.out.println(aux.toString());
			
			//Para calcular el tamaño maximo soportado por la frontera
			//if(frontera.getFrontera().size() > Fmax) {
				//Fmax = frontera.getFrontera().size();
			//}
			frontera.getFrontera().remove(0);
			estadoAux = aux.getEstado();
			estado=estadoAux.getBotellas();
			List<Bottle> clone = new ArrayList<>();
			clonarLista(estado,clone);
				if(esObjetivo(estadoAux)) {
					solucion = aux;
					
					break;
				}else {
					if(listaVisitados.isEmpty()) {
						listaVisitados.add(clone);
						idNodo=expandir(aux,frontera,idNodo,"DEPTH");
					}else {
						if(isVisited(listaVisitados,clone,0)==false) {
							listaVisitados.add(clone);
							idNodo=expandir(aux,frontera,idNodo,"DEPTH");
						}
					}
				}
			}
			//System.out.println(Fmax);
			return solucion;
	}

	public static Nodo busquedaUniforme(Estado e){
		Nodo solucion = null;
		Nodo aux;
		Estado estadoAux;
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(nodos);
		List<Bottle> estado;
		List<List<Bottle>> listaVisitados=new ArrayList<List<Bottle>>();
		
		int idNodo=0;
		frontera.pushN(new Nodo(0,0.0, e, null, null, 0,0,0));
		while(!frontera.getFrontera().isEmpty()){
			aux = frontera.getFrontera().get(0);
			aux.setHeuristica(calcularHeuristica(aux));
			
			frontera.getFrontera().remove(0);
			estadoAux = aux.getEstado();
			estado=estadoAux.getBotellas();
			List<Bottle> clone = new ArrayList<>();
			clonarLista(estado,clone);
				if(esObjetivo(estadoAux)) {
					solucion = aux;
					
					break;
				}else {
					if(listaVisitados.isEmpty()) {
						listaVisitados.add(clone);
						idNodo=expandir(aux,frontera,idNodo,"UNIFORM");
					}else {
						if(isVisited(listaVisitados,clone,0)==false) {
							listaVisitados.add(clone);
							idNodo=expandir(aux,frontera,idNodo,"UNIFORM");
						}
					}
				}
			}
	
			return solucion;

	}
	public static Nodo busquedaA(Estado e){
		Nodo solucion = null;
		Nodo aux;
		Estado estadoAux;
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(nodos);
		List<Bottle> estado;
		List<List<Bottle>> listaVisitados=new ArrayList<List<Bottle>>();
		//int Fmax = frontera.getFrontera().size();
		int idNodo=0;
		
		int visitas = 0;
		frontera.pushN(new Nodo(0,0.0, e, null, null, 0,0,0));
		while(!frontera.getFrontera().isEmpty()){
			
			aux = frontera.getFrontera().get(0);
			aux.setHeuristica(calcularHeuristica(aux));
			aux.setValor(calcularHeuristica(aux));
			if(aux.getId() == 685) {
				System.out.println(aux.toString());
				System.out.println(aux.getId());
				System.out.println(aux.getEstado());
				System.out.println(getEstadoHash(aux.getEstado().getBotellas()));
				
			}
			//if(aux.getId() == 685) {
				//System.out.println(aux.toString());
				//System.out.println(aux.getEstado());
				//System.out.println(aux.getEstado().getBotellas().toString());
			//	System.out.println(aux.getEstado().getBotellas().get(0).getPorciones().get(0).getColor() +"/"+ aux.getEstado().getBotellas().get(0).getPorciones().get(0).getQuantity());
			//}
			//System.out.println(aux.toString());
			
			//Para calcular el tamaño maximo soportado por la frontera
			//if(frontera.getFrontera().size() > Fmax) {
			//	Fmax = frontera.getFrontera().size();
			//}
			
			frontera.getFrontera().remove(0);
			estadoAux = aux.getEstado();
			estado=estadoAux.getBotellas();
			List<Bottle> clone = new ArrayList<>();
			
			clonarLista(estado,clone);
			
				if(esObjetivo(estadoAux)) {
					solucion = aux;
					
					break;
				}else {
					if(listaVisitados.isEmpty()) {
						
						listaVisitados.add(clone);
						idNodo=expandir(aux,frontera,idNodo,"A");
					}else {
						if(isVisited(listaVisitados,clone,0)==false) {
							listaVisitados.add(clone);
							idNodo=expandir(aux,frontera,idNodo,"A");
						}
					}
				}
			}
			//System.out.println(Fmax);
			return solucion;

	}
	public static Nodo busquedaGreedy(Estado e){
		Nodo solucion = null;
		Nodo aux;
		Estado estadoAux;
		ArrayList<Nodo> nodos = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(nodos);
		List<Bottle> estado;
		List<List<Bottle>> listaVisitados=new ArrayList<List<Bottle>>();
		int idNodo=0;
		frontera.pushN(new Nodo(0,0.0, e, null, null, 0,0,0));
		while(!frontera.getFrontera().isEmpty()){
			aux = frontera.getFrontera().get(0);
			aux.setHeuristica(calcularHeuristica(aux));
			aux.setValor(calcularHeuristica(aux));
			
			frontera.getFrontera().remove(0);
			estadoAux = aux.getEstado();
			estado=estadoAux.getBotellas();
			List<Bottle> clone = new ArrayList<>();
			clonarLista(estado,clone);
				if(esObjetivo(estadoAux)) {
					solucion = aux;
					
					break;
				}else {
					if(listaVisitados.isEmpty()) {
						listaVisitados.add(clone);
						idNodo=expandir(aux,frontera,idNodo,"GREEDY");
					}else {
						if(isVisited(listaVisitados,clone,0)==false) {
							listaVisitados.add(clone);
							idNodo=expandir(aux,frontera,idNodo,"GREEDY");
						}
					}
				}
			}
		
			return solucion;

	}
	public static boolean isVisited(List<List<Bottle>> listaVisitados, List<Bottle> estado, int iteracion) {
		boolean visitado = false;
		boolean estadoVisitado = true;
		int cantidadActual = 0;
		int cantidad = 0;
		List<Bottle> copiaEstado = new ArrayList<Bottle>();
		int visitas = 0;
		clonarLista(estado, copiaEstado);
		
		if(getEstadoHash(copiaEstado).equals("be1590686e4bdb9337897c9a4c8841e5")) {
			//System.out.println(copiaEstado.get(0));
			//System.out.println(estado.get(1).getPorciones().get(0).getColor()+ "/" + estado.get(1).getPorciones().get(0).getQuantity());
			visitas++;
		}
		
		for (int i = 0; i < estado.size(); i++) {
			cantidadActual += estado.get(i).getCantidadLlenada();
		}
		
		for (int i = 0; i < listaVisitados.size(); i++) {
			List<Bottle> auxList = listaVisitados.get(i);
			estadoVisitado = true;
			cantidad = 0;
			for (int j = 0; j < auxList.size() && estadoVisitado; j++) {
				Bottle botellaAux = auxList.get(j);
				if (botellaAux.getPorciones().size() == estado.get(j).getPorciones().size()) {
					for (int k = 0; k < botellaAux.getPorciones().size(); k++) {
						if (botellaAux.getPorciones().get(k).getColor() == estado.get(j).getPorciones().get(k).getColor() &&
								botellaAux.getPorciones().get(k).getQuantity() == estado.get(j).getPorciones().get(k).getQuantity()) {
								cantidad += botellaAux.getPorciones().get(k).getQuantity();
						}
					}
					
				} else {
					estadoVisitado = false;
				}
			}
			
			if (cantidad == cantidadActual) {
				visitado = true;
				break;
			}
		}
		//System.out.println(visitas);
		return visitado;
	}
	
	public static String getEstadoHash(List<Bottle> estado) {
		String estadoHash = "" + parseJsonBottles(estado), hashtext = "";
		byte[] mensageDigest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			mensageDigest = md.digest(estadoHash.getBytes());
			BigInteger no = new BigInteger(1, mensageDigest);
			hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashtext;
	}
	public static JsonArray parseJsonBottles(List<Bottle> estado) {
		
		JsonArray attr = new JsonArray(2), jsonBotella = new JsonArray(), arrayBotellas = new JsonArray();	
		for(Bottle botella: estado) {		
			Stack<Portion> porciones = botella.getPorciones();		
			Iterator<Portion> iterador = porciones.iterator();
			
			while(iterador.hasNext()) {		
				Portion porcion = porciones.pop();	
				
				attr.add(porcion.getColor());
				attr.add(porcion.getQuantity());
				
				jsonBotella.add(attr);		
				attr = new JsonArray(2);		
			}
			
			arrayBotellas.add(jsonBotella);		
			jsonBotella = new JsonArray();		
		}
		
		return arrayBotellas;
	}

}