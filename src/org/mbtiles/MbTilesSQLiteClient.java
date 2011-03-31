package org.mbtiles;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class MbTilesSQLiteClient {
	private static final int VERSION_BDD = 1;

	private MbTilesSQLite mbtilesdb;

	private SQLiteDatabase db;

	private File dbpath;

	public MbTilesSQLiteClient(Context ctx, File dbpath) {
		mbtilesdb = new MbTilesSQLite(ctx, dbpath.getName(), null, VERSION_BDD);
		this.dbpath = dbpath;
	}

	public void open(){
		 // db = mbtilesdb.getReadableDatabase();
		db = SQLiteDatabase.openDatabase(dbpath.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
	}

	public void close(){
		db.close();
	}
 
	public SQLiteDatabase getBDD(){
		return db;
	}

//	public Drawable getTileAsDrawable(int x, int y, int z){
//		Bitmap b = getTileAsBitmap(x, y, z);
//		//Drawable.
//	}
	
	public Bitmap getTileAsBitmap(int x, int y, int z){
		String sx = Integer.toString(x);
		String sy = Integer.toString(y);
		String sz = Integer.toString(z);
		// new SQLiteCursor(db, driver, MbTilesSQLite.TABLE_TILES_NAME, query);
		return this.getTileAsBitmap(sx, sy, sz);
	}

	public Bitmap getTileAsBitmap(String x, String y, String z) {
		// Cursor c = db.rawQuery("select tile_data from tiles where x=?s and y=?s and z=?s;", new String[]{x, y, z});
		Cursor c = db.rawQuery("select tile_data from tiles where tile_column = '" + x + "' and tile_row = '" + y + "' and zoom_level = '" + z + "'", null);
		c.moveToFirst();
		byte[] bb = c.getBlob(c.getColumnIndex("tile_data"));
		return BitmapFactory.decodeByteArray(bb, 0, bb.length);
	}
}
