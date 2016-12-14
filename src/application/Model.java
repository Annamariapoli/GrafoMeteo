package application;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import bean.Situazione;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	
	//CHIEDO:
	//grafo non orientato   non pesato
	//non semplice : puo avere loop, so no multigrafo:
	
	DefaultDirectedGraph<Integer, DefaultEdge> grafo = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
	List<Situazione> situazioniTorino = new LinkedList<Situazione>();
//	List<Integer> viciniPrimoLivello = new LinkedList<Integer>();	
//	List<Integer> viciniSecondoLivello = new LinkedList<Integer>();
	
	public List<Situazione> getSituazioni(){                                         //a torino nel 2013
		situazioniTorino = dao.getSituazioni();
		return situazioniTorino;
	}
	
	public void buildGraph(){
		List<Situazione> situazioniTorino = getSituazioni();                    //situazioni a torino nel 2013
		for(int i = 0; i<situazioniTorino.size(); i++ ){
			grafo.addVertex( situazioniTorino.get(i).getTMedia());            //prendo la tmedia x i vertici
		}
		for(Integer v : grafo.vertexSet()){                                  //x ogni vertice del grafo
			for(int i =0; i< situazioniTorino.size()-1; i++){
				grafo.addEdge(situazioniTorino.get(i).getTMedia(), situazioniTorino.get(i+1).getTMedia());
			}
		}
	}
	
	public boolean trovaTemperaturaMediaTraNodi(int temperatura){             //-->inutile
		for(Situazione s : getSituazioni() ){
			if(s.getTMedia() == temperatura){
				return true;
			}
		}
		 return false;
	}
	
	public List<Integer> getPrimiVicini(int temperatura){                        //se il grafo contiene questo vertice
		List<Integer> viciniPrimoLivello = new LinkedList<Integer>();
		if(grafo.containsVertex(temperatura)){
			viciniPrimoLivello = Graphs.neighborListOf(grafo, temperatura);   //prende i vicini di quel vertice
		}
		return viciniPrimoLivello;
	}
	
	public List<Integer> getSecondiVicini(int temperatura){
		List<Integer> secondiVicini = new LinkedList<Integer>();
		if(grafo.containsVertex(temperatura)){                    //controllo inutile
			for(Integer v: getPrimiVicini(temperatura)){          //x ogni primo vicino di temperatura
				secondiVicini.addAll(Graphs.neighborListOf(grafo, v));
			}	
		}
		return secondiVicini;
	}
	
	//2 livelli del grafo:
	
//	public List<Integer> getViciniPrimoLivello(){                            //dato un vertice vado a vedere i vicini (collegati tramite 1 arco)
//		for(Integer v : grafo.vertexSet()){                                 //x ogni vertice del grafo
//			 viciniPrimoLivello.addAll(Graphs.neighborListOf(grafo, v));   //calcolo i vicini e li metto nella lista
//		}
//		return viciniPrimoLivello;                  
//	}
	
//	public List<Integer> getViciniSecondoLivello(){		
//		for(Integer v : primiVicini()){
//			viciniSecondoLivello = Graphs.neighborListOf(grafo, v);         //2 mod diversi x aggiungere i vicini alla lista // è giusto?
//		}
//		return viciniSecondoLivello;
//	}

//data una temperaturaMedia vado a vedere a cosa è collegata (di 2 livelli- -> xke entro 2 giorni) 
//1 livello è l'arco xke collego ad 1 gg di distanza 
//so 2 livello sono 2 gg
}
