package com.example.monthly.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView(internal var context: Context, attrs : AttributeSet?) : View(context, attrs), java.io.Serializable{

    private var mbitmap : Bitmap? = null
    private var mCanvas : Canvas? = null
    private var mPath : Path = Path()
    private var mPaint : Paint = Paint()
    private var mX : Float = 0.toFloat()
    private var mY : Float = 0.toFloat()

    private var touchAvailabe = true

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(mPath, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas()
    }

    private fun onStartTouchEvent(x: Float, y: Float){
        mPath.moveTo(x,y)
        mX = x
        mY = y
    }

    private fun onMoveTouchEvent(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if(dx >= 5f || dy >= 5f) {
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2)
            mX = x
            mY = y
        }
    }

    private fun upTouchEvent() {
        mPath.lineTo(mX, mY)
    }

    fun ClearCanvas() {
        mPath.reset()
        invalidate()
    }

//    fun getCanvasPath(): Path{
//        return mPath
//    }
//
//    fun setCanvasPath(mPath: Path){
//        this.mPath = mPath
//    }

//    fun drawSign(){
//        mCanvas?.drawPath(mPath, mPaint)
//    }
    // 화면에 터치가 감지 됐을 때
    override fun onTouchEvent(event: MotionEvent): Boolean {

        // 터치 불가능 일 경우(initFinalActivity에서 사용될 때) 리턴
        if (!touchAvailabe) return true

        val x = event.x
        val y = event.y

        when(event.action) {
            // 터치 누름 감지
            MotionEvent.ACTION_DOWN -> {
                onStartTouchEvent(x, y)
                invalidate()
            }

            // 터치 이동 감지
            MotionEvent.ACTION_MOVE -> {
                onMoveTouchEvent(x, y)
                invalidate()
            }

            // 터치 뗌 감지
            MotionEvent.ACTION_UP -> {
                upTouchEvent()
                invalidate()
            }
        }
        return true
    }

    fun setTouchDisable() {
        touchAvailabe = false
    }
}