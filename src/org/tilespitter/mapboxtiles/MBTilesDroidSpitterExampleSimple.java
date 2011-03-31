package org.tilespitter.mapboxtiles;

/** @author Simon Thépot aka djcoin <simon.thepot@gmail.com, simon.thepot@makina-corpus.com> */

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/** A very simple tile loader fetching one tile at a time from a MbTiles sqlite database */
public class MBTilesDroidSpitterExampleSimple extends Activity {
	
	private static final String TAG = MBTilesDroidSpitterExampleSimple.class.getName();
	
	private final File sdcard = Environment.getExternalStorageDirectory();
	private final String db_name = "map.mbtiles";	
	private final File sqlitefile = new File(sdcard, db_name); // sqlite file to load	
	
	private ImageView tileImage;
	private MBTilesDroidSpitter sqlcli;
	private Button loadTileButton, showMetadataButton;
	private EditText coordx;
	private EditText coordy;
	private EditText coordz;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);                
        
        initView();
        
        sqlcli = new MBTilesDroidSpitter(this, sqlitefile);
        
        // Initial data
        coordx.setText("1");
        coordy.setText("1");
        coordz.setText("1");
    }

    private void initView() {
    	tileImage = (ImageView) findViewById(R.id.tileImage);
        loadTileButton = (Button) findViewById(R.id.loadTileButton);
        showMetadataButton = (Button) findViewById(R.id.showMetadataButton);
        
        coordx = (EditText) findViewById(R.id.x);
        coordy = (EditText) findViewById(R.id.y);
        coordz =(EditText) findViewById(R.id.z);
        
        loadTileButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String x = coordx.getText().toString();
				String y = coordy.getText().toString();
				String z = coordz.getText().toString();
				
				try {
					Integer.parseInt(x);
					Integer.parseInt(y);
					Integer.parseInt(z);
				} catch (NumberFormatException e) {
					Toast.makeText(MBTilesDroidSpitterExampleSimple.this, "NumberFormatException" + e.getMessage(), Toast.LENGTH_SHORT).show();					
					return;
				}
				Bitmap b = sqlcli.getTileAsBitmap(x, y, z);
				sqlcli.getTileAsDrawable(x, y, z);
				if (b == null) {
					Log.d(TAG, "No tile found for " + x + "/" + y + "/" + z + ".");
					Toast.makeText(MBTilesDroidSpitterExampleSimple.this, "No tile found for " + x + "/" + y + "/" + z + ".", Toast.LENGTH_SHORT).show();					
				} else {
					tileImage.setImageBitmap(b);
				}
			}
		});
        
        showMetadataButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showMetadata();
			}
		});
	}

	public void showMetadata() {
    	MbTilesMetadata m = sqlcli.getMetadata();
    	String s = (m == null ? "No metadata or no metadata loaded yet" : m.toString()); 
    	Log.i(TAG, s); 
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	boolean fetchMetadata = true;
    	String versionOfMbtileSpec = "1.0";
    	sqlcli.open(fetchMetadata, versionOfMbtileSpec);
    	
    	if (fetchMetadata) {
    		showMetadata(); // Autoshow Metadata
    	}
    }

    @Override
    protected void onStop() {
    	super.onStop();
    	sqlcli.close();
    }

}