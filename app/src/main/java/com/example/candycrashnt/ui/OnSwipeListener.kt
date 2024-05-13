package com.example.candycrashnt.ui

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeListener(context: Context?) : View.OnTouchListener{

    var gestureDelector: GestureDetector

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDelector.onTouchEvent(event!!)
    }
    inner class GestureListenner :GestureDetector.SimpleOnGestureListener(){
        val SWIPE_THRESOLD = 100
        val SWIPE_VELOCITY_THRESOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false

            val yDift = e2.y - e1!!.y
            val xDift = e2.x - e1!!.x

            if (Math.abs(xDift) > Math.abs(yDift)){

                if(Math.abs(xDift)>SWIPE_THRESOLD &&
                    Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD) {
                    if (xDift > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLift()
                    }
                    result = true

                }
                else if(Math.abs(yDift) > SWIPE_THRESOLD &&
                    Math.abs(velocityY) > SWIPE_VELOCITY_THRESOLD) {
                    if (yDift > 0) {
                        onSwipeTop()
                    } else {
                        onSwipeBottom()
                    }
                    result = true
                }
            }

            if (Math.abs(xDift) > Math.abs(yDift)){

                if(Math.abs(xDift)>SWIPE_THRESOLD &&
                    Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD) {
                    if (xDift > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLift()
                    }
                    result = true

                }
                else if(Math.abs(yDift) > SWIPE_THRESOLD &&
                    Math.abs(velocityY) > SWIPE_VELOCITY_THRESOLD) {
                    if (yDift > 0) {
                        onSwipeTop()
                    } else {
                        onSwipeBottom()
                    }
                    result = true
                }
            }
            if (Math.abs(xDift) > Math.abs(yDift)){

                if(Math.abs(xDift)>SWIPE_THRESOLD &&
                    Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD) {
                    if (xDift > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLift()
                    }
                    result = true

                }
            }
            else if(Math.abs(yDift) > SWIPE_THRESOLD &&
                Math.abs(velocityY) > SWIPE_VELOCITY_THRESOLD) {
                if (yDift > 0) {
                    onSwipeTop()
                } else {
                    onSwipeBottom()
                }
                result = true
            }
            return result
        }
    }

    open fun onSwipeBottom() {}
    open fun onSwipeTop() {}
    open fun onSwipeLift() {}
    open fun onSwipeRight() {}

    init {
        gestureDelector = GestureDetector(context,GestureListenner())
    }

}