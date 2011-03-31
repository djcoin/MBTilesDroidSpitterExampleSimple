package org.mbtiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

// sqlite3 <your_sqlite_db>
// private final static String ANDROID_REQUIREMENT_TABLE = "CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT DEFAULT 'en_US');";
// private final static String ANDROID_REQUIREMENT_INSERT = "INSERT INTO android_metadata VALUES ('en_US');";

public class MbTilesSQLite extends SQLiteOpenHelper {

	public final static String TABLE_TILES_NAME = "tiles";
	private final static String CREATE_TILES = "CREATE TABLE "  + TABLE_TILES_NAME + " (zoom_level INTEGER, tile_column INTEGER, tile_row INTEGER, tile_data BLOB);";	
	private final static String CREATE_METADATA = "CREATE TABLE metadata (name TEXT, value TEXT)";
	private final static String INDEX_TILES = "CREATE UNIQUE INDEX tile_index ON "  + TABLE_TILES_NAME + " (zoom_level, tile_column, tile_row);";
	private final static String INDEX_METADATA = "CREATE UNIQUE INDEX name ON metadata (name);";	
			
//	CREATE TABLE images (
//	        tile_data blob,
//	        tile_id VARCHAR(256));
//	CREATE TABLE map (
//	        zoom_level integer, 
//	        tile_column integer, 
//	        tile_row integer, 
//	        tile_id VARCHAR(256));
	
	
	public MbTilesSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TILES);
		db.execSQL(CREATE_METADATA);
		db.execSQL(INDEX_TILES);
		db.execSQL(INDEX_METADATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
		db.execSQL("DROP TABLE IF EXISTS tiles");
		db.execSQL("DROP TABLE IF EXISTS metadata");
		onCreate(db); 
	}

}
