package com.sbl.foags.activity.authenticate.model.video

import android.hardware.Camera
import android.media.MediaRecorder
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.CameraHelp
import com.sbl.foags.utils.RxJavaUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import java.io.File
import java.io.IOException
import java.util.*


class RecordVideoActivity: BaseActivity(), View.OnClickListener {


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var recordLayout: FrameLayout
    private lateinit var surfaceView: SurfaceView
    private lateinit var finishShowView: ImageView
    private lateinit var timeView: TextView
    private lateinit var playView: ImageView
    private lateinit var reRecordView: ImageView
    private lateinit var startAndStopView: ImageView
    private lateinit var sureAndSCView: ImageView

    private var isRecording = false
    private var videoDuration: Long = 0
    private var recordTime: Long = 0
    private val mCameraHelp = CameraHelp()
    private var mediaRecorder: MediaRecorder? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    private var resultPath: String? = null


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_record_video

    override fun initView() {
        bindViews()
    }

    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        recordLayout = findViewById(R.id.recordLayout)
        surfaceView = findViewById(R.id.surfaceView)
        finishShowView = findViewById(R.id.finishShowView)
        timeView = findViewById(R.id.timeView)
        playView = findViewById(R.id.playView)
        reRecordView = findViewById(R.id.reRecordView)
        startAndStopView = findViewById(R.id.startAndStopView)
        sureAndSCView = findViewById(R.id.sureAndSCView)


        backView.setOnClickListener(this)
        playView.setOnClickListener(this)
        reRecordView.setOnClickListener(this)
        startAndStopView.setOnClickListener(this)
        sureAndSCView.setOnClickListener(this)
    }

    override fun loadData() {

        titleView.text = UIUtils.getString(R.string.video_authenticate)

        var hasTest = false

        recordLayout.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                if(hasTest){
                    return
                }
                hasTest = true

                recordLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val height = recordLayout.measuredHeight

                recordLayout.post {

                    val layoutParams = recordLayout.layoutParams
                    layoutParams.width  = (height/16) * 9
                    layoutParams.height = height
                    recordLayout.layoutParams = layoutParams
                }


                surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        mSurfaceHolder = holder
                        mCameraHelp.openCamera(
                            this@RecordVideoActivity,
                            Camera.CameraInfo.CAMERA_FACING_FRONT,
                            mSurfaceHolder
                        )
                    }

                    override fun surfaceChanged(
                        holder: SurfaceHolder,
                        format: Int,
                        width: Int,
                        height: Int
                    ) {
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        mCameraHelp.release()
                    }
                })
            }
        })
    }


    override fun onPause() {
        super.onPause()
        errorStop(UIUtils.getString(R.string.auth_video_please_retry_record))
    }

    override fun onDestroy() {
        super.onDestroy()
        mCameraHelp.release()
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            reRecordView -> {
                initReleaseRecorderState()
            }

            startAndStopView -> {
                if(isRecording){
                    isRecording = false
                    stopRecorder()
                    initFinishRecorderState()
                }else{
                    isRecording = true
                    startRecorder()
                    initStartRecordState()
                    videoDuration = 0
                    recordTime = System.currentTimeMillis()
                    runLoopPro()
                }
            }

            sureAndSCView -> {

                if(reRecordView.visibility == View.INVISIBLE){

                    if (mCameraHelp.cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        mCameraHelp.openCamera(
                            this@RecordVideoActivity,
                            Camera.CameraInfo.CAMERA_FACING_FRONT,
                            mSurfaceHolder
                        )
                    } else {
                        mCameraHelp.openCamera(
                            this@RecordVideoActivity,
                            Camera.CameraInfo.CAMERA_FACING_BACK,
                            mSurfaceHolder
                        )
                    }
                }else{
                    Toast.makeText(this@RecordVideoActivity, resultPath, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun runLoopPro() {
        RxJavaUtil.loop(20, object : RxJavaUtil.OnRxLoopListener {
            override fun takeWhile(): Boolean {
                return isRecording
            }

            override fun onExecute() {
                val currentTime = System.currentTimeMillis()
                videoDuration += currentTime - recordTime
                recordTime = currentTime
                timeView.text = stringForTime(videoDuration)
            }

            override fun onFinish() {
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                errorStop(UIUtils.getString(R.string.auth_video_record_fail))
            }
        })
    }


    private fun errorStop(content: String){
        if(isRecording){
            isRecording = false
            stopRecorder()
            initReleaseRecorderState()
            Toast.makeText(this@RecordVideoActivity, content, Toast.LENGTH_SHORT).show()
        }
    }


    private fun stringForTime(timeMs: Long): String? {
        val totalSeconds = timeMs.toInt() / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        val mFormatBuilder = StringBuilder()
        val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
        mFormatBuilder.setLength(0)
        return if (hours > 0) mFormatter.format(
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        ).toString() else mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }


    private fun initReleaseRecorderState(){
        timeView.text = "00:00"
        playView.visibility = View.GONE
        finishShowView.visibility = View.GONE
        reRecordView.visibility = View.INVISIBLE
        startAndStopView.visibility = View.VISIBLE
        startAndStopView.setImageResource(R.drawable.ic_renzheng_lux)
        sureAndSCView.visibility = View.VISIBLE
        sureAndSCView.setImageResource(R.drawable.ic_renzheng_fzsxt)

        resultPath = null
    }


    private fun initStartRecordState(){
        timeView.text = "00:00"
        playView.visibility = View.GONE
        finishShowView.visibility = View.GONE
        reRecordView.visibility = View.INVISIBLE
        startAndStopView.visibility = View.VISIBLE
        startAndStopView.setImageResource(R.drawable.ic_renzheng_luz)
        sureAndSCView.visibility = View.INVISIBLE
    }


    private fun initFinishRecorderState(){
        val imageFile = File(resultPath)
        if (imageFile.exists()) {
            this.resultPath = resultPath
            playView.visibility = View.VISIBLE
            finishShowView.visibility = View.VISIBLE
            Glide.with(this).load(imageFile).into(finishShowView)
        } else {
            finishShowView.visibility = View.GONE
        }
        reRecordView.visibility = View.VISIBLE
        startAndStopView.visibility = View.INVISIBLE
        sureAndSCView.visibility = View.VISIBLE
        sureAndSCView.setImageResource(R.drawable.ic_renzheng_wancheng)
    }



    private fun startRecorder(){
        resultPath =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "Video" + System.currentTimeMillis() + ".mp4"

        mCameraHelp.camera.unlock()
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setCamera(mCameraHelp.camera)
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder!!.setVideoSize(1920, 1080)
        mediaRecorder!!.setVideoFrameRate(30)
        mediaRecorder!!.setVideoEncodingBitRate(1024 * 1024 * 20)
        //        setCamcorderProfile();
        if (mCameraHelp.cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mediaRecorder!!.setOrientationHint(90)
        } else {
            mediaRecorder!!.setOrientationHint(270)
        }
        mediaRecorder!!.setPreviewDisplay(surfaceView.holder.surface)
        //        mediaRecorder.setMaxDuration(1000 * VIDEO_TIMES);
        mediaRecorder!!.setOutputFile(resultPath)
        //        setVideoOrientation();
        try {
            mediaRecorder!!.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaRecorder!!.start()
    }

    private fun stopRecorder() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder!!.stop()
            } catch (e: Exception) {
            }
            mediaRecorder!!.release()
            mediaRecorder = null
        }
        mCameraHelp.camera?.lock()
    }
}