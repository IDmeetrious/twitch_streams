package com.example.twitchstream.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.twitchstream.R
import kotlinx.coroutines.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


private const val TAG = "ProgressView"

class ProgressView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private var circleX = 0f
    private var circleY = 0f
    private var circleRadius = 100f
    private var outlineColor = Color.CYAN
    private var innerColor = Color.WHITE
    private var textColor = Color.YELLOW
    private var valueText = "0"
    private var mTextSize = 18
    private var textX = 0f
    private var textY = 0f
    private var progressCount = 1
    private var path: Path

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ProgressView, 0, 0
        )
        typeArray.apply {
            try {
                outlineColor = getColor(R.styleable.ProgressView_outlineColor, Color.CYAN)
                innerColor = getColor(R.styleable.ProgressView_innerColor, Color.BLACK)
                textColor = getColor(R.styleable.ProgressView_textColor, Color.YELLOW)
                valueText = getString(R.styleable.ProgressView_valueText) ?: "0"

            } finally {
                recycle()
            }
        }
        path = Path()

    }

    @SuppressLint("Recycle")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mainScope.launch {
            for (i in 1..6) {
                delay(1000)
                valueText = "$i"
                Log.i(TAG, "--> onSizeChanged: $i")
//                progressCount++
                linePath(i)
                Log.i(TAG, "--> onSizeChanged: progress = $i")
                invalidate()
            }
            path.close()
        }
    }

    private val mPath = Path().apply {
        val l = 1.2f
        val a: Double = (270 * Math.PI / 180)
        Log.i(TAG, "--> path: a = $a")
//        moveTo(
//            circleX + cos(a).toFloat() * circleRadius,
//            circleY + sin(a).toFloat() * circleRadius
//        )
//        lineTo(
//            circleX + cos(a + 0.1).toFloat() * circleRadius * l,
//            circleY + sin(a + 0.1).toFloat() * circleRadius * l
//        )
//        lineTo(
//            circleX + cos(a - 0.1).toFloat() * circleRadius * l,
//            circleY + sin(a - 0.1).toFloat() * circleRadius * l
//        )
//        lineTo(
//            circleX + cos(a).toFloat() * circleRadius,
//            circleY + sin(a).toFloat() * circleRadius
//        )
    }

    private fun linePath(i: Int){
        val sides = 6
        val angle = 2.0 * Math.PI / sides
        path.lineTo(
            circleX + (circleRadius * Math.cos(angle * i)).toFloat(),
            circleY + (circleRadius * Math.sin(angle * i)).toFloat())

    }

    private fun createPath(radius: Float, i: Int = 1): Path {
        val sides = 6
        val angle = 2.0 * Math.PI / sides

        path.moveTo(
            circleX + (radius * Math.cos(0.0)).toFloat(),
            circleY + (radius * Math.sin(0.0)).toFloat())

//        path.close()
        return path
    }
//    private fun createPath(sides: Int, radius: Float): Path {
//        val path = Path()
//        val angle = 2.0 * Math.PI / sides
//        path.moveTo(
//            circleX + (radius * Math.cos(0.0)).toFloat(),
//            circleY + (radius * Math.sin(0.0)).toFloat())
//        for (i in 1 .. sides) {
//            path.lineTo(
//                circleX + (radius * Math.cos(angle * i)).toFloat(),
//                circleY + (radius * Math.sin(angle * i)).toFloat())
//        }
//        path.close()
//        return path
//    }

    private val outlinePaint = Paint().apply {
        isAntiAlias = true
        color = outlineColor
    }
    private val innerPaint = Paint().apply {
        isAntiAlias = true
        color = innerColor
    }
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        color = textColor
        textSize = dpToPx(mTextSize).toFloat()
        textAlign = Paint.Align.CENTER
    }

    private val pathPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getCircleCenter(measuredWidth, measuredHeight)
        getTextPosition(measuredWidth, measuredHeight)
        Log.i(
            TAG, "--> onMeasure: width = $measuredWidth" +
                    ", height = $measuredHeight"
        )
        createPath(circleRadius)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "--> onDraw: ")
        canvas?.apply {
//            drawCircle(circleX, circleY, circleRadius, outlinePaint)
//            drawCircle(circleX, circleY, circleRadius - 20f, innerPaint)
//            drawText(valueText, textX, textY, textPaint)
            drawPath(path, pathPaint)
        }

    }

    private fun dpToPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp.toFloat() * metrics.density
        return px.toInt()
    }

    private fun getCircleCenter(w: Int, h: Int) {
        circleX = (w.toFloat() / 2)
        circleY = (h.toFloat() / 2)
        Log.i(
            TAG, "--> getCircleCenter: circleX = $circleX" +
                    ", circleY = $circleY"
        )
    }

    private fun getTextPosition(w: Int, h: Int) {
        val width = w.toFloat()
        val textWidth = textPaint.measureText(valueText)
        val metrics = textPaint.fontMetrics
        val floatH = abs(metrics.top - metrics.bottom)
        Log.i(TAG, "--> getTextPosition: textWidth = $textWidth")
        textX = (width / 2)
        Log.i(TAG, "--> getTextPosition: textX = $textX")
//        textY = (h.toFloat() / 2) + (mTextSize / 2)
        textY = (h.toFloat() / 2) + (floatH / 2 - mTextSize)
    }

    override fun onDetachedFromWindow() {
        mainScope.cancel(null)
        Log.i(TAG, "--> onDetachedFromWindow: ")
        super.onDetachedFromWindow()
    }
}