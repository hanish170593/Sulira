package com.application.suliraapplication.activity.heartbeat

import android.animation.ValueAnimator
import android.hardware.Camera
import android.hardware.Camera.PreviewCallback
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.UpdateHealthConditionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_layout_heart_rate_monitor.*
import java.util.concurrent.atomic.AtomicBoolean

class HeartCheckActivity : BaseActivity(), View.OnTouchListener, View.OnClickListener {

    private val updateHealthConditionViewModel by lazy {
        ViewModelProvider(this).get(UpdateHealthConditionViewModel::class.java)
    }

    companion object {
        var current = TYPE.GREEN
    }

    private var mAnimator: ValueAnimator? = null

    private var age = 0

    private val TAG = "HeartRateMonitor"
    private val processing =
        AtomicBoolean(false)
    private var previewHolder: SurfaceHolder? = null
    private var camera: Camera? = null
    private var averageIndex = 0
    private val averageArraySize = 4
    private val averageArray = IntArray(averageArraySize)

    private var beatsIndex = 0
    private val beatsArraySize = 3
    private val beatsArray =
        IntArray(beatsArraySize)
    private var beats = 0.0
    private var startTime: Long = 0
    var heartRate: String? = null
    private var currentProgress: Int = 0
    private var lastProgress: Int = 0
    private var newValeComing: Boolean = false
    private var reverseComing: Boolean = false

