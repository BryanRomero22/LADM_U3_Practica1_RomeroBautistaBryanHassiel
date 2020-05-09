package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main3.*
import Utils.*
import android.widget.ArrayAdapter
import android.widget.ListView

class Main3Activity : AppCompatActivity() {
    var id= ""
    val SELECT_PHOTO = 2222


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var extra = intent.extras

        editTextDescripcion.setText(extra?.getString("descripcion"))
        editTextCaptura.setText(extra?.getString("fechaCaptura"))
        editTextEntrega.setText(extra?.getString("fechaEntrega"))

        id = extra?.getInt("id").toString()

        btnActualizar.setOnClickListener {
            actualizar()
        }

        btnEliminar.setOnClickListener {
            eliminar()
        }

        btnRegresar.setOnClickListener {
            var intento = Intent(this, MainActivity::class.java)
            startActivityForResult(intento,0)

        }
        btnSeleccionarImg.setOnClickListener {
            cargarImagen()
        }

        btnGuardarImg.setOnClickListener {
            var actividad = Actividad(
                descripcion.text.toString(),
                fechaCaptura.text.toString(),
                fechaEntrega.text.toString()
            )

            actividad.asignarPuntero(this)

            var resultado = actividad.insertar()

            if (resultado == true) {

                dialogo("SE CAPTURO ACTIVIDAD")
                descripcion.setText("")
                fechaCaptura.setText("")
                fechaEntrega.setText("")

            } else {
                when (actividad.error) {
                    1 -> {
                        dialogo("error en tabla NO SE CREO o NO SE CONECTO A LA BASE DE DATOS")
                    }
                    2 -> {
                        dialogo("error NO SE PUDO INSERTAR")
                    }
                }//when
            }//else
        }//btnSave
    }
    fun actualizar(){
        var actividadActualizada = Actividad(editTextDescripcion.text.toString(), editTextCaptura.text.toString(), editTextEntrega.text.toString())
        actividadActualizada.id = id.toInt()
        actividadActualizada.asignarPuntero(this)

        if (actividadActualizada.actualizar()==true){
            dialogo("SE ACTUALIZO")
        }else{
            dialogo("ERROR, NO SE PUDO ACTUALIZAR")
        }

        finish()
    }

    fun  dialogo(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }//Dialogo

    fun eliminar(){
        var actividadEliminada = Actividad("","","")
        actividadEliminada.id=id.toInt()
        actividadEliminada.asignarPuntero(this)

        if (actividadEliminada.eliminar()){
            dialogo("SE ELIMINO")
        }else{
            dialogo("NO SE PUDO ELIMINAR")
        }
        finish()

    }

    fun cargarImagen() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 10)

        /*val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.type = "image/*"
        startActivityForResult(photoPicker, SELECT_PHOTO)*/*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            var path = data?.data
            imageView.setImageURI(path)
        }

        /*super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data !== null) {
            val pickedImage = data.data
            imageView.setImageURI(pickedImage)
        }*/
    }

}