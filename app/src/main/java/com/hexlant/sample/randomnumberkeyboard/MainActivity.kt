package com.hexlant.sample.randomnumberkeyboard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hexlant.ui.NumberKeyboardListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberKeyboard.setListener(object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                var text = tvTemp.text
                tvTemp.text = "$text$number"
            }

            override fun onLeftAuxButtonClicked() {
                numberKeyboard.resetRandom()
            }

            override fun onRightAuxButtonClicked() {
                var text = tvTemp.text
                if(text.isNotEmpty()){
                    tvTemp.text = text.substring(0, text.length - 1)
                }

            }

        })
    }
}
