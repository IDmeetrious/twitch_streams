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
    private var textColor = Color.BLUE
    private var valueText = "0"
    private var mTextSize = 18
    private var textX = 0f
    private var textY = 0f
    private var progressCount = 1
    private var progressSides = 6
    private var path: Path

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ProgressView, 0, 0
        )
        typeArray.apply {
            try {
                outlineColor = getColor(R.styleable.ProgressView_outlineColor, Color.CYAN)
                innerColor = getColor(R.styleable.ProgressView_innerColor, Color.BLACK)
                textColor = getColor(R.styleable.ProgressView_textColor, Color.BLUE)
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
            var progress = 0

            for (i in 1..progressSides) {
                delay(1000)
                progress = (100 / progressSides) * i
                if (i == progressSides) progress = 100
                valueText = "${progress}%"
                Log.i(TAG, "--> onSizeChanged: $valueText")
                progressCount = i
                updatePath(progressCount)
                Log.i(TAG, "--> onSizeChanged: progress = $progressCount")
                invalidate()
            }
            path.close()
        }
    }


    private fun updatePath(i: Int) {
        val angle = 2.0 * Math.PI / progressSides
        path.lineTo(
            circleX + (circleRadius * cos(angle * i)).toFloat(),
            circleY + (circleRadius * sin(angle * i)).toFloat()
        )

    }

    private fun createPath(): Path {
        path.moveTo(
            circleX + (circleRadius * cos(0.0)).toFloat(),
            circleY + (circleRadius * sin(0.0)).toFloat()
        )
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
        createPath()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "--> onDraw: ")
        canvas?.apply {
//            drawCircle(circleX, circleY, circleRadius, outlinePaint)
//            drawCircle(circleX, circleY, circleRadius - 20f, innerPaint)
            drawText(valueText, textX, textY, textPaint)
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