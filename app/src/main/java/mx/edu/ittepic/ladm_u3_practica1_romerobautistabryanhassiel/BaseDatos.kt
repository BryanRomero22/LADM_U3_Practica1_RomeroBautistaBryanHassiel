package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class BaseDatos(context: Context?, nombreBD: String?, cursor: SQLiteDatabase.CursorFactory?, versionBD: Int): SQLiteOpenHelper(context, nombreBD, cursor, versionBD){
    companion object{
        val DB_NAME="actividades"
        val DB_VER=1
        val TBL_NAME="EVIDENCIAS"
        val COL_NAME="ID_ACTIVIDAD"
        val COL_FOTO="FOTO"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL("CREATE TABLE ACTIVIDAD(IDACTIVIDAD INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPCION VARCHAR(2000), FECHACAPTURA VARCHAR(200), FECHAENTREGA VARCHAR(200))")
            db?.execSQL("CREATE TABLE EVIDENCIAS(IDEVIDENCIA INTEGER PRIMARY KEY AUTOINCREMENT, IDACTIVIDAD INTEGER, FOTO BLOB)"
            )
        }catch (err: SQLiteException){

        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addBitmap(idAct:Int,image:ByteArray){
        val database=this.writableDatabase
        val cv= ContentValues()
        cv.put(COL_NAME,idAct)
        cv.put(COL_FOTO,image)
        database.insert(TBL_NAME,null,cv)
    }//addBitmap

    fun getBitmapByIDAct(idAct:Int):ByteArray?{
        val db=this.writableDatabase
        val qb= SQLiteQueryBuilder()

        val sqlSelect=arrayOf(COL_FOTO)
        qb.tables= TBL_NAME
        val c= qb.query(db,sqlSelect,"ID_ACTIVIDAD = ?", arrayOf(idAct.toString()),null,null,null)
        var result:ByteArray?=null
        if (c.moveToFirst()){
            do{
                result=c.getBlob(c.getColumnIndex(COL_FOTO))
            }while (c.moveToNext())
        }//if
        return result
    }//getBitmap
}