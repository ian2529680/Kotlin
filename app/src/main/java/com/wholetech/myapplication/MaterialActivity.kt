package com.wholetech.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
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
    private val REQUEST_RECORD = 100
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.d(TAG, "secret:" + secretNumber.secert)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            replay()
        }
        counter.setText(secretNumber.count.toString())
        Log.d(TAG, "onCreate: " + secretNumber.secert)
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
        .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: " + count + "/" + nick)
    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.replay_game))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                number.setText("")
            })
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
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
                .setPositiveButton(getString(R.string.ok), {dialog, which->
                    if(diff==0) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", secretNumber.count)
//                        startActivity(intent)
                        startActivityForResult(intent, REQUEST_RECORD)
                    }
                })
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_RECORD) {
            if(resultCode == Activity.RESULT_OK) {
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: " + nickname)
                replay()
            }
        }
    }
}