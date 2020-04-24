package com.thoughtworks.wechatmoment.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener


class ColorFilterImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle), OnTouchListener {

    private fun init() {
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> setColorFilter(Color.TRANSPARENT)
        }
        return false
    }

    init {
        init()
    }
}
