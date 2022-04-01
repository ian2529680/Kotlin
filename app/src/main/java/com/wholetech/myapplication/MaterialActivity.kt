package com.wholetech.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.d(TAG, "secret:" + secretNumber.secert)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
           AlertDialog.Builder(this)
               .setTitle(getString(R.string.replay_game))
               .setMessage(getString(R.string.are_you_sure))
               .setPositiveButton(getString(R.string.ok), {dialog, which ->
                   secretNumber.reset()
                   counter.setText(secretNumber.count.toString())
                   number.setText("")
               })
               .setNegativeButton(getString(R.string.cancel), null)
               .show()
        }
        counter.setText(secretNumber.count.toString())
    }

    fun check(view : View) {
        val n1 = number.text.toString()
        if(n1 == "") {
            var error_message = getString(R.string.please_enter_a_number)
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(error_message)
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        } else {
            var n = n1.toInt()
            println("number:$n")
            Log.d(TAG, "number:" + n)
            val diff = secretNumber.validate(n)
            var message = getString(R.string.yes_you_got_it)
            if(diff < 0) {
                message = getString(R.string.bigger)
            } else if(diff > 0) {
                message = getString(R.string.smaller)
            }
            if(secretNumber.count<3 && diff == 0) {
                message = getString(R.string.excellent_the_number_is) + secretNumber.secert
            }
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            counter.setText(secretNumber.count.toString())
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        }
    }
}