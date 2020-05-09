package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Cambio de activity
        btnCapturar.setOnClickListener {
            var actividad = Actividad(
                descripcion.text.toString(),
                fechaCaptura.text.toString(),
                fechaEntrega.text.toString()
            )

            actividad.asignarPuntero(this)

            var resultado = actividad.insertar()

            if (resultado == true) {

                mensaje("SE CAPTURO ACTIVIDAD")
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

        }//btnInsertar



        btnBuscar.setOnClickListener {
            val  intent: Intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
        }

    }
    fun mensaje(s:String){
        Toast.makeText(this, s, Toast.LENGTH_LONG)
            .show()
    }//Mensaje
    fun  dialogo(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }//Dialogo


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}