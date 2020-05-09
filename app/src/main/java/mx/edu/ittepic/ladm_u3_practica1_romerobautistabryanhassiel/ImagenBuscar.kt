package mx.edu.ittepic.ladm_u3_practica1_romerobautistabryanhassiel

import Utils.Utils
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.imagenbuscar.*

class ImagenBuscar : AppCompatActivity() {
    val SELECT_PHOTO= 2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imagenbuscar)
        btnSelect.setOnClickListener {
            val photoPicker= Intent(Intent.ACTION_PICK)
            photoPicker.type="image/*"

            startActivityForResult(photoPicker,SELECT_PHOTO)
        }//btnSelect

        btnSave.setOnClickListener {
            val bitmap=(img_select.drawable as BitmapDrawable).bitmap
            val alertDialog= AlertDialog.Builder(this)
            alertDialog.setTitle("Ingrese ID de actividad")
            val editText= EditText(this)
            alertDialog.setView((editText))
            alertDialog.setPositiveButton("Ok"){dialog, which ->
                if(!TextUtils.isEmpty(editText.text.toString())){
                    BaseDatos(applicationContext,"Escuela",null,1)
                        .addBitmap(editText.text.toString().toInt(),Utils.getBytes(bitmap))
                    Toast.makeText(this,"Guardado correctamente", Toast.LENGTH_LONG).show()
                }//if
                else{
                    Toast.makeText(this,"No se pudo guardar, por favor ingresa el ID", Toast.LENGTH_LONG).show()
                }//else
            }//setPositive
            alertDialog.show()
        }//btnSave
    }//onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==SELECT_PHOTO && resultCode== Activity.RESULT_OK && data!==null){
            val pickedImage=data.data
            img_select.setImageURI(pickedImage)
        }
    }
}
