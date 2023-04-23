package com.jahangiri.passwordgenerator.customToast

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.jahangiri.passwordgenerator.R

@SuppressLint("MissingInflatedId")
fun Toast.showCustomToastSuccess(message: String, activity: Activity) {
    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast_success,
        activity.findViewById(R.id.toast_container)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.txt_toast)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.TOP, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

