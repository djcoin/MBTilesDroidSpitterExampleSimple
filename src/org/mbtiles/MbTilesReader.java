package org.mbtiles;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MbTilesReader extends Activity {
    private ImageView tileImage;
	private MbTilesSQLiteClient sqlcli;
	private Button loadButton;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File sdcard = Environment.getExternalStorageDirectory();
        String db_name = "map.mbtiles";
        
        tileImage = (ImageView) findViewById(R.id.tileImage);
        loadButton = (Button) findViewById(R.id.loadButton);
        
        loadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 // String x = ((EditText) findViewById(R.id.x))
				String x = ((EditText) findViewById(R.id.x)).getText().toString();
				String y = ((EditText) findViewById(R.id.y)).getText().toString();
				String z = ((EditText) findViewById(R.id.z)).getText().toString();
				
				try {				
					Integer.parseInt(x);
					Integer.parseInt(y);
					Integer.parseInt(z);
				} catch (NumberFormatException e) {
					Toast.makeText(MbTilesReader.this, "NumberFormatException" + e.getMessage(), Toast.LENGTH_SHORT);					
					return;
				}
				// System.out.println(x + " " + y + " " + z);
				Log.d("HERE", x + " " + y + " " + z);
				Bitmap b = sqlcli.getTileAsBitmap(x, y, z);
				tileImage.setImageBitmap(b);
			}
		});
        
        sqlcli = new MbTilesSQLiteClient(this, new File(sdcard, db_name));        
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	sqlcli.open();
    	
    	Bitmap b = sqlcli.getTileAsBitmap(1, 1, 1);
		tileImage.setImageBitmap(b);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	sqlcli.close();
    }
    
}