package com.example.sqlite01

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)

        val btnGuardarPersonaje = findViewById<Button>(R.id.botonGuardarPersonaje)
        val btnRecuperarPersonaje = findViewById<Button>(R.id.botonRecuperarPersonaje)


        btnGuardarPersonaje.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.editTextNombre).toString()
            val pesoMochila = findViewById<EditText>(R.id.editTextPesoMochila).toString().toInt()
            val estadoVital = findViewById<EditText>(R.id.editTextEstadoVital).toString()
            val clase = findViewById<EditText>(R.id.editTextClase).toString()
            val raza = findViewById<EditText>(R.id.editTextRaza).toString()
            val personaje = Personaje(nombre, pesoMochila, estadoVital, raza, clase)
            dbHelper.insertarPersonaje(personaje)
            Toast.makeText(this, "Personaje guardado en la bbdd SQLite", Toast.LENGTH_SHORT).show()
        }

        btnRecuperarPersonaje.setOnClickListener {
            val selectorPersonaje = findViewById<EditText>(R.id.editTextSelectorPersonaje).text.toString().toInt()
            val nombreRec = findViewById<TextView>(R.id.textViewNombre)
            val pesoMochilaRec = findViewById<TextView>(R.id.textViewPesoMochila)
            val estadoVitalRec = findViewById<TextView>(R.id.textViewEstadoVital)
            val claseRec = findViewById<TextView>(R.id.textViewClase)
            val razaRec = findViewById<TextView>(R.id.textViewRaza)
            val personajes = dbHelper.getPersonas()

            nombreRec.text = personajes[selectorPersonaje].getNombre()
            pesoMochilaRec.text = personajes[selectorPersonaje].getPesoMochila().toString()
            

        }

    }
}

class DatabaseHelper(context: Context) :SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Personajes.db"
        private const val TABLA_PERSONAJES = "personajes"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PESOMOCHILA = "pesoMochila"
        private const val COLUMN_ESTADOVITAL = "estadoVital"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_CLASE = "clase"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_PERSONAJES ($KEY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT, $COLUMN_PESOMOCHILA INTEGER, $COLUMN_ESTADOVITAL TEXT," +
                "$COLUMN_RAZA TEXT, $COLUMN_CLASE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJES")
        onCreate(db)
    }

    fun insertarPersonaje(personaje :Personaje){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, personaje.getNombre())
            put(COLUMN_PESOMOCHILA, personaje.getPesoMochila())
            put(COLUMN_ESTADOVITAL, personaje.getEstadoVital())
            put(COLUMN_RAZA, personaje.getRaza())
            put(COLUMN_CLASE, personaje.getClase())
        }
        db.insert(TABLA_PERSONAJES, null, values)
        db.close()
    }
    @SuppressLint("Range")
    fun getPersonas() :ArrayList<Personaje>{
        val personajes = ArrayList<Personaje>()
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val pesoMochila = cursor.getInt(cursor.getColumnIndex(COLUMN_PESOMOCHILA))
                val estadoVital = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOVITAL))
                val clase = cursor.getString(cursor.getColumnIndex(COLUMN_CLASE))
                val raza = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
                personajes.add(Personaje(nombre,pesoMochila,estadoVital,clase,raza))
            }while(cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return personajes
    }

}