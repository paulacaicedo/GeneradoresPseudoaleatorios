
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
 

public class pseudoaleatorio {
	
	
	static int iteracion;
	static float[] pseudoaletorios;
	static int cont = 0;
	static int numero_pseudoaleatorio_inicial;
	
	public static double Media(float[] aleatorios) {
		
		float temp = 0;
		for (int i = 0; i < aleatorios.length; i++) {
			temp = temp + aleatorios[i];
		}
		
		double media = temp / aleatorios.length;	
		
		return media;
	}
	
	public static double Varianza(float[] aleatorios) {
		
		
		double media = Media(aleatorios);
		double varianza = 0;
		for(int i = 0 ; i < aleatorios.length; i++){
			   double rango;
			   rango = Math.pow(aleatorios[i] - media,2f);
			   varianza = varianza + rango;
		}
		
		varianza = varianza / (aleatorios.length-1);
			
		return varianza;
	}
	
	public static void pruebasMedia(float[] aleatorios) {
		
		int cant_datos = aleatorios.length;
		double confianza= .95;
		double alfa = 1-confianza;
		
		NormalDistribution nd = new NormalDistribution();
		DescriptiveStatistics estadistica = new DescriptiveStatistics();
		
		
		double media = Media(aleatorios);
		
		for (int i=0; i<cant_datos; i++){
			estadistica.addValue(aleatorios[i]);
		}
		
		double z= nd.inverseCumulativeProbability(1 - alfa/2 );
		
		double li = 0.5 - z * (1/Math.sqrt(12*estadistica.getN()));
		double ls = 0.5 + z * (1/Math.sqrt(12*estadistica.getN()));
		
		System.out.println("\n---Prueba de medias---");
		System.out.println("Media: " + media);
		System.out.println("Limite inferior: "+ li);
		System.out.println("Limite superior: " + ls);
		
		if(media > li && media < ls) {
			System.out.println("Pasa la prueba de media");
		}else {
			System.out.println("No Pasa la prueba de media");
		
		}
		
	}
	
	
	public static void pruebasVarianza(float[] aleatorios) {
		
		int cant_datos = aleatorios.length;
		int grados_libertad = cant_datos-1;
		double confianza= .95;
		double alfa = 1-confianza;
		
		 
		ChiSquaredDistribution chi = new ChiSquaredDistribution(grados_libertad);
		
		double varianza = Varianza(aleatorios);
		
		 
		double li = chi.inverseCumulativeProbability(alfa/2)/(12*grados_libertad);
		double ls = chi.inverseCumulativeProbability(1-alfa/2)/(12*grados_libertad);
		
		System.out.println("\n---Prueba de varianza---");
		System.out.println("Varianza: "+ varianza);
		System.out.println("Limite inferior: " + li);
		System.out.println("Limite superior: " + ls);
		
		if(varianza > li && varianza < ls) {
			System.out.println("Pasa la prueba de varianza");
		}else {
			System.out.println("No Pasa la prueba de varianza");
		
		}
		
 
		
	}
	
	public static void pruebaGrafica(float[] aleatorios) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
		
		for (int i = 0; i < aleatorios.length; i++) {
			dataset.addValue(aleatorios[i], "numerosPseudoaleatorios",String.valueOf(i));
		}
		
		
		final JFreeChart chart = ChartFactory.createLineChart(
	           "Comportamiento",
	           "Cantidad", 
	           "Números Pseudoaleatorios", 
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	        );

		final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        JFrame ventana = new JFrame("");
        ventana.setVisible(true);
        ventana.setSize(800,600);
        ventana.add(chartPanel);
        
 
		
	}
	
	public static void pruebasCorridas(float[] aleatorios) {
		ArrayList<Integer> bits = new ArrayList<>();
		
		int corridas,dato;
		double confianza= .95;
		double alfa = 1-confianza;
		
		 
	    for (int i=1; i<aleatorios.length; i++){
	        if (aleatorios[i]<=aleatorios[i-1]){
	            bits.add(0);
	        }
	        else{
	            bits.add(1);
	        }                
	    }
	    
	    corridas = 1;
	    dato= bits.get(0);
	    
	    for (int i=1; i<bits.size(); i++){
	        if (bits.get(i) != dato){
	            corridas++;
	            dato = bits.get(i);
	        }
	    }
	    
	    
	    System.out.println("\n---Prueba de Corridas---");
	    System.out.println("Corridas " + corridas);
	    
	    
	    double esperado = (2*aleatorios.length-1)/ (double)3;
	    System.out.println("Valor Esperado: " +esperado);        
	    double varianza = (16*aleatorios.length-29)/(double) 90;
	    System.out.println("Varianza: " + varianza);
	    double z= Math.abs((corridas-esperado)/Math.sqrt(varianza));
	    System.out.println("Z=" + z);
	    
	    NormalDistribution normal = new NormalDistribution();
	    double  zn =  normal.inverseCumulativeProbability(1-alfa/2);
	    
	   //COMPARAMOS: SI ES MAYOR MI valor Z AL DE LA TABLA, NO PASA
	    if (z < zn){
	        System.out.println("No se rechaza que son independientes. " );
	    }
	    else{
	        System.out.println("No Pasa la prueba de corridas");
	    }
	    
		
	}
	
	
	public static boolean isprime(int numero) {
		boolean prime = true;
		if(numero <= 1) {
			prime = false;
			return prime;
		}else {
			for (int i = 2; i < numero/2; i++) {
				if((numero%i)==0){
					prime=false;
					break;
					
				}
				
			}
			
			return prime;
		}
	}
	
	
	
	public static float[] multiplicadorConstante(int iteracion, int semilla, int constante ) {
		
		
		if(iteracion == 0) {
			return pseudoaletorios;
			
		}else {
			
			int temp = constante * semilla;
			int mitad = String.valueOf(temp).length() /2;
			String numero =  String.valueOf(temp).substring(mitad-2,mitad+2);
			
			if(Integer.parseInt(numero) == 0) {return pseudoaletorios;}
			
			
			pseudoaletorios[cont] = Float.valueOf("0."+numero);
			
			cont++;
			iteracion = iteracion - 1;		
			multiplicadorConstante(iteracion,Integer.parseInt(numero),constante);
			
			
			
		}
		
		
		
		
		return pseudoaletorios;
		
		
		
	}
	
	public static float[] AlgoritmoLineal(int iteracion, int k, int c, int g, float semilla ) {
		
		

		if(iteracion == 0) {
			
			return pseudoaletorios;
			
		}else {
			
			
			int a = 1 + (4*k);
			float m= (int) Math.pow(2, g);
			
			float x = ((a*(int)semilla)+c) % (int)m;
			
			float r = x/(m-1);
			
			cont++;
			iteracion = iteracion - 1;		
			pseudoaletorios[cont] = (float) r;
			
			AlgoritmoLineal(iteracion,k,c,g,x);
				
			
		}
		

	
		return pseudoaletorios;
		
	}
	
	 
	
	
	public static float[] AlgoritmoCongruencial(int iteracion, int k,  int g, float semilla) {
		
		 
		if(iteracion == 0) {
			
			return pseudoaletorios;
			
		}else {
			

			int a = 5 + (8*k);
			float m= (int) Math.pow(2, g);
			
			float x = ((a*(int)semilla)) % (int)m;
			
			float r = x/(m-1);
			
			if(cont == 0) {
				 
				numero_pseudoaleatorio_inicial = (int) x;
			 
			}else {
				
				 
				if(numero_pseudoaleatorio_inicial==(int)x && cont!=0) {
					 
					return pseudoaletorios; 
				}
				
			}
			
			
			cont++;
			iteracion = iteracion - 1;		
			pseudoaletorios[cont] = (float) r;
			
			 
			
			AlgoritmoCongruencial(iteracion,k,g,x);
			
			
		}
		
		return pseudoaletorios;
		
	}
	
	public static float[] AlgoritoAditivo(List<Integer> lista,int m) {
		
		System.out.println(cont);
		System.out.println(lista.get(lista.size()-1));
		
		float x = (lista.get(lista.size()-1)+lista.get(cont)) %  m;
		lista.add((int) x);
		
		float r = x/(m-1);
		
		if(cont == 0) {
			 
			numero_pseudoaleatorio_inicial = (int) x;
		 
		}else {
			
			 
			if(numero_pseudoaleatorio_inicial==(int)x && cont!=0) {
				return pseudoaletorios; 
			}
			
		}
		
		
		cont++;	
		pseudoaletorios[cont] = (float) r;
		
		AlgoritoAditivo(lista,m);
		
		return pseudoaletorios;
		
	}
	
	public static float[] AlgoritmoCongruencialCuadratico(int iteracion, int g, int a, int c,int b,int semilla) {
		
		System.out.println(semilla);
		
		if(iteracion == 0) {
			
			return pseudoaletorios;
			
		}else {
			
			
			float m= (int) Math.pow(2, g);
			float x =( (a*(int)Math.pow(semilla, 2)) + (b*semilla) + c) %  (int)m;
			
			
			float r = x/(m-1);
			
			if(cont == 0) {
				numero_pseudoaleatorio_inicial = (int) x;
			 
			}else {
				
				if(numero_pseudoaleatorio_inicial==(int)x && cont!=0) {
					 
					return pseudoaletorios; 
				}
				
			}
			
			
			cont++;
			iteracion = iteracion - 1;		
			pseudoaletorios[cont] = (float) r;
			
			 AlgoritmoCongruencialCuadratico(iteracion,g,a,c,b,(int)x);
			
				
		
		}
			
		
		
		
		
		 
		
		return pseudoaletorios;
		
	}
	
	
	
	public static float[] cuadradosMedios(int iteracion, int semilla ) {
		 
		
		if(iteracion == 0) {
			return pseudoaletorios;
			
		}else {

			int temp = (int) Math.pow(semilla, 2);
		  
			
			int mitad = String.valueOf(temp).length() /2;
			String numero =  String.valueOf(temp).substring(mitad-2,mitad+2);

			if(Integer.parseInt(numero) == 0) {return pseudoaletorios;}
			
			
			pseudoaletorios[cont] = Float.valueOf("0."+numero);
			
			cont++;
			iteracion = iteracion - 1;		
			cuadradosMedios(iteracion,Integer.parseInt(numero));				
			
			
		}
		
		return pseudoaletorios;
		
	}
	
	
	public static float[] productosMedios(int iteracion, int semilla0, int semilla1 ) {
		 
		String numero = "";
		
		if(iteracion == 0) {
			return pseudoaletorios;
			
		}else {
			
			int temp = semilla0 * semilla1;
			int mitad = String.valueOf(temp).length() /2;
			numero =  String.valueOf(temp).substring(mitad-2,mitad+2);
			  
			if(Integer.parseInt(numero) == 0) {return pseudoaletorios;}
			
			pseudoaletorios[cont] = Float.valueOf("0."+numero);
			
			cont++;
			iteracion = iteracion - 1;		
			productosMedios(iteracion,semilla1,Integer.parseInt(numero));				
			
			
		}
		
		return pseudoaletorios;
		
	}
	

	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in); 
		
		
		
		/*
		//CUADRADOS MEDIOS-----------------------------------------------------------------------
		
		
		System.out.println("Ingrese el numero semilla (Digito > 3) :");
		int semilla = sc.nextInt();
		
		while (String.valueOf(semilla).length() <= 3) {
			System.out.println("Ingrese el numero semilla (Digito > 3) :");
			semilla = sc.nextInt();
		}
		
		System.out.println("¿Cuantos numeros pseudoaleatorios?: ");
		iteracion = sc.nextInt();
		pseudoaletorios = new float[iteracion+1];
		
		cont = 0;
		cuadradosMedios(iteracion+1,semilla);
		
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			System.out.println(pseudoaletorios[i]);
			
		}
		
		//PRUEBAS DE VERIFICACION DE NUMEROS 
		
		
		pruebasMedia(pseudoaletorios);
		pruebasVarianza(pseudoaletorios);
		pruebasCorridas(pseudoaletorios);
		pruebaGrafica(pseudoaletorios);
		
		
		*/
		
		
		
		/*
		 
		//PRODUCTOS MEDIOS  ----------------------------------------------------------------------
		
		System.out.println("Ingrese el numero semilla0 (Digito > 3) :");
		int semilla0 = sc.nextInt();
		
		while (String.valueOf(semilla0).length() <= 3) {
			System.out.println("Ingrese el numero semilla (Digito > 3) :");
			semilla0 = sc.nextInt();
		}
		
		System.out.println("Ingrese el numero semilla0 (Digito > 3) :");
		int semilla1 = sc.nextInt();
		
		while (String.valueOf(semilla1).length() <= 3) {
			System.out.println("Ingrese el numero semilla (Digito > 3) :");
			semilla1 = sc.nextInt();
		}
		
		
		System.out.println("¿Cuantos numeros pseudoaleatorios?: ");
		iteracion = sc.nextInt();
		pseudoaletorios = new float[iteracion+1];
		
		cont = 0;
		productosMedios(iteracion+1,semilla0,semilla1);
		
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			System.out.println(pseudoaletorios[i]);
			
		}
		
		//PRUEBAS DE VERIFICACION DE NUMEROS 
		
		
		pruebasMedia(pseudoaletorios);
		pruebasVarianza(pseudoaletorios);
		pruebasCorridas(pseudoaletorios);
		pruebaGrafica(pseudoaletorios);
		
		*/
 
		
		/*
		//MULTIPLICADOR CONSTANTE  ----------------------------------------------------------------------
		
		System.out.println("Ingrese el numero semilla (Digito > 3) :");
		int semilla_i = sc.nextInt();
				
		while (String.valueOf(semilla_i).length() <= 3) {
			System.out.println("Ingrese el numero semilla (Digito > 3) :");
			semilla_i = sc.nextInt();
		}
				
		System.out.println("Ingrese el numero constante(Digito > 3) :");
		int constante = sc.nextInt();
				
		while (String.valueOf(constante).length() <= 3) {
			System.out.println("Ingrese el numero semilla (Digito > 3) :");
			constante = sc.nextInt();
		}
				
				
		System.out.println("¿Cuantos numeros pseudoaleatorios?: ");
		iteracion = sc.nextInt();
		pseudoaletorios = new float[iteracion+1];
				
		cont = 0;
		multiplicadorConstante(iteracion, semilla_i, constante);
				
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			System.out.println(pseudoaletorios[i]);
		}
		
		*/
		
		
		/*
		//ALGORITMO LINEAL  ----------------------------------------------------------------------
		
		
		System.out.println("Ingrese el numero semilla:");
		int semilla_1 = sc.nextInt();
		
		System.out.println("Ingrese el numero k:");
		int k = sc.nextInt();
		
		System.out.println("Ingrese el numero c (Numero Primo) :");
		int c = sc.nextInt();
		
		while(isprime(c)==false) {
			System.out.println("Ingrese el numero c (Numero Primo) :");
			c = sc.nextInt();
		}
		
		System.out.println("Ingrese el numero g  :");
		int g = sc.nextInt();
		
		
		int m= (int) Math.pow(2, g);
	
		 
		pseudoaletorios = new float[m+1];
				
		cont = 0;
		AlgoritmoLineal(m,k,c,g,semilla_1); 
				
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			System.out.println(pseudoaletorios[i]);
		}
		
		*/
		
		
		/*
		//ALGORITMO CONGRUENCIAL MULTIPLICATIVO  ----------------------------------------------------------------------
		
		System.out.println("Ingrese el numero semilla impar:");
		int x_1 = sc.nextInt();
		
		while(x_1%2==0) {
			
			System.out.println("Ingrese el numero semilla impar:");
			x_1 = sc.nextInt();
			
		}
		
		System.out.println("Ingrese el numero k:");
		int k = sc.nextInt();
		
			
		System.out.println("Ingrese el numero g  :");
		int g = sc.nextInt();
		
		
		int m= (int) Math.pow(2, g);
	
		 
		pseudoaletorios = new float[m+1];
				
		cont = 0;
		AlgoritmoCongruencial(m,k,g,x_1); 
				
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			
			 
			if(pseudoaletorios[i]==0.0) {
				continue;
			}else {
				System.out.println(pseudoaletorios[i]);
			}
			 
		}
		*/

		/*
		
		//ALGORITMO ADITIVO  ----------------------------------------------------------------------
		
		List<Integer> secuencia = new ArrayList<Integer>();
		
		System.out.println("¿Cuantos numeros va a digitar?: ");
		int x = sc.nextInt();
		
		for (int i = 0; i < x; i++) {
			secuencia.add(sc.nextInt());
			
		}
		
		System.out.println("Digite numero m: ");
		int m = sc.nextInt();
		
		cont =0 ;
		pseudoaletorios = new float[m+1];
		
		AlgoritoAditivo(secuencia,m);
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			
			 
			if(pseudoaletorios[i]==0.0) {
				continue;
			}else {
				System.out.println(pseudoaletorios[i]);
			}
			 
		}
		
		*/
		
		//ALGORITMO CONGRUENCIAL CUADRATICO  ----------------------------------------------------------------------
		
		System.out.println("Digite la semilla ");
		int x_0= sc.nextInt();
		
		
		
		
		
		System.out.println("Digite el numero a par: ");
	    int a =sc.nextInt();
	    while(a%2!=0) {
			
			System.out.println("Ingrese el numero a par:");
			a = sc.nextInt();
			
		}
	    
	    System.out.println("Digite el numero b: ");
	    int b= sc.nextInt();
	    
	    
	    System.out.println("Digite el numero c impar: ");
	    int c =sc.nextInt();
	    while(c%2==0) {
			
			System.out.println("Ingrese el numero c impar:");
			c = sc.nextInt();
			
		}
	    
	    System.out.println("Digite el numero g");
	    int g =sc.nextInt();
	    
	    
	    int m= (int) Math.pow(2, g);

		pseudoaletorios = new float[m+1];
				
		cont = 0;
		AlgoritmoCongruencialCuadratico(m, g, a, c, b, x_0);
	    
	    
		System.out.println("Numero Pseudoaleatorios");
		System.out.println(cont);
		for (int i = 0; i < pseudoaletorios.length; i++) {
			
			 
			if(pseudoaletorios[i]==0.0) {
				continue;
			}else {
				System.out.println(pseudoaletorios[i]);
			}
			 
		}
		
		
		
		
	}

}
