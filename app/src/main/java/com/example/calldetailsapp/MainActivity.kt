package com.example.calldetailsapp

import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Long
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast

import android.content.ContentResolver

class MainActivity : AppCompatActivity() {
    lateinit var callLogsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callLogsTextView = findViewById(R.id.call_logs_text_view)
        getCallLogs()
    }

     open fun getCallLogs() {
        var cr: ContentResolver? = getBaseContext().getContentResolver()
        var c: android.database.Cursor? =
            cr?.query(CallLog.Calls.CONTENT_URI, null, null, null, null)
        var totalCall: kotlin.Int = 1
        if (c != null) {
            totalCall = 5 // intenger call log limit
            if (c.moveToLast()) { //starts pulling logs from last - you can use moveToFirst() for first logs
                for (j in 0 until totalCall) {
                    var phNumber: kotlin.String? =
                        c.getString(c.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                    var callDate: kotlin.String? =
                        c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DATE))
                    var callDuration: kotlin.String? =
                        c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    var dateFormat: Date? = Date(java.lang.Long.valueOf(callDate))
                    var callDayTimes: kotlin.String? = java.lang.String.valueOf(dateFormat)
                    var direction: kotlin.String? = null
                    when (c.getString(c.getColumnIndexOrThrow(CallLog.Calls.TYPE)).toInt()) {
                        CallLog.Calls.OUTGOING_TYPE -> direction = "OUTGOING"
                        CallLog.Calls.INCOMING_TYPE -> direction = "INCOMING"
                        CallLog.Calls.MISSED_TYPE -> direction = "MISSED"
                        else -> {
                        }
                    }
                    c.moveToPrevious() // if you used moveToFirst() for first logs, you should this line to moveToNext
                    callLogsTextView.append(phNumber + "\n" + callDuration + "\n" + callDayTimes + "\n" + direction + "\n\n\n")
//                    Toast.makeText(
//                        getBaseContext(),
//                        phNumber + callDuration + callDayTimes + direction,
//                        Toast.LENGTH_SHORT
//                    ).show() // you can use strings in this line
                }
            }
            c.close()
        }
    }

}