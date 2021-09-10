package me.erfandp.buttondp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity: AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val button = findViewById<CustomizableGenericButton>(R.id.customizableGenericButton)
		button.onClickListener = {
			Toast.makeText(applicationContext,"hallo",Toast.LENGTH_SHORT).show()
		}
	}
}