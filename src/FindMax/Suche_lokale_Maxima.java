package FindMax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lukas Krabbe + Mohammad Abdulreda
 * 
 * The Class Suche_lokale_Maxima.
 */
public class Suche_lokale_Maxima {

	private String Daten_Datei;
	private String Label_Datei;
	private BufferedReader br_CountData = null;
	private BufferedReader br_LoadData = null;
	private BufferedReader br_Label = null;
	private String line = "";
	private String Splite_Daten_durch_Komma = ",";
	private String[] Aufgesplittete_Daten_als_String_Array;

	private int AnzahlSpalten = 0; // -
	private int AnzahlZeilen = 0; // |
	private double[][] Werte_Array = null;

	private double Wert;
	private ArrayList<Double> Werte = new ArrayList<Double>();
	private ArrayList<Integer> yPOS = new ArrayList<Integer>();
	private ArrayList<Integer> xPOS = new ArrayList<Integer>();
	private ArrayList<Integer> yPOSLabel = new ArrayList<Integer>();
	private ArrayList<Integer> xPOSLabel = new ArrayList<Integer>();

	private int Zähl_Variable = 0;
	private boolean remove;

	private int tmp_X;
	private int tmp_Y;

	private double Durchschnittswerte = 0;

	

	/**
	 * Instantiates a new suche lokale maxima.
	 *
	 * @param Daten_Datei the daten datei
	 * @param Label_Datei the label datei
	 * @param Grenze_negativ_Y the grenze negativ Y
	 * @param Grenze_positiv_Y the grenze positiv Y
	 * @param Grenze_negativ_X the grenze negativ X
	 * @param Grenze_positiv_X the grenze positiv X
	 * @param Radius the radius
	 * @param Höhenunterschied_Max_Durchschnitt the höhenunterschied max durchschnitt
	 * @param Akzeptanz_Radius the akzeptanz radius
	 */
	public Suche_lokale_Maxima(String Daten_Datei, String Label_Datei,int Grenze_negativ_Y, int Grenze_positiv_Y, int Grenze_negativ_X, int Grenze_positiv_X, int Radius, int Höhenunterschied_Max_Durchschnitt, int Akzeptanz_Radius ) {
		super();
		this.Daten_Datei = Daten_Datei;
		this.Label_Datei = Label_Datei;
		Lade_Daten_aus_Dateien();
		Suche_lokale_Maxima_durch_Umfeldbetrachtung(Grenze_negativ_Y, Grenze_positiv_Y, Grenze_negativ_X, Grenze_positiv_X);
		Überprüfe_Lösche_doppelte_Maxima(Radius);
		Finde_Richtige_Maxima_mit_Durchschnittshöhe(Höhenunterschied_Max_Durchschnitt);
		Bestimme_FScore(Akzeptanz_Radius );
	}

	/**
	 * Lade daten aus dateien.
	 */
	private void Lade_Daten_aus_Dateien() {

		try {

			br_CountData = new BufferedReader(new FileReader(Daten_Datei));
			br_LoadData = new BufferedReader(new FileReader(Daten_Datei));
			br_Label = new BufferedReader(new FileReader(Label_Datei));
			int j = 0;

			while ((line = br_CountData.readLine()) != null) {
				AnzahlSpalten++;
				Aufgesplittete_Daten_als_String_Array = line
						.split(Splite_Daten_durch_Komma);

				if (Aufgesplittete_Daten_als_String_Array.length > AnzahlZeilen)
					AnzahlZeilen = Aufgesplittete_Daten_als_String_Array.length;

			}

			Werte_Array = new double[AnzahlSpalten][AnzahlZeilen];

			while ((line = br_LoadData.readLine()) != null) {

				Aufgesplittete_Daten_als_String_Array = line
						.split(Splite_Daten_durch_Komma);

				for (int i = 0; i <= Aufgesplittete_Daten_als_String_Array.length - 1; i++) {
					Werte_Array[j][i] = Double
							.parseDouble(Aufgesplittete_Daten_als_String_Array[i]);
				}
				j++;
			}

			while ((line = br_Label.readLine()) != null) {

				String[] Aufgesplittete_Daten_als_String_Array = line
						.split(Splite_Daten_durch_Komma);
				xPOSLabel.add((int)Double
						.parseDouble(Aufgesplittete_Daten_als_String_Array[1]));
				yPOSLabel.add((int)Double
						.parseDouble(Aufgesplittete_Daten_als_String_Array[0]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br_CountData != null && br_LoadData != null && br_Label != null) {
				try {
					br_CountData.close();
					br_LoadData.close();
					br_Label.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Suche lokale maxima durch umfeldbetrachtung.
	 *
	 * @param Grenze_negativ_Y the grenze negativ Y
	 * @param Grenze_positiv_Y the grenze positiv Y
	 * @param Grenze_negativ_X the grenze negativ X
	 * @param Grenze_positiv_X the grenze positiv X
	 */
	private void Suche_lokale_Maxima_durch_Umfeldbetrachtung(int Grenze_negativ_Y, int Grenze_positiv_Y,
			int Grenze_negativ_X, int Grenze_positiv_X) {

		for (int X = 0; X < AnzahlSpalten; X++) {
			for (int Y = 0; Y < AnzahlZeilen; Y++) {

				Wert = Werte_Array[X][Y];
				yPOS.add(Y);
				xPOS.add(X);
				Werte.add(Wert);
				remove = false;

				for (int By = Grenze_negativ_Y; By <= Grenze_positiv_Y; By++) {
					for (int Bx = Grenze_negativ_X; Bx <= Grenze_positiv_X; Bx++) {
						if (X + Bx >= 0 && X + Bx < AnzahlSpalten) {
							if (Y + By >= 0 && Y + By < AnzahlZeilen) {

								if (Wert < Werte_Array[X + Bx][Y + By]) {
									remove = true;
								}

							}
						}
					}
				}

				if (remove) {
					yPOS.remove(Zähl_Variable);
					xPOS.remove(Zähl_Variable);
					Werte.remove(Zähl_Variable);
					Zähl_Variable--;
				}

				Zähl_Variable++;
			}
		}
	}



	/**
	 * Überprüfe und lösche doppelte maxima.
	 *
	 * @param Radius the radius
	 */
	private void Überprüfe_Lösche_doppelte_Maxima(int Radius) {

		for (int m = 0; m < Werte.size(); m++) {

			for (int n = 0; n < Werte.size(); n++) {

				if (n != m && (Werte.get(m) - Werte.get(n) == 0)) {

					if (((yPOS.get(m) - yPOS.get(n)) - (xPOS.get(m) - xPOS
							.get(n))) < Radius) {

						Werte.remove(n);
						xPOS.remove(n);
						yPOS.remove(n);

					}

				}
			}
		}
	}

	/**
	 * Finde richtige maxima mit durchschnittshöhe.
	 * Lösche Maxima die zu klein sind
	 *
	 * @param Höhenunterschied_Max_Durchschnitt the höhenunterschied max durchschnitt
	 */
	private void Finde_Richtige_Maxima_mit_Durchschnittshöhe(
			int Höhenunterschied_Max_Durchschnitt) {

		for (int i = 0; i < Werte.size(); i++) {
			tmp_X = xPOS.get(i);
			tmp_Y = yPOS.get(i);
			Wert = Werte.get(i);

			for (int Y = -20; Y <= 20; Y++) {
				for (int X = -20; X <= 20; X++) {
					if (tmp_X + X >= 0 && tmp_X + X < AnzahlSpalten) {
						if (tmp_Y + Y >= 0 && tmp_Y + Y < AnzahlZeilen) {

							Durchschnittswerte += Werte_Array[tmp_X + X][tmp_Y
									+ Y];

							Zähl_Variable++;

						}
					}
				}
			}

			if ((Wert - (Durchschnittswerte / Zähl_Variable)) < Höhenunterschied_Max_Durchschnitt) {
				xPOS.remove(i);
				yPOS.remove(i);
				Werte.remove(i);
			}

			Zähl_Variable = 0;
			Durchschnittswerte = 0;
		}

	}

	/**
	 * Bestimme F score.
	 * Gebe F-Score aus
	 *
	 * @param Akzeptanz_Radius the akzeptanz radius
	 */
	private void Bestimme_FScore(int Akzeptanz_Radius ) {
		int Gefunden = 0;
		for (int m = 0; m < Werte.size(); m++) {

			for (int z = 0; z < xPOSLabel.size(); z++) {
				if (((xPOS.get(m) - xPOSLabel.get(z))
						+ (yPOS.get(m) - yPOSLabel.get(z)) < Akzeptanz_Radius )
						&& (Werte.get(m)- Werte_Array[xPOSLabel.get(z)][yPOSLabel
										.get(z)] == 0)) {
					Gefunden++;
					break;
				}
			}
		}

		System.out.println("Korrekt vom Algoritmus erkannt: " + Gefunden);
		System.out.println("Vorhande Label: " + xPOSLabel.size());
		Double Recall = (double) ((double) Gefunden / (double) xPOSLabel.size());
		System.out.println("Recall: " + Recall);

		System.out.println("Insgesamt gefunde Werte: " + Werte.size());
		Double Precision = (double) Gefunden / (double) Werte.size();
		System.out.println("Prescion: " + Precision);
		Double FSCORE = 2 / ((1 / Recall) + (1 / Precision));
		System.out.println("F-Score: " + FSCORE);

	}

}
