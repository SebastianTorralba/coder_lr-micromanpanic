package ar.com.twoboot.microman.dominio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;
import ar.com.twoboot.microman.util.Util;

public class Transaccion {
	protected String Nombretabla;
	public static SQLiteDatabase baseDatos;
	private String nombreDB = "db_panicalarm.db";
	StringBuilder DBPath = new StringBuilder();
	private Context contexto = null;

	public Transaccion(Context contexto) {
		this.contexto = contexto;
		DBPath = DBPath.append(Environment.getExternalStorageDirectory())
				.append("/").append(contexto.getPackageName())
				.append("/databases/");
		if (this.baseDatos == null) {
			boolean existe = checkDB();
			
		
			if (existe) {
				conectarDB();
			} else {

				// By calling this method and empty database will be created
				// into the default system path
				// of your application so we are gonna be able to overwrite that
				// database with our database.
				// this.getReadableDatabase();

				try {
					copiarDB();
					conectarDB();
				} catch (IOException e) {

					throw new Error("Error copying database");

				}
			}
		}
		else
		{conectarDB();}
	}

	private void readObject(ObjectInputStream paramObjectInputStream)
			throws IOException, ClassNotFoundException {
		paramObjectInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream paramObjectOutputStream)
			throws IOException {
		paramObjectOutputStream.defaultWriteObject();
	}

	public final int conectarDB() {
		// TODO: implement
		if (baseDatos == null) {
			baseDatos = SQLiteDatabase.openDatabase(DBPath+Util.VERSION_MICROMAN+nombreDB, null,
					SQLiteDatabase.OPEN_READWRITE);
			if (baseDatos.isOpen()) {
				return 0;
			} else {
				return -1;
			}
		} else {

			return -2;
		}
	}

	public final int delete(String paramString) {
		this.baseDatos.beginTransaction();
		this.baseDatos.rawQuery("delete from " + paramString, null);
		this.baseDatos.setTransactionSuccessful();
		Log.i("SQLSERVER", "BORRAR");
		return 0;
	}

	protected void finalize() {
	}

	public final SQLiteDatabase getBaseDatos() {
		return null;
	}

	public final long getid(String paramString) {
		long l = 0L;
		Cursor localCursor = this.baseDatos.rawQuery(
				"select max(id_formulario) from formulario ", null);
		if (localCursor.moveToFirst())
			do
				l = 1L + localCursor.getInt(0);
			while (localCursor.moveToNext());
		localCursor.close();
		return l;
	}

	public final long insert(String paramString,
			ContentValues paramContentValues) {
		long l = 0L;
		this.baseDatos.isOpen();
		try {
			l = this.baseDatos.insertOrThrow(paramString, null,
					paramContentValues);
			Log.i("SQLSERVER", "INSERT SQLite");
			return l;
		} catch (SQLException localSQLException) {
			Log.e("dalus", localSQLException.getMessage());
		}
		return l;
	}

	private void copiarDB() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = contexto.getAssets().open(nombreDB);

		// Path to the just created empty db
		String outFileName = DBPath +Util.VERSION_MICROMAN+ nombreDB;
		File myFile = new File(DBPath.toString());
		if (!myFile.exists()) {
			Log.i("Microman", "El Archivo No Existe");
			myFile.mkdirs();
		}

		//
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	private boolean checkDB() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DBPath +Util.VERSION_MICROMAN+ nombreDB;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}
		return checkDB != null ? true : false;
	}

	public final void setBaseDatos(SQLiteDatabase paramSQLiteDatabase) {
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.Transaccion JD-Core Version: 0.6.0
 */