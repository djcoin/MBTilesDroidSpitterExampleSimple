Overview
========

Simple example of use of the `MBTilesDroidSpitter <https://github.com/djcoin/MBTilesDroidSpitter>`_ library reading the 
`MBTiles format <http://mapbox.com/documentation/mbtiles-file-format>`_ from Android.

**Screenshots at:** https://github.com/djcoin/MBTilesDroidSpitterExampleSimple/tree/master/assets/screenshots

Features
--------

- Load a database taken from /sdcard/map.mbtiles (change the path in source code to change the database to load, will be fixed soon)
- Load one tile at a time given its x/y/z taken from user input and display the matching tile or error message if none
- Display the metadata information of the table

Setting up
==========

Fetch an sqlite "MBTiled" database if you don't have any
--------------------------------------------------------

Get an `SQLite database <http://www.sqlite.org/>`_ in the `MBTiles format <http://mapbox.com/documentation/mbtiles-file-format>`_.

You may find some at http://mapbox.com/tileset/world-light.
    E.g.: ``wget http://mapbox-tilesets.s3.amazonaws.com/mbtiles/World_Light.mbtiles.zip)``


Make your database "Android" compatible
---------------------------------------

Android requires any sqlite database to have an non-empty "android_metadata" table.
    E.g.: ``sqlite3 <your_database> "CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT DEFAULT 'en_US'); DELETE FROM android_metadata; INSERT INTO android_metadata VALUES ('en_US');"``

Load it to your device and make it fetchable
--------------------------------------------

Load the database on your device.
    E.g.: ``adb push path/to/your/sqlite_android_enabled_db /sdcard/map.mbtiles``

If you load it in your sdcard base directory and name it "map.mbtiles" it will be loaded automatically.
Otherwise, put it wherever you like and change the path of the sqlite database in the source code


Contributors
============

- Simon Thépot aka djcoin <simon.thepot@gmail.com> <simon.thepot@makina-corpus.com>. Work done while being at `Makina Corpus <http://www.makina-corpus.com>`_
