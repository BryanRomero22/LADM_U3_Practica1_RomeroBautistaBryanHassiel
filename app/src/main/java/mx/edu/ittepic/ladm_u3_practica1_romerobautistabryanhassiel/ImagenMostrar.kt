package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import Utils.Utils
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.imagenmostrar.*

class ImagenMostrar : AppCompatActivity(){internal lateinit var dbHelper:BaseDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imagenmostrar)
        dbHelper= BaseDatos(this,"Escuela",null,1)
        btnMostrar.setOnClickListener {
            if(dbHelper.getBitmapByIDAct(editIdAct.text.toString().toInt())!==null){
                val bitmap= Utils.getImage(dbHelper.getBitmapByIDAct(editIdAct.text.toString().toInt())!!)
                img_show.setImageBitmap(bitmap)
            }//if
            else{
                Toast.makeText(this,"No se encontró el bitmap", Toast.LENGTH_LONG).show()
            }
        }//btnMostrar
    }//onCreate
}//class