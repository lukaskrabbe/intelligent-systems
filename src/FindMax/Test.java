package FindMax;

public class Test {

	/**
	 * The main method.
	 * 
	 * @author Lukas Krabbe + Mohammad Abdulreda
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		

		System.out.println("Data_0: ");
		@SuppressWarnings("unused")
		Suche_lokale_Maxima Suche = new Suche_lokale_Maxima("data0.csv","label0.csv",-25,25,-25,25,10,12,8);
		System.out.println();
		
		System.out.println("Data_1: ");
		Suche = new Suche_lokale_Maxima("data1.csv","label1.csv",-25,25,-25,25,10,12,8);
		System.out.println();
		
		
		System.out.println("Data_2: ");
		Suche = new Suche_lokale_Maxima("data2.csv","label2.csv",-25,25,-25,25,10,12,8);
		System.out.println();
		

//		Suche_lokale_Maxima fm1 = new Suche_lokale_Maxima("data2.csv","label2.csv",-35,35,-35,35,10,12,8);
//		Suche_lokale_Maxima fm1 = new Suche_lokale_Maxima("data2.csv","label2.csv",-40,40,-40,40,10,12,8);
//		Suche_lokale_Maxima fm1 = new Suche_lokale_Maxima("data2.csv","label2.csv",-25,25,-25,25,10,2,8);
//		Suche_lokale_Maxima fm1 = new Suche_lokale_Maxima("data2.csv","label2.csv",-20,20,-20,20,10,12,8);
		
	}

}
