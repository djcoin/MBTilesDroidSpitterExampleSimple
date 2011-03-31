
Author: Simon Thépot aka djcoin <simon.thepot@gmail.com> <simon.thepot@makina-corpus.com>

Setting up
==========

Fetch an sqlite "MBTiled" database if you don't have any
--------------------------------------------------------

Get an sqlite database in the mbtiles format (http://mapbox.com/documentation/mbtiles-file-format).
You may find some at http://mapbox.com/tileset/world-light: (wget http://mapbox-tilesets.s3.amazonaws.com/mbtiles/World_Light.mbtiles.zip)


Make your database "Android" compatible
---------------------------------------

Android requires any sqlite database to have an non-empty "android_metadata" table.

Example:
sqlite3 <your_database> "CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT DEFAULT 'en_US'); DELETE FROM android_metadata; INSERT INTO android_metadata VALUES ('en_US');"

Load it to your device and make it fetchable
--------------------------------------------

Load the database on your device:

Example:
adb push path/to/your/sqlite_android_enabled_db /sdcard/map.mbtiles

If you load it in your sdcard base directory and name it "map.mbtiles" it will be loaded automatically.
Otherwise, put it wherever you like and change the path of the sqlite database in the source code

