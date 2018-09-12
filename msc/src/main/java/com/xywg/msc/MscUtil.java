package com.xywg.msc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

/**
 * Created by zhangyinlei on 2018/7/12
 */
public class MscUtil {

    private static final String TAG = MscUtil.class.getSimpleName();

    /**
     * 语音转为文字，使用弹出窗
     *
     * @param context
     * @param path    wav文件地址
     */
    public static void voice2TextDialog(Context context, final OnGetVoiceString onGetVoiceString, String path) {
        RecognizerDialog recognizerDialog = new RecognizerDialog(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i == ErrorCode.SUCCESS) {
                    Log.d(TAG, "语音转文字初始化成功！ ");
                }
            }
        });
        recognizerDialog.setParameter(SpeechConstant.PARAMS, null);// 清空参数
        recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文
        recognizerDialog.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);// 设置引擎
        recognizerDialog.setParameter(SpeechConstant.DOMAIN, "iat");// 应用领域
        recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音0~10000
        recognizerDialog.setParameter(SpeechConstant.VAD_EOS, "5000");
        recognizerDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
        recognizerDialog.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        recognizerDialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, path);
        recognizerDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (null != recognizerResult) {
                    String s = recognizerResult.getResultString();
                    //解析语音
                    String resultString = MscUtil.parseVoice(s);
                    onGetVoiceString.onGetVoiceString(resultString);
                } else {
                    Log.d(TAG, "recognizer result : null");
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.d(TAG, "speechError" + speechError.getErrorDescription());
            }
        });
        recognizerDialog.show();
        TextView titleTv = recognizerDialog.getWindow().getDecorView().findViewWithTag("textlink");
        titleTv.setText("");
    }

    /**
     * 语音转为文字
     *
     * @param context
     * @param path    保存的文件视屏地址
     */
    public static void voice2Text(Context context, final OnGetVoiceString onGetVoiceString, String path) {
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createRecognizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i == ErrorCode.SUCCESS) {
                    Log.d(TAG, "语音转文字初始化成功！ ");
                }
            }
        });
        // 清空参数
        speechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 简体中文:"zh_cn", 美式英文:"en_us"
        speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置听写引擎 "cloud", "local","mixed"  在线  本地  混合 本地的需要本地功能集成
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 短信和日常用语：iat (默认)  视频：video  地图：poi  音乐：music
        speechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        // 普通话：mandarin(默认) 粤 语：cantonese 四川话：lmz
        speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        speechRecognizer.setParameter(SpeechConstant.VAD_EOS, "5000");
        // 设置返回结果格式 听写会话支持json和plain
        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        speechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        speechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, path);
        speechRecognizer.startListening(new RecognizerListener() {
            @Override
            public void onVolumeChanged(int volume, byte[] data) {
                Log.d(TAG, "返回音频数据：" + data.length);
            }

            @Override
            public void onResult(RecognizerResult result, boolean isLast) {
                if (null != result) {
                    String s = result.getResultString();
                    //解析语音
                    String resultString = MscUtil.parseVoice(s);
                    onGetVoiceString.onGetVoiceString(resultString);
                } else {
                    Log.d(TAG, "recognizer result : null");
                }
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            }

            @Override
            public void onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            }

            @Override
            public void onError(SpeechError error) {
                Log.d(TAG, "onError Code：" + error.getErrorCode());
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话id为null
                if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                    String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                    Log.d(TAG, "session id =" + sid);
                }
            }

        });
    }

    /**
     * 科大讯飞读取文字，机器声音
     *
     * @param context
     * @param textString 需要读取的文字
     */
    public static void speekText(Context context, String textString) {
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i == ErrorCode.SUCCESS) {
                    Log.d(TAG, "文字读取初始化成功！ ");
                }
            }
        });
        mTts.setParameter(SpeechConstant.PARAMS, null);// 清空参数
        //设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "vixyun"); // 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成
        mTts.startSpeaking(textString, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                // 开始读取
            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {
                // 合成进度
            }

            @Override
            public void onSpeakPaused() {
                // 暂停读取
            }

            @Override
            public void onSpeakResumed() {
                // 继续播放
            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {
                // 读取进度
            }

            @Override
            public void onCompleted(SpeechError speechError) {
                // 读取完成
            }

            @Override
            public void onEvent(int eventType, int i1, int i2, Bundle bundle) {
                // 以下代码用于获取与云端的会话 id，当业务出错时将会话 id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话 id为null
                if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                    String sid = bundle.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                    Log.d(TAG, "session id =" + sid);
                }
            }
        });
    }

    /**
     * 解析语音json
     */
    public static String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

    public interface OnGetVoiceString {
        void onGetVoiceString(String voiceString);
    }

}
