package ar.com.twoboot.microman.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import android.util.Log;
import ar.com.twoboot.panico.objetos.Cuestionario;

public class CoderLog {
	private static File logFile;
	private static FileWriter w;
	private static BufferedWriter bw;
	private static PrintWriter wr ;

	public CoderLog() {
		// TODO Auto-generated constructor stub
	}
	public  static void log(Cuestionario cuestionario,String error){
		try {
			File myFile = new File("/sdcard/errores.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append((new Date()).toString());
//			myOutWriter.append(cuestionario.getOrden().toString());
			myOutWriter.append(error);
			Log.i("MicroMan","Escribiendo");
			myOutWriter.close();
			fOut.close();
		} catch (Exception e) {
			Log.e("MicroLog", e.getMessage());
		}
	}
	public static void log(Cuestionario cuestionario,String accion,String valor){
		try {
			if(logFile==null){
			logFile = new File("/sdcard/trlg");
			
			if(!logFile.exists()){
				Log.i("Microman","El Archivo No Existe");
				logFile.createNewFile();
				}
		
			}
//			FileOutputStream fOut = new FileOutputStream(myFile);
//			OutputStreamWriter myOutWriter = 
//									new OutputStreamWriter(fOut);
			w = new FileWriter(logFile,true);
			bw = new BufferedWriter(w);
			bw.newLine();
			wr =new PrintWriter(bw);  
			wr.append("FECHA:"+new Date());
			//			wr.append(cuestionario.getRuta().toString());
			//wr.append(cuestionario.getOrden().toString());
			wr.append("ACCION:"+accion);
			wr.append("VALOR:"+valor);
			wr.append('\n');
			Log.i("MicroMan","Escribiendo "+accion);
			wr.close();
			bw.close();
			
		} catch (Exception e) {
			Log.e("MicroLog", e.getMessage());
		}
	}
	
	public static String leerLog(){
		String errores;
		try {
			File myFile = new File("/sdcard/errores.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
			errores=aBuffer;
		} catch (Exception e) {
			errores=e.getMessage();
		}
		return errores;
	}

}
