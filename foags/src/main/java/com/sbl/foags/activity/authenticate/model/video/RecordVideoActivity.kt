package com.sbl.foags.activity.authenticate.model.video

import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.lansosdk.videoeditor.LanSoEditor
import com.lansosdk.videoeditor.LanSongFileUtil
import com.libyuv.LibyuvUtil
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.zhaoss.weixinrecorded.util.CameraHelp
import com.zhaoss.weixinrecorded.util.MyVideoEditor
import com.zhaoss.weixinrecorded.util.RecordUtil
import com.zhaoss.weixinrecorded.util.RecordUtil.OnPreviewFrameListener
import com.zhaoss.weixinrecorded.util.RxJavaUtil
import com.zhaoss.weixinrecorded.util.RxJavaUtil.OnRxAndroidListener
import com.zhaoss.weixinrecorded.util.RxJavaUtil.OnRxLoopListener
import java.io.File
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

    private var isRecordVideo = false
    private var videoDuration: Long = 0
    private var recordTime: Long = 0
    private var videoPath: String? = null
    private var audioPath: String? = null
    private val mVideoEditor = MyVideoEditor()
    private val mCameraHelp = CameraHelp()
    private var mSurfaceHolder: SurfaceHolder? = null
    private var recordUtil: RecordUtil? = null
    private var mOnPreviewFrameListener: OnPreviewFrameListener? = null

    private var resultPath: String? = null


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_record_video

    override fun initView() {
        LanSoEditor.initSDK(this, null)
        LanSongFileUtil.setFileDir("/sdcard/FOAgsVideoAuth/" + System.currentTimeMillis() + "/")
        LibyuvUtil.loadLibrary()

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
                val width = recordLayout.measuredWidth
                val height = recordLayout.measuredHeight

                recordLayout.post {

                    val layoutParams = recordLayout.layoutParams
                    layoutParams.width  = (height/16) * 9
                    layoutParams.height = height
                    recordLayout.layoutParams = layoutParams
                }




                mCameraHelp.setPreviewCallback { data, camera ->
                    if (isRecordVideo && mOnPreviewFrameListener != null) {
                        mOnPreviewFrameListener!!.onPreviewFrame(data)
                    }
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
        onError(UIUtils.getString(R.string.auth_video_please_retry_record))
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCamera()
    }

    private fun releaseCamera() {
        mCameraHelp.release()
        recordUtil?.stop()
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
                if(isRecordVideo){
                    isRecordVideo = false
                    upEvent()
                    finishVideo()
                }else{
                    isRecordVideo = true
                    startRecord()
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


    private fun startRecord() {
        RxJavaUtil.run<Boolean>(object : OnRxAndroidListener<Boolean?> {
            @Throws(Throwable::class)
            override fun doInBackground(): Boolean? {
                videoPath =
                    LanSongFileUtil.DEFAULT_DIR + System.currentTimeMillis() + ".h264"
                audioPath =
                    LanSongFileUtil.DEFAULT_DIR + System.currentTimeMillis() + ".pcm"
                val isFrontCamera =
                    mCameraHelp.cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT
                val rotation: Int
                rotation = if (isFrontCamera) {
                    270
                } else {
                    90
                }
                recordUtil = RecordUtil(
                    videoPath,
                    audioPath,
                    mCameraHelp.width,
                    mCameraHelp.height,
                    rotation,
                    isFrontCamera
                )
                return true
            }

            override fun onFinish(result: Boolean?) {
                mOnPreviewFrameListener = recordUtil!!.start()
                videoDuration = 0
                recordTime = System.currentTimeMillis()
                runLoopPro()

                initStartRecordState()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                onError(UIUtils.getString(R.string.auth_video_record_fail))
            }
        })
    }


    private fun runLoopPro() {
        RxJavaUtil.loop(20, object : OnRxLoopListener {
            override fun takeWhile(): Boolean {
                return recordUtil != null && recordUtil!!.isRecording
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
                onError(UIUtils.getString(R.string.auth_video_record_fail))
            }
        })
    }


    private fun finishVideo() {
        RxJavaUtil.run<String>(object : OnRxAndroidListener<String?> {
            @Throws(Exception::class)
            override fun doInBackground(): String? { //合并h264
                //h264转mp4
                var mp4Path =
                    LanSongFileUtil.DEFAULT_DIR + System.currentTimeMillis() + ".mp4"
                mVideoEditor.h264ToMp4(videoPath, mp4Path)
                //合成音频
                val aacPath: String = mVideoEditor.executePcmEncodeAac(
                    audioPath,
                    RecordUtil.sampleRateInHz,
                    RecordUtil.channelCount
                )
                //音视频混合
                mp4Path = mVideoEditor.executeVideoMergeAudio(mp4Path, aacPath)
                return mp4Path
            }

            override fun onFinish(result: String?) {
                initFinishRecorderState(result!!)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                onError(UIUtils.getString(R.string.auth_video_edit_fail))
            }
        })
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


    private fun onError(content: String){
        if(isRecordVideo){
            Toast.makeText(this@RecordVideoActivity, content, Toast.LENGTH_SHORT).show()
            upEvent()
            initReleaseRecorderState()
        }
    }

    private fun upEvent() {
        if (recordUtil != null) {
            recordUtil!!.stop()
            recordUtil = null
        }
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


    private fun initFinishRecorderState(url: String){
        val imageFile = File(url)
        if (imageFile.exists()) {
            this.resultPath = url
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
}