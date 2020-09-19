package com.hatenablog.zyxwv.android_as_reader

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hatenablog.zyxwv.androidas.IASMessageService

class MainActivity : AppCompatActivity() {

    var mService : IASMessageService? = null
    // bind するときの使う無名クラス
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("READER", "DISCONNECT")
            mService = null
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d("READER", "CONNECT")
            mService = IASMessageService.Stub.asInterface(p1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val uri = Uri.parse("content://com.hatenablog.zyxwv.androidas.message_provider")
            Log.d("HOGEHOGE", uri.toString())
            val projection = arrayOf("name", "body")
            val selection = null
            val selectionArgs = null
            val sortOrder = ""
            val client = contentResolver.acquireContentProviderClient(uri)
            Log.d("AAA", client.toString())
            val cursor = client?.query(uri, projection, selection, selectionArgs, sortOrder)
//            val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
            Log.d("FUGA", cursor.toString())

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(0)
                    val body = cursor.getString(1)
                    Log.d("AndroidAS", name)
                    Log.d("AndroidAS", body)
                }
            }
        }

//        val uri = Uri.parse("content://com.hatenablog.zyxwv.androidas.message_provider")
//        val client = contentResolver.acquireContentProviderClient(uri)
//        Log.d("AAA", client.toString())

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val messages = mService?.latestMessages
            for(message in messages!!) {
                Log.d("MESSAGE", message.toString())
            }
        }

        Log.d("PERMISSION", ContextCompat.checkSelfPermission(this, "com.hatenablog.zyxwv.androidas.permission.READ_MESSAGE").toString())
        Log.d("PERMISSION", PackageManager.PERMISSION_GRANTED.toString())

        val intent = Intent("com.hatenablog.zyxwv.androidas.ACTION_BIND")
        intent.setPackage("com.hatenablog.zyxwv.androidas")
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}