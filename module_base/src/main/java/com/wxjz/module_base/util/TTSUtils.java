package com.wxjz.module_base.util;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.wxjz.module_base.base.BaseApplication;

import java.util.Locale;

/**
 * @ClassName TTSUtils
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-19 17:46
 * @Version 1.0
 */
public class TTSUtils implements InitListener, SynthesizerListener {

    //先私有化这个类
    private TTSUtils() {

    }

    private static volatile TTSUtils instance = null;
    //初始化成功
    private boolean isInitSuccess = false;
    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认本地发音人
    public static String voicerLocal = "xiaoyan";
    public static String voicerXtts = "xiaoyan";
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_LOCAL;

//    private TextToSpeech mTextToSpeech;

    //单例模式
    public static TTSUtils getInstance() {
        if (instance == null) {
            synchronized (TTSUtils.class) {
                if (instance == null) {
                    instance = new TTSUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 初始化合成对象
     */
    public void init() {
//        mTextToSpeech = new TextToSpeech(BaseApplication.getContext(), this);
//        // 设置语速
//        mTextToSpeech.setSpeechRate(0.5f);
//        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
//        mTextToSpeech.setPitch(1.0f);
        mTts = SpeechSynthesizer.createSynthesizer(BaseApplication.getContext(), this);
    }


    //开始合成
    public void speak(String msg) {
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(BaseApplication.getContext(), "当前需要朗读的内容为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (mTextToSpeech != null) {
//            /*
//                TextToSpeech的speak方法有两个重载。
//                // 执行朗读的方法
//                speak(CharSequence text,int queueMode,Bundle params,String utteranceId);
//                // 将朗读的的声音记录成音频文件
//                synthesizeToFile(CharSequence text,Bundle params,File file,String utteranceId);
//                第二个参数queueMode用于指定发音队列模式，两种模式选择
//                （1）TextToSpeech.QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
//                （2）TextToSpeech.QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，
//                等前面的语音任务执行完了才会执行新的语音任务
//             */
//            mTextToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
//        }


        if (isInitSuccess) {
            if (mTts.isSpeaking()) {
                stop();
            }
            mTts.startSpeaking(msg, this);
        } else {
            init();
        }
    }

    public void stop() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }
//        if (mTextToSpeech != null) {
//            mTextToSpeech.stop();
//        }
    }


    @Override
    public void onInit(int i) {
        Log.d("TTS", "InitListener init() code = " + i);
        if (i != ErrorCode.SUCCESS) {
            isInitSuccess = false;
            Log.d("TTS", "初始化失败,错误码：" + i + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
        } else {
            isInitSuccess = true;
            setParam();
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    }

    /**
     * 参数设置
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
            //如果是本地的话
            //设置使用本地引擎
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            //设置发音人资源路径
            mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
            //设置发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicerLocal);

        }

        // 设置发音语速
        mTts.setParameter(SpeechConstant.SPEED, "40");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
    }

    @Override
    public void onSpeakBegin() {
        //开始播放
        Log.d("TTS", "开放播放");
    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {
        // 合成进度
        Log.d("TTS", "合成进度");
    }

    @Override
    public void onSpeakPaused() {
        //暂停播放
        Log.d("TTS", "暂停播放");
    }

    @Override
    public void onSpeakResumed() {
        //继续播放
        Log.d("TTS", "继续播放");
    }

    @Override
    public void onSpeakProgress(int i, int i1, int i2) {
        //播放进度
        Log.d("TTS", "播放进度");
    }

    @Override
    public void onCompleted(SpeechError speechError) {
        if (speechError == null) {
            Log.d("TTS", "播放完成");
        } else if (speechError != null) {
            Log.d("TTS", "没有播放完成");
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
    }


    public void pause() {
//        if (mTextToSpeech != null) {
//            mTextToSpeech.stop();
//        }
        if (mTts != null) {
            mTts.pauseSpeaking();
        }

    }

    public void resume() {

//        if (mTextToSpeech != null) {
//
//        }
        if (mTts != null) {
            mTts.resumeSpeaking();
        }
    }

    public void release() {
//        if (mTextToSpeech != null) {
//            // 不管是否正在朗读TTS都被打断
//            mTextToSpeech.stop();
//            // 关闭，释放资源
//            mTextToSpeech.shutdown();
//            mTextToSpeech = null;
//        }
        if (null != mTts) {
            mTts.stopSpeaking();
            mTts.destroy();  //退出时释放
        }
    }


    //获取发音人资源路径
    private String getResourcePath() {
        StringBuffer tempBuffer = new StringBuffer();
        String type = "tts";
        if (mEngineType.equals(SpeechConstant.TYPE_XTTS)) {
            type = "xtts";
        }
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(BaseApplication.getContext(), ResourceUtil.RESOURCE_TYPE.assets, type + "/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        if (mEngineType.equals(SpeechConstant.TYPE_XTTS)) {
            tempBuffer.append(ResourceUtil.generateResourcePath(BaseApplication.getContext(), ResourceUtil.RESOURCE_TYPE.assets, type + "/" + TTSUtils.voicerXtts + ".jet"));
        } else {
            tempBuffer.append(ResourceUtil.generateResourcePath(BaseApplication.getContext(), ResourceUtil.RESOURCE_TYPE.assets, type + "/" + TTSUtils.voicerLocal + ".jet"));
        }

        return tempBuffer.toString();
    }


}