    private fun getSmallestPreviewSize(
        width: Int,
        height: Int,
        parameters: Camera.Parameters
    ): Camera.Size? {
        var result: Camera.Size? = null
        for (size in parameters.supportedPreviewSizes) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size
                } else {
                    val resultArea = result.width * result.height
                    val newArea = size.width * size.height
                    if (newArea < resultArea) result = size
                }
            }
        }
        return result
    }

    enum class TYPE {
        GREEN, RED
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_layout_heart_rate_monitor)

        tvDateHeart.text = intent.getStringExtra("lastUpdate")
        startBPM()
        tvStartBPM.setOnClickListener {
            tvStartBPM.visibility = View.GONE
            setFlashParam()
        }
        csHeatBeat.setOnTouchListener(this)

        ivBackHeartRate.setOnClickListener(this)
        btUpdateHeartRate.setOnClickListener(this)

        updateHealthConditionViewModel.successful.observe(this, Observer {
            dismissDialog()

            if (it) {
                setError(updateHealthConditionViewModel.customResponse.msg)
                super.onBackPressed()
            } else {
                setError(updateHealthConditionViewModel.message)
            }

        })

        heartRateMeasureDialog()
    }

    private fun animateSeekBar(progress: Int) {
        newValeComing = true
        val progressData: Int = progress * 10
        currentProgress *= 10
        if (progressData > currentProgress) {
            newValeComing = false
            for (i in lastProgress until progressData) {
                lastProgress = i
                if (newValeComing) {
                    break
                }
                setSeekProgress(i, currentProgress)
            }
        } else {

            if (reverseComing) {
                return
            }

            newValeComing = false
            for (i in lastProgress downTo progressData) {
                if (newValeComing) {
                    lastProgress = 0
                    break
                }
                setSeekProgress(i, currentProgress)
            }
        }
    }

    private fun setSeekProgress(progress: Int, currentProgress: Int) {
        if (mAnimator != null) {
            mAnimator!!.cancel()
            mAnimator!!.removeAllUpdateListeners()
            mAnimator!!.removeAllListeners()
            mAnimator = null
        }
        mAnimator = ValueAnimator.ofInt(currentProgress, progress)
        mAnimator!!.duration =
            resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        mAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        mAnimator!!.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            csHeatBeat.progress = value
        }
        mAnimator!!.start()
    }

    private fun startBPM() {
        previewHolder = preview!!.holder
        previewHolder!!.addCallback(surfaceCallback)
        previewHolder!!.setType(
            SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS
        )
        val singUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)
        age = singUpModel.userInfo.age.toInt()
        camera =
            Camera.open()
        startTime =
            System.currentTimeMillis()
    }

    public override fun onPause() {
        super.onPause()
        if (camera != null) {
            camera!!.setPreviewCallback(
                null
            )
            camera!!.stopPreview()
            camera!!.release()
            camera = null
        }
    }

    private val previewCallback: PreviewCallback = object : PreviewCallback {
        override fun onPreviewFrame(
            data: ByteArray,
            cam: Camera
        ) {

            if (tvStartBPM.visibility == View.VISIBLE) {
                return
            }

            if (data == null) throw NullPointerException()
            val size = cam.parameters.previewSize ?: throw NullPointerException()
            if (!processing.compareAndSet(false, true)) return
            val width = size.width
            val height = size.height
            val imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width)

            if (imgAvg < 200) {
                processing.set(false)
                rlHeartView.visibility = View.GONE
                tvPlaceYourFinger.visibility = View.VISIBLE
                startTime = System.currentTimeMillis()
                beats = 0.0
                animateSeekBar(0)
                reverseComing = true
                currentProgress = 0
                tvHeartRate.text = "00"
                return
            }

            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false)
                return
            }

            var averageArrayAvg = 0
            var averageArrayCnt = 0
            for (i in averageArray.indices) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i]
                    averageArrayCnt++
                }
            }

            val rollingAverage =
                if (averageArrayCnt > 0) averageArrayAvg / averageArrayCnt else 0
            var newType: TYPE =
                current
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED
                if (newType != current) {
                    beats++
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN
            }
            if (averageIndex == averageArraySize) averageIndex = 0
            averageArray[averageIndex] = imgAvg
            averageIndex++

            // Transitioned from one state to another to the same
            if (newType != current) {
                current = newType
                image!!.postInvalidate()
            }

            val endTime = System.currentTimeMillis()
            val totalTimeInSecs = (endTime - startTime) / 1000.0

            if (totalTimeInSecs > 1) {
                rlHeartView.visibility = View.VISIBLE
                tvPlaceYourFinger.visibility = View.GONE
                if (reverseComing) {
                    lastProgress = 0
                }
                reverseComing = false
                animateSeekBar(totalTimeInSecs.toInt())
                currentProgress = totalTimeInSecs.toInt()
            }
            setTemporaryData(totalTimeInSecs.toInt())
            if (totalTimeInSecs >= 10) {
                val bps =
                    beats / totalTimeInSecs
                val dpm = (bps * 60.0).toInt()

                if (dpm < 30 || dpm > 180) {
                    startTime =
                        System.currentTimeMillis()
                    beats =
                        0.0
                    processing.set(false)
                    return
                }

                if (beatsIndex == beatsArraySize) beatsIndex = 0
                beatsArray[beatsIndex] = dpm
                beatsIndex++
                var beatsArrayAvg = 0
                var beatsArrayCnt = 0
                for (i in beatsArray.indices) {
                    if (beatsArray[i] > 0
                    ) {
                        beatsArrayAvg += beatsArray[i]
                        beatsArrayCnt++
                    }
                }
                val beatsAvg = beatsArrayAvg / beatsArrayCnt
                heartRate = beatsAvg.toString()
                tvHeartRate.text = heartRate
                startTime = System.currentTimeMillis()
                beats = 0.0
                onPause()
            }
            processing.set(
                false
            )
        }
    }

    private fun setTemporaryData(totalTimeInSecs: Int) {
        var beatsIndex = 0
        val beatsArray =
            IntArray(beatsArraySize)
        val bps =
            beats / totalTimeInSecs
        val dpm = (bps * 60.0).toInt()
        if (beatsIndex == beatsArraySize) beatsIndex = 0
        beatsArray[beatsIndex] = dpm
        beatsIndex++
        var beatsArrayAvg = 0
        var beatsArrayCnt = 0
        for (i in beatsArray.indices) {
            if (beatsArray[i] > 0) {
                beatsArrayAvg += beatsArray[i]
                beatsArrayCnt++
            }
        }

        if (beatsArrayCnt != 0) {
            val beatsAvg = beatsArrayAvg / beatsArrayCnt
            if (beatsAvg.toString().length > 3) {
                return
            }

            heartRate = beatsAvg.toString()
            tvHeartRate.text = heartRate
        }
    }

    private fun setFlashParam() {
        val parameters =
            camera!!.parameters
        parameters.flashMode = Camera.Parameters.FLASH_MODE_TORCH
        camera!!.parameters = parameters
    }

    private val surfaceCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                camera!!.setPreviewDisplay(
                    previewHolder
                )
                camera!!.setPreviewCallback(
                    previewCallback
                )
            } catch (t: Throwable) {
                Log.e("surfaceCallbackDemo", "Exception in setPreviewDisplay()", t)
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            val parameters =
                camera!!.parameters
            val size =
                getSmallestPreviewSize(width, height, parameters)
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height)
                Log.d(TAG, "Using width=" + size.width + " height=" + size.height)
            }
            camera!!.parameters = parameters
            camera!!.startPreview()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return true
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            ivBackHeartRate -> super.onBackPressed()
            btUpdateHeartRate -> {
                showDialog()
                updateHealthConditionViewModel.updateHealthCondition(
                    PreferenceManager().userId.toString(),
                    "heartRate",
                    tvHeartRate.text.toString()
                )
            }
        }
    }

    private fun heartRateMeasureDialog() {
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_layout_heart_rate_measure, null)
        mBottomSheetDialog.setContentView(sheetView)
        val ivDismissDialog: AppCompatImageView? =
            mBottomSheetDialog.findViewById(R.id.ivDismissDialog)
        val tvDismissDialog: AppCompatTextView? =
            mBottomSheetDialog.findViewById(R.id.tvDismissDialog)
        val btDismissDialog: AppCompatButton? =
            mBottomSheetDialog.findViewById(R.id.btDismissDialog)
        ivDismissDialog!!.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        tvDismissDialog!!.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        btDismissDialog!!.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
        mBottomSheetDialog.show()
    }

}