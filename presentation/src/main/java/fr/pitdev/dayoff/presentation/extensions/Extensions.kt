package fr.pitdev.dayoff.presentation.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import fr.pitdev.dayoff.presentation.R

fun Activity.hideKeyboard() {
    val view: View? = this.currentFocus
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Activity.showKeyboard() {
    val view: View? = this.currentFocus
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.showSnackBar(message: String) {
    Snackbar.make(this.findViewById(R.id.content), message, Snackbar.LENGTH_SHORT).show()
}