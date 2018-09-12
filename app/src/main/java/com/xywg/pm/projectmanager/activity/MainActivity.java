package com.xywg.pm.projectmanager.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.Constant;
import com.example.common.base.BaseActivity;
import com.example.common.dialog.CameraOrChooseDialog;
import com.example.common.dialog.ConfirmDialogFragment;
import com.example.common.listener.OnPositiveClickListener;
import com.example.common.sutils.utils.EventBusUtil;
import com.example.common.sutils.utils.GlideUtil;
import com.example.common.sutils.utils.MainHandlerUtil;
import com.example.common.sutils.utils.NfcUtil;
import com.example.common.sutils.utils.ScreenUtil;
import com.example.common.sutils.utils.ToastUtil;
import com.example.common.sutils.utils.io.FileUtil;
import com.example.common.sutils.utils.io.GsonUtil;
import com.example.common.sutils.utils.io.SPUtil;
import com.example.common.widget.album.CameraUtil;
import com.example.common.widget.treeview.Dept;
import com.example.common.widget.treeview.Node;
import com.example.common.widget.treeview.NodeHelper;
import com.example.common.widget.treeview.NodeTreeAdapter;
import com.example.common.widget.view.CustomListView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechRecognizer;
import com.moudle.bdlocation.locationutil.LocationUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xywg.msc.MscUtil;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.adapter.OprationAdapter;
import com.xywg.pm.projectmanager.bean.ChildrenBean;
import com.xywg.pm.projectmanager.bean.PId;
import com.xywg.pm.projectmanager.bean.RecordTextBean;
import com.xywg.pm.projectmanager.bean.RfidLocationBean;
import com.xywg.pm.projectmanager.bean.UserInfoBean;
import com.xywg.pm.projectmanager.broadcast.NetworkReceiver;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.message.NetWorkEvent;
import com.xywg.pm.projectmanager.presenter.IMainPresenter;
import com.xywg.pm.projectmanager.presenter.MainPresenter;
import com.xywg.pm.projectmanager.util.ADFilterTool;
import com.xywg.pm.projectmanager.util.RetrofitUtil;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.functions.Consumer;
import wendu.dsbridge.CompletionHandler;
import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;

@Route(path = RouterPath.ROUTER_MAIN)
public class MainActivity extends BaseActivity<MainPresenter> implements IMainPresenter.IMainView, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // header
    private ImageView headImage;
    private TextView nameText, companyText;
    private CheckBox checkCompany;

    private DrawerLayout drawerLayout;
    private LinearLayout mLlError;
    private DWebView dWebView;
    private CustomListView mLvOperation;
    private ListView mLvCompany;
    private LinearLayout mLlParent;
    private RelativeLayout mRlRecord;
    private Switch mThemeSwitch;

    // data
    private String mSessionId;
    private String mDefaultProjectIdJson;
    private boolean mLoadError;
    private boolean mTheme;
    private AnimationDrawable mRecordAnimation;// 录音动画
    private CompletionHandler mCompletionImageHandler;// 回调给H5，图片
    private CompletionHandler mCompletionRecordHandler;// 回调给H5，语音和文字
    private long mRecordTime;//语音时长
    private SpeechRecognizer mSpeechRecognizer;// 录音
    private StringBuilder mStringBuilder = new StringBuilder();// 文字
    private String mRecordPath;// 语音文件
    private String mLastRecordPath;// 上一个语音

    private NetworkReceiver mNetworkReceiver;
    // h5

    private final static String HOME_COOKIES = "http://192.168.1.64:8081/app/user/login";
    private final static String HOME_DEFAULT_URL = "http://192.168.1.64:8091/#/home";
    private final static String HOME_OPERATION_URL = "http://192.168.1.64:8091/#/homeList";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //context is Activity context
        this.deleteDatabase("webview.db");
        this.deleteDatabase("webviewCache.db");
        mTheme = (boolean) SPUtil.getInstance(Const.ConstantSp.SP_THEME_FILE_NAME).get(Const.ConstantSp.SP_THEME_KEY, true);
        if (mTheme) {
            MainActivity.this.setTheme(R.style.DayTheme);
        } else {
            MainActivity.this.setTheme(R.style.NightTheme);
        }
        setContentView(R.layout.activity_main);
        EventBusUtil.register(this);
        initLocation();
        //在代码中实现动态注册的方式
        IntentFilter filter = new IntentFilter();
        mNetworkReceiver = new NetworkReceiver();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    protected void findViews() {
        // head
        headImage = findViewById(R.id.head_image);
        nameText = findViewById(R.id.user_name);
        companyText = findViewById(R.id.company_name);
        checkCompany = findViewById(R.id.check_company);

        // main
        drawerLayout = findViewById(R.id.drawer_layout);
        mLlError = findViewById(R.id.ll_error);
        dWebView = findViewById(R.id.d_web_view);
        mLvCompany = findViewById(R.id.lv_choose_project_item);
        mLlParent = findViewById(R.id.ll_parent);
        mLvOperation = findViewById(R.id.lv_choose_operation_item);
        findViewById(R.id.ll_setting).setOnClickListener(this);
        mRlRecord = findViewById(R.id.review_voice_rl);
        mThemeSwitch = findViewById(R.id.theme_switch);
        ImageView ivRecord = findViewById(R.id.review_voice_icon);
        mRecordAnimation = (AnimationDrawable) ivRecord.getDrawable();
        // 先隐藏布局
        hideFromTopAnimation();
    }

    @Override
    protected void init() {
        GlideUtil.loadImageRound(this, R.mipmap.launch_icon, headImage);
        checkCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showFromBottomAnimation();
                } else {
                    hideFromTopAnimation();
                }
            }
        });
        if (mTheme) {
            mThemeSwitch.setChecked(true);
        } else {
            mThemeSwitch.setChecked(false);
        }
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SPUtil.getInstance(Const.ConstantSp.SP_THEME_FILE_NAME).put(Const.ConstantSp.SP_THEME_KEY, true);
                } else {
                    SPUtil.getInstance(Const.ConstantSp.SP_THEME_FILE_NAME).put(Const.ConstantSp.SP_THEME_KEY, false);
                }
                Intent intent = MainActivity.this.getIntent();
                finish();
                startActivity(intent);
            }
        });
        initView();
        initGetData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebSettings webSettings = dWebView.getSettings();
        webSettings.setDomStorageEnabled(true);//开启DOM storage API功能
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        if (Const.DEBUG) {
            // webview
            DWebView.setWebContentsDebuggingEnabled(true);
        }
        // 设置默认显示的主页url
        RetrofitUtil.synCookies(this, HOME_COOKIES);
        dWebView.clearCache(true);
        dWebView.addJavascriptObject(new JsApi(), null);
        dWebView.disableJavascriptDialogBlock(true);
        dWebView.loadUrl(HOME_DEFAULT_URL);
        dWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(HOME_DEFAULT_URL)) {
                    if (!TextUtils.isEmpty(mSessionId))
                        UsJsFun(Const.ConstantGetJs.GET_SESSION_ID, new Object[]{mSessionId});
                    if (!TextUtils.isEmpty(mDefaultProjectIdJson))
                        UsJsFun(Const.ConstantGetJs.GET_PROJECT_ID, new Object[]{mDefaultProjectIdJson});
                }
                if (!mLoadError) {
                    mLlError.setVisibility(View.GONE);
                    dWebView.setVisibility(View.VISIBLE);
                }
                mLoadError = false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mLlError.setVisibility(View.VISIBLE);
                dWebView.setVisibility(View.GONE);
                mLoadError = true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLlError.setVisibility(View.VISIBLE);
                dWebView.setVisibility(View.GONE);
                mLoadError = true;
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                if (!ADFilterTool.hasAd(MainActivity.this, url)) {
                    //正常加载
                    return super.shouldInterceptRequest(view, url);
                } else {
                    //含有广告资源屏蔽请求
                    return new WebResourceResponse(null, null, null);
                }
            }

        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = dWebView.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(dWebView.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void initGetData() {
        UserInfoBean userInfoBean = null;
        UserInfoBean.UserBean userBean = null;
        List<UserInfoBean.OrginsBean> orginsBeanList = null;
        List<UserInfoBean.OperationBean> operationBeanList = null;
        if (null != getIntent()) {
            userInfoBean = (UserInfoBean) getIntent().getSerializableExtra(Const.ConstantKey.LOGIN_KEY);
        }

        if (null != userInfoBean) {
            userBean = userInfoBean.getUser();
            operationBeanList = userInfoBean.getOperation();
            orginsBeanList = userInfoBean.getOrgins();
            mSessionId = userInfoBean.getSessionId();
            Const.Constant.mUserName = userBean.getSuNickName();
            String orgName = userBean.getOrgName();
            if (!TextUtils.isEmpty(Const.Constant.mUserName))
                nameText.setText(Const.Constant.mUserName);
            if (!TextUtils.isEmpty(orgName))
                companyText.setText(orgName);
        }

        // 传递默认projectId
        if (null != userBean) {
            String orgId = String.valueOf(userBean.getOrgId());
            mDefaultProjectIdJson = GsonUtil.obj2String(new PId(orgId, ""));
        }

        // 设置数据源，默认显示第一个
        if (null != operationBeanList) {
            initLeftOperation(operationBeanList);
        }

        // 设置数据源
        if (null != orginsBeanList) {
            initLeftProject(orginsBeanList);
        }
    }

    private void initLeftOperation(final List<UserInfoBean.OperationBean> operationBeanList) {
        OprationAdapter oprationAdapter = new OprationAdapter(operationBeanList, this);
        mLvOperation.setAdapter(oprationAdapter);
        mLvOperation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfoBean.OperationBean operationBean = operationBeanList.get(position);
                if (null != operationBean) {
                    Const.Constant.mOperationId = String.valueOf(operationBean.getId());
                    dWebView.loadUrl(HOME_OPERATION_URL);
                }
                closeDrawer();
            }
        });
    }

    private void initLeftProject(List<UserInfoBean.OrginsBean> orginsBeanList) {
        List<Node> data = null;
        LinkedList<Node> linkedList = null;
        if (null == orginsBeanList) return;
        data = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < orginsBeanList.size(); i++) {
            data.add(new Dept(i + 1, 0, orginsBeanList.get(i), orginsBeanList.get(i).getLabel()));
            List<ChildrenBean> childrenBeanList = orginsBeanList.get(i).getChildren();
            getChildList(i + 1, data, childrenBeanList);
        }
        linkedList.addAll(NodeHelper.sortNodes(data));
        NodeTreeAdapter nodeTreeAdapter = new NodeTreeAdapter(this, mLvCompany, linkedList);
        mLvCompany.setAdapter(nodeTreeAdapter);
        nodeTreeAdapter.setOnClickUidListener(new NodeTreeAdapter.OnSetClickObject() {
            @Override
            public void onSetClickObject(Object object) {
                String orgId = "";
                String project = "";
                int flag = 0;
                String label = "";
                if (object instanceof UserInfoBean.OrginsBean) {
                    UserInfoBean.OrginsBean orginsBean = (UserInfoBean.OrginsBean) object;
                    label = orginsBean.getLabel();
                    flag = orginsBean.getFlag();
                    if (flag == Const.ORG_ID) {
                        orgId = String.valueOf(orginsBean.getId());
                    } else if (flag == Const.PRO_ID) {
                        orgId = String.valueOf(orginsBean.getPid());
                        project = String.valueOf(orginsBean.getId());
                    }
                } else if (object instanceof ChildrenBean) {
                    ChildrenBean childrenBean = (ChildrenBean) object;
                    label = childrenBean.getLabel();
                    flag = childrenBean.getFlag();
                    if (flag == Const.ORG_ID) {
                        orgId = String.valueOf(childrenBean.getId());
                    } else if (flag == Const.PRO_ID) {
                        orgId = String.valueOf(childrenBean.getPid());
                        project = String.valueOf(childrenBean.getId());
                    }
                }
                dWebView.reload();
                String json = GsonUtil.obj2String(new PId(orgId, project));
                UsJsFun(Const.ConstantGetJs.GET_PROJECT_ID, new Object[]{json});
                companyText.setText(label);
                checkCompany.setChecked(false);
            }
        });
    }

    /**
     * 调用js方法，传值给H5
     *
     * @param fun
     * @param args
     */
    public void UsJsFun(String fun, Object[] args) {
        if (null != dWebView) {
            dWebView.callHandler(fun, args, new OnReturnValue<Object>() {
                @Override
                public void onValue(Object o) {
                    Log.d(TAG, o.toString());
                }
            });
        }
    }

    /**
     * js调用本地方法
     */
    public class JsApi {

        @JavascriptInterface
        public void backHomePage(Object msg) {
            MainHandlerUtil.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    openDrawer();
//                    CPushMessage cPushMessage = new CPushMessage();
//                    cPushMessage.setTitle("阿里云推送");
//                    cPushMessage.setContent("阿里云推送服务");
//                    CustomNotificationUtil.buildNotification(MainActivity.this, cPushMessage);
                }
            });
        }

        @JavascriptInterface
        public void addImages(Object msg, final CompletionHandler<String> handler) {
            MainHandlerUtil.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    mCompletionImageHandler = handler;
                    CameraOrChooseDialog cameraOrChooseDialog = new CameraOrChooseDialog(MainActivity.this, 1);
                    cameraOrChooseDialog.show(getSupportFragmentManager(), "");
                }
            });
        }

        @JavascriptInterface
        public void commonFun(final Object msg, final CompletionHandler<String> handler) {
            MainHandlerUtil.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    int constant = (int) msg;
                    switch (constant) {
                        case Const.ConstantDS.DS_LOGIN:// 登录
                            dsLogin();
                            handler.complete();
                            break;
                        case Const.ConstantDS.DS_RFID: // 刷卡
                            dsNfc();
                            handler.complete();
                            break;
                        case Const.ConstantDS.DS_OPERATION_ID:// 获取operationId
                            handler.complete(Const.Constant.mOperationId);
                            break;
                        case Const.ConstantDS.DS_WEATHER:// 天气
                            String weatherJson = presenter.getWeatherJson();
                            if (!TextUtils.isEmpty(weatherJson))
                                handler.complete(weatherJson);
                            handler.complete();
                            break;
                        case Const.ConstantDS.DS_RECORD:// 语音
                            if (FileUtil.isFile(mRecordPath))
                                playRecord(mRecordPath);
                            handler.complete();
                            break;
                        case Const.ConstantDS.DS_USER_NAME:// 用户名
                            handler.complete(Const.Constant.mUserName);
                            break;
                    }
                }
            });
        }

        @JavascriptInterface
        public void openNetRadio(final Object msg, final CompletionHandler<String> handler) {
            MainHandlerUtil.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    String recordUrl = (String) msg;
                    if (!TextUtils.isEmpty(recordUrl) && !recordUrl.equals("null")) {
                        playRecord(Const.AUDIO_URL + recordUrl);
                    }
                    handler.complete();
                }
            });
        }

        @JavascriptInterface
        public void testRecord(final Object msg, final CompletionHandler<String> handler) {
            MainHandlerUtil.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    mCompletionRecordHandler = handler;
                    String constant = (String) msg;
                    switch (constant) {
                        case "1":// 开始
                            RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    , Manifest.permission.RECORD_AUDIO)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                dsAudio();
                                            } else {
                                                ToastUtil.show("请开启相关录音权限！");
                                            }
                                        }
                                    });
                            break;
                        case "2":// 结束
                            if (null != mSpeechRecognizer)
                                mSpeechRecognizer.stopListening();
                            break;
                    }
                }
            });
        }

    }

    /**
     * 登录
     */
    private void dsLogin() {
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
        confirmDialogFragment.setTitle("登录信息过期，请重新登录")
                .setCancelClick("取消", null)
                .setConfirmClick("确定", new OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick(View view, String content) {
                        clearCookies(MainActivity.this);
                        ARouter.getInstance().build(RouterPath.ROUTER_LOGIN).navigation();
                        MainActivity.this.finish();
                    }
                }).show(getSupportFragmentManager(), "");
    }

    /**
     * nfc功能
     */
    private void dsNfc() {
        if (!NfcUtil.getInstance().isSupportNfc()) {
            ToastUtil.show("该设备不支持NFC！");
        } else {
            if (!NfcUtil.getInstance().isNfcEnabled()) {
                ConfirmDialogFragment nfcDialog = new ConfirmDialogFragment();
                nfcDialog.setTitle("请打开手机NFC功能")
                        .setCancelClick("取消", null)
                        .setConfirmClick("确定", new OnPositiveClickListener() {
                            @Override
                            public void onPositiveClick(View view, String content) {
                                Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                                MainActivity.this.startActivity(setNfc);
                            }
                        }).show(getSupportFragmentManager(), "");
            }
        }
    }

    /**
     * 语音功能
     */
    private void dsAudio() {
        if (!FileUtil.isHasSdCard()) {
            ToastUtil.show("没有SD卡");
            return;
        }
        voice2Text();
    }

    /**
     * 语音转为文字
     */
    public void voice2Text() {
        mStringBuilder.setLength(0);
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i == ErrorCode.SUCCESS) {
                    Log.d(TAG, "语音转文字初始化成功！ ");
                }
            }
        });
        // 清空参数
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null);
        // 简体中文:"zh_cn", 美式英文:"en_us"
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置听写引擎 "cloud", "local","mixed"  在线  本地  混合 本地的需要本地功能集成
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 短信和日常用语：iat (默认)  视频：video  地图：poi  音乐：music
        mSpeechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        // 普通话：mandarin(默认) 粤 语：cantonese 四川话：lmz
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "5000");
        // 设置返回结果格式 听写会话支持json和plain
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mRecordPath = Const.ConstantPath.AUDIO_PATH + System.currentTimeMillis() + ".wav";
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, mRecordPath);
        mSpeechRecognizer.startListening(new RecognizerListener() {
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
                    mStringBuilder.append(resultString);
                    if (isLast) {
                        mRlRecord.setVisibility(View.GONE);
                        mRecordAnimation.stop();
                        mRecordTime = System.currentTimeMillis() - mRecordTime;
                        int recordCount = (int) (mRecordTime / 1000);
                        if (recordCount > 30) {
                            ToastUtil.show("语音时间不得超过30s！");
                            mRecordPath = mLastRecordPath;
                            return;
                        }
                        if (FileUtil.isFile(mRecordPath)) {
                            mLastRecordPath = mRecordPath;
                            presenter.uploadRadio(mRecordPath, recordCount);
                        }
                    }
                } else {
                    Log.d(TAG, "recognizer result : null");
                }
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                mRlRecord.setVisibility(View.GONE);
                if (null != mRecordAnimation) {
                    mRecordAnimation.stop();
                }
            }

            @Override
            public void onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
                mRecordTime = System.currentTimeMillis();
                mRlRecord.setVisibility(View.VISIBLE);
                if (null != mRecordAnimation) {
                    mRecordAnimation.start();
                }
            }

            @Override
            public void onError(SpeechError error) {
                mRlRecord.setVisibility(View.GONE);
                if (null != mRecordAnimation) {
                    mRecordAnimation.stop();
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        initNfc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopNfc();
    }

    /**
     * 初始化nfc
     */
    private void initNfc() {
        if (NfcUtil.getInstance().getNfcAdapter() != null) {
            // nfc功能
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
            String[][] techLists = new String[][]{new String[]{NfcA.class.getName()},
                    new String[]{NfcF.class.getName()},
                    new String[]{NfcB.class.getName()},
                    new String[]{NfcV.class.getName()}};// 允许扫描的标签类型
            NfcUtil.getInstance().getNfcAdapter().enableForegroundDispatch(this, pendingIntent, null, techLists);
        }
    }

    /**
     * 关闭nfc
     */
    private void stopNfc() {
        if (NfcUtil.getInstance().getNfcAdapter() != null) {
            NfcUtil.getInstance().getNfcAdapter().disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null) return;
        // 百度定位获取信息
        initLocation(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_setting) { //设置
            ARouter.getInstance().build(RouterPath.ROUTER_SETTING)
                    .navigation();
        }
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case Constant.REQUEST_CODE_TAKE_PHOTO:
                    ARouter.getInstance().build(RouterPath.ROUTER_EDIT_PHOTO)
                            .withString(EditPhotoActivity.PHOTO_PATH, CameraUtil.getImageFile(CameraUtil.mImageCacheName).getAbsolutePath())
                            .navigation(this, Const.ConstantCode.EDIT_PHOTO_REQUEST_CODE);
                    break;
                // 选择照片
                case Constant.REQUEST_CODE_CHOOSE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    String photoPath = FileUtil.getInstance().getRealPath(mSelected.get(0));
                    if (!TextUtils.isEmpty(photoPath)) {
                        presenter.uploadImage(photoPath);
                    }
                    break;
                // 获取编辑记号的图片地址
                case Const.ConstantCode.EDIT_PHOTO_REQUEST_CODE:
                    if (data.getStringExtra(EditPhotoActivity.PHOTO_DATA) != null) {
                        String path = data.getStringExtra(EditPhotoActivity.PHOTO_DATA);
                        if (!TextUtils.isEmpty(path)) {
                            presenter.uploadImage(path);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onUploadImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            if (null != mCompletionImageHandler) {
                mCompletionImageHandler.complete(imageUrl);
            }
        }
    }

    @Override
    public void onUploadRadio(String radioUrl, int recordCount) {
        String recordJson = GsonUtil.obj2String(new RecordTextBean(mStringBuilder.toString(), radioUrl, recordCount));
        if (null != mCompletionRecordHandler)
            mCompletionRecordHandler.complete(recordJson);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }

    private void initLocation(final Intent intent) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new LocationUtil(getApplicationContext(), new LocationUtil.GetLocationListener() {
                                @Override
                                public void onGetLocationListener(double latitude, double longitude, String cityName) {
                                    Log.d(TAG, "纬度为：" + latitude + "经度为" + longitude);
                                    getNfcIntent(intent, latitude, longitude);
                                }
                            });
                        } else {
                            ToastUtil.show("请确定获取了定位相关的权限！");
                            getNfcIntent(intent, presenter.getLatitude(), presenter.getLongitude());
                        }
                    }
                });
    }

    /**
     * 获取nfc的id
     *
     * @param intent
     */
    private void getNfcIntent(Intent intent, double latitude, double longitude) {
        // 当该Activity接收到NFC标签时，运行该方法
        // 调用工具方法，读取NFC数据
        if (NfcUtil.getInstance().isHasNfcIntent(intent)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] dataId = tag.getId();
            String str = NfcUtil.bytesToHexString(dataId);
            if (!TextUtils.isEmpty(str)) {
                Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                dWebView.reload();
                String rfidJson = GsonUtil.obj2String(new RfidLocationBean(str, latitude, longitude));
                UsJsFun(Const.ConstantGetJs.GET_RFID, new Object[]{rfidJson});
            }
        }
    }

    private void openDrawer() {
        if (null != drawerLayout) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void closeDrawer() {
        if (null != drawerLayout) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void hideFromTopAnimation() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLlParent, "alpha", 0f);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(mLlParent, "translationY", ScreenUtil.getScreenSize().y);
        AnimatorSet set = new AnimatorSet();
        set.play(alphaAnimator).with(transAnimator);
        set.setDuration(500);
        set.start();
    }

    private void showFromBottomAnimation() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLlParent, "alpha", 1f);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(mLlParent, "translationY", 0f);
        AnimatorSet set = new AnimatorSet();
        set.play(alphaAnimator).with(transAnimator);
        set.setDuration(500);
        set.start();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (dWebView.getVisibility() == View.VISIBLE) {
                if (dWebView.canGoBack()) {
                    dWebView.goBack(); //goBack()表示返回webView的上一页面
                    return true;
                }
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new LocationUtil(getApplicationContext(), new LocationUtil.GetLocationListener() {
                                @Override
                                public void onGetLocationListener(double latitude, double longitude, String cityName) {
                                    presenter.setLatLon(latitude, longitude);
                                    if (!TextUtils.isEmpty(cityName))
                                        presenter.getWeatherByCityName(cityName);
                                }
                            });
                        } else {
                            ToastUtil.show("请确定获取了定位相关的权限！");
                        }
                    }
                });
    }

    /**
     * 播放语音
     */
    private void playRecord(String recordPath) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recordPath);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer.stop();
        }

    }

    /**
     * 将数据循环读取编号
     *
     * @param i
     * @param data
     * @param childrenBeanList
     */
    private void getChildList(int i, List<Node> data, List<ChildrenBean> childrenBeanList) {
        if (childrenBeanList == null) return;
        int j = i * 10;
        for (int k = 0; k < childrenBeanList.size(); k++) {
            if (childrenBeanList.get(k) == null) continue;
            data.add(new Dept(j + k, i, childrenBeanList.get(k), childrenBeanList.get(k).getLabel()));
            getChildList(j + k, data, childrenBeanList.get(k).getChildren());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetWorkEvent netWorkEvent) {
        if (null != dWebView)
            dWebView.reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkReceiver);
        EventBusUtil.unregister(this);
        clearCookies(this);
    }

    @SuppressWarnings("deprecation")
    public void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
        dWebView.clearFormData();
        dWebView.clearCache(true);
        dWebView.clearHistory();
    }
}
