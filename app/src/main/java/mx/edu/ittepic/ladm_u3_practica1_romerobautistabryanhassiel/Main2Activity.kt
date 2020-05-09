package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception

class Main2Activity : AppCompatActivity() {
    val nombreBaseDatos = "practica1"
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnBuscarID.setOnClickListener {
            consultaID()
        }
        cargarLista()
    }

    fun cargarLista(){
        var lista = findViewById<ListView>(R.id.lista)
        try {
            var conexion = Actividad("","","")//recuperar data
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodos()//Se recupera mostrarTodos

            if (data.size == 0) {
                if (conexion.error == 3) {
                    var vector = Array<String>(data.size, { "" })
                    dialogo("No se encontraron resultados")
                    lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)
                }//if
                return
            }//if

            var total = data.size-1
            var  vector=Array<String>(data.size, {""}) //Vector estatico igual que array list (tamaño)
            listaID = ArrayList<String>()
            (0..total).forEach{
                var actividad = data[it] //Desde el 0 hasta el -1
                var item = "Id: " + actividad.id+ "\nDescripcion: "+ actividad.descripcion+"\nFecha Captura: "+actividad.fechaCaptura+"\nFecha Entrega: "+actividad.fechaEntrega
                vector[it] = item
                listaID.add((actividad.id.toString()))
            }

            lista.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,vector)
            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var  actEncontrada = con.buscar(listaID[position])

                if (con.error == 4){
                    dialogo("ERROR NO SE ENCONTRO ID")
                    return@setOnItemClickListener
                }
                AlertDialog.Builder(this)
                    .setTitle("¿QUE DESEAS HACER?")
                    .setMessage("Id: ${actEncontrada.id}\nDescripcion: ${actEncontrada.descripcion}\nFecha Captura: ${actEncontrada.fechaCaptura}\nFecha Encontrada: ${actEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver a detalle"){d,i ->cargarEnOtroActivity(actEncontrada)}
                    .setNeutralButton("Cancelar"){d,i->}
                    .show()
            }
        }catch (error: SQLiteException){
            mensaje(error.message.toString())
        }

    }//CargarLista

    fun consultaID(){
        var lista = findViewById<ListView>(R.id.lista)
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarID(editTextID.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("No se encontraron resultados")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = java.util.ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id + "\nDescripción: " + actividades.descripcion + "\nFecha Captura: " + actividades.fechaCaptura + "\nFecha entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actEncontrada.id}\nDescripción: ${actEncontrada.descripcion}\nFecha de captura: ${actEncontrada.fechaCaptura}\nFecha de entrega: ${actEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver a detalle") { d, i ->
                        cargarEnOtroActivity(actEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }




    fun mensaje(mensaje:String){
        AlertDialog.Builder(this)
            .setMessage(mensaje)
            .show()
    }
    fun  dialogo(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }

    fun  cargarEnOtroActivity(act: Actividad, img: Imagenes){

        var intento = Intent(this, Main3Activity::class.java)

        intento.putExtra("id",act.id)
        intento.putExtra("descripcion",act.descripcion)
        intento.putExtra("fechaCaptura",act.fechaCaptura)
        intento.putExtra("fechaEntrega",act.fechaEntrega)
        intento.putExtra("foto",img.foto)

        startActivityForResult(intento,0)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cargarLista()
    }

}
