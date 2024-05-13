package com.example.candycrashnt

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.candycrashnt.ui.OnSwipeListener
import java.util.Arrays.asList

class MainActivity : AppCompatActivity() {
    var gemm = intArrayOf(
        R.drawable.bejeweled_3_gems___green_by_spoildsoul_d3ghgcp,
        R.drawable.bejeweled_3_gems___orange_by_spoildsoul_d3ghght,
        R.drawable.bejeweled_3_gems___purple_by_spoildsoul_d3ghgje,
        R.drawable.bejeweled_3_gems___red_by_spoildsoul_d3ghh6k,
        R.drawable.bejeweled_3_gems___white_by_spoildsoul_d3ghgn7,
        R.drawable.bejeweled_3_gems___yellow_by_spoildsoul_d3ghgoq
    )

    var widthOfBlock :Int = 0
    var noOfBlock :Int = 8
    var widthOfScreen: Int = 0
    lateinit var gemms: ArrayList<ImageView>
    var gemsTobBeDragged :Int = 0
    var gemsToBeReplace :Int = 0
    var notGemm :Int = R.drawable.transparent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        widthOfScreen = displayMetrics.widthPixels
        widthOfBlock = widthOfScreen/noOfBlock

        gemms = ArrayList()
        createBoard()

        for (imageView in gemms){
            imageView.setOnTouchListener(
                object  :OnSwipeListener(this){
                    override fun onSwipeRight() {
                        super.onSwipeRight()
                        gemsTobBeDragged = imageView.id
                        gemsToBeReplace = gemsTobBeDragged + 1
                        gemmsInterChange()
                    }

                    override fun onSwipeLift() {
                        super.onSwipeLift()
                        gemsTobBeDragged = imageView.id
                        gemsToBeReplace = gemsTobBeDragged - 1
                        gemmsInterChange()
                    }

                    override fun onSwipeTop() {
                        super.onSwipeTop()
                        gemsTobBeDragged = imageView.id
                        gemsToBeReplace = gemsTobBeDragged - noOfBlock
                        gemmsInterChange()
                    }

                    override fun onSwipeBottom() {
                        super.onSwipeBottom()
                        gemsTobBeDragged = imageView.id
                        gemsToBeReplace = gemsTobBeDragged + noOfBlock
                        gemmsInterChange()
                    }
                }
            )
        }
        startRepeat()
    }

    private fun gemmsInterChange() {
        var background :Int = gemms.get(gemsToBeReplace).tag as Int
        var background1 :Int = gemms.get(gemsTobBeDragged).tag as Int

        gemms.get(gemsTobBeDragged).setImageResource(background)
        gemms.get(gemsToBeReplace).setImageResource(background1)

        gemms.get(gemsTobBeDragged).setTag(background)
        gemms.get(gemsTobBeDragged).setTag(background1)

    }

    private fun checkRowForThree(){
        for (i in 0..61){
            var choseGems = gemms.get(i).tag
            var isBlank :Boolean = gemms.get(i).tag == notGemm
            val notValid = arrayOf(6,7,14,15,22,23,30,31,38,19,46,47,54,55)
            val list = asList(*notValid)
            if (!list.contains(i)){
                var x=i

                if (gemms.get(x++).tag as Int == choseGems &&
                    !isBlank && gemms.get(x++).tag as Int == choseGems &&
                    gemms.get(x).tag as Int == choseGems){
                    gemms.get(x).setImageResource(notGemm)
                    gemms.get(x).setTag(notGemm)
                    x--
                    gemms.get(x).setImageResource(notGemm)
                    gemms.get(x).setTag(notGemm)
                    x--
                    gemms.get(x).setImageResource(notGemm)
                    gemms.get(x).setTag(notGemm)
                }
            }

        }
        moveDownGems()
    }

    private fun checkColumnForThree(){
        for (i in 0..47){
            var choseGems = gemms.get(i).tag
            var isBlank :Boolean = gemms.get(i).tag == notGemm

            var x=i

            if (gemms.get(x).tag as Int == choseGems &&
                !isBlank &&
                gemms.get(x+noOfBlock).tag as Int == choseGems &&
                gemms.get(x+2*noOfBlock).tag as Int == choseGems){

                gemms.get(x).setImageResource(notGemm)
                gemms.get(x).setTag(notGemm)
                x = x + noOfBlock
                gemms.get(x).setImageResource(notGemm)
                gemms.get(x).setTag(notGemm)
                x = x + noOfBlock
                gemms.get(x).setImageResource(notGemm)
                gemms.get(x).setTag(notGemm)
            }


        }
        moveDownGems()
    }

    val repeatChecker :Runnable = object :Runnable{
        override fun run() {
            try {
                checkColumnForThree()
                checkRowForThree()
                moveDownGems()
            }finally{ }
        }
    }

    private fun startRepeat() {
        repeatChecker.run()
    }

    private fun moveDownGems() {
        val firstRow = arrayOf(1,2,3,4,5,6,7)
        val list = asList(*firstRow)

        for (i in 55 downTo 0){
            if (gemms.get(i +noOfBlock).tag as Int == notGemm){
                gemms.get(i+noOfBlock).setImageResource(gemms.get(i).tag as Int)
                gemms.get(i+noOfBlock).setTag(gemms.get(i).tag as Int)

                gemms.get(i).setImageResource(notGemm)
                gemms.get(i).setTag(notGemm)

                if(list.contains(i) && gemms.get(i).tag == notGemm){
                    var randomColor :Int = Math.abs(Math.random() * gemm.size).toInt()
                    gemms.get(i).setImageResource(gemm[randomColor])
                    gemms.get(i).setTag(gemm[randomColor])
                }
            }
        }
        for (i in 0..7){
            if (gemms.get(i).tag as Int == notGemm){
                var randomColor :Int = Math.abs(Math.random() * gemm.size).toInt()
                gemms.get(i).setImageResource(gemm[randomColor])
                gemms.get(i).setTag(gemm[randomColor])
            }
        }
    }

    private fun createBoard() {
        val grindLayaout = findViewById<GridLayout>(R.id.board)
        grindLayaout.rowCount = noOfBlock
        grindLayaout.columnCount = noOfBlock
        grindLayaout.layoutParams.width = widthOfScreen
        grindLayaout.layoutParams.height = widthOfScreen

        for (i in 0 until noOfBlock * noOfBlock){
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            var random :Int = Math.floor(Math.random() * gemm.size).toInt()

            imageView.setImageResource(gemm[random])
            imageView.setTag(gemm[random])

            gemms.add(imageView)
            grindLayaout.addView(imageView)

        }
    }
}