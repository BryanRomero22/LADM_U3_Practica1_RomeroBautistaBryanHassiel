package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.sql.SQLException

class Imagenes(img:ByteArray?) {
    var foto = img
    var id = 0
    var error = -1

    val nombreBaseDatos = "actividades"

    var puntero: Context?=null

    fun  asignarPuntero(p:Context){
        puntero=p
    }//AsignarPuntero

    fun insertar():Boolean{
        error=-1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var insertar = base.writableDatabase
            var datos= ContentValues()

            datos.put("FOTO", foto)

            var respuesta = insertar.insert("EVIDENCIAS", "IDACTIVIDAD", datos)
            if (respuesta.toInt() == -1){
                error = 2
                return false
            }

        }catch (e:SQLException){
            error = 1
            return false
        }
        return true
    }//FinInsertar


    fun mostrarTodos(): ArrayList<Imagenes> {
        var data = ArrayList<Imagenes>()

        error = -1

        try {
            //Conexion con la base de datos var base
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase

            var columnas = arrayOf("*")//Para mostrar todos los campos

            var cursor = select.query("ACTIVIDAD", columnas, null, null, null, null, null)

            if (cursor.moveToFirst()) {
                do {
                    var actTemporal = Imagenes(cursor.getBlob(2))
                    actTemporal.id = cursor.getInt(0)//Agregar ID
                    data.add(actTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }
        } catch (e: SQLException) {
            error = 1
        }
        return data
        error = 3
    }//FunMostrarTodos


    fun actualizar():Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var actualizar = base.writableDatabase
            var datos=ContentValues()

            var idActualizar = arrayOf(id.toString())

            datos.put("FOTO", foto)

            var respuesta=  actualizar.update("EVIDENCIAS", datos,"IDACTIVIDAD = ?", idActualizar)

            if (respuesta.toInt() == 0){
                error = 5
                return false
            }
        }catch (e:SQLException){
            error=1
            return false
        }
        return  true

    }//Actualizar

    fun buscar(id:String) : Imagenes{
        var actividadEncontrada = Actividad("-1", "-1", "-1")

        error=-1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var  idBuscar = arrayOf(id)

            var cursor = select.query("ACTIVIDAD", columnas, "IDACTIVIDAD = ?", idBuscar, null, null, null)

            if (cursor.moveToFirst()){
                actividadEncontrada.id= id.toInt()
                actividadEncontrada.descripcion = cursor.getString(1)
                actividadEncontrada.fechaCaptura = cursor.getString(2)
                actividadEncontrada.fechaEntrega = cursor.getString(3)

            }else{
                error = 4
            }

        }catch (e:SQLException){
            error = 1 //Error en tabbla o conexion
        }

        return actividadEncontrada
    }//FunBuscar
}