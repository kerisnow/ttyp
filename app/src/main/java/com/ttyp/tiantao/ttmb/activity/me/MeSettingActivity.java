package com.ttyp.tiantao.ttmb.activity.me;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.mydialog.MyDialog1;
import com.ttyp.tiantao.ttmb.activity.MyBaseAActivity;
import com.ttyp.tiantao.ttmb.activity.login.LoginActivity;
import com.ttyp.tiantao.ttmb.entity.UserEntity;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MeSettingActivity extends MyBaseAActivity implements EasyPermissions.PermissionCallbacks{
    ImageView backImage;
    TextView title;
    LinearLayout headimageLayout;
    ImageView headimage;
    LinearLayout picknameLayout;
    TextView pickname;
    LinearLayout phoneLayout;
    TextView phone;
    LinearLayout passwordLayout;
    LinearLayout aliLayout;
    LinearLayout searchLayout;
    Switch searchSwitch;
    LinearLayout checkUpdateLayout;
    LinearLayout logoutLayout;
    MyDialog1 myDialog1;
    MyDialog1 myDialog2;
    MyDialog1 myDialog3;
    private AlertDialog profilePictureDialog;
    Boolean aBoolean;
    private Context mContext;

    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;

    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        initView();
        initData();
        setListener();
    }



    private void initView(){
        backImage = findViewById(R.id.but_a_backimage);
        title = findViewById(R.id.title_text);
        headimageLayout = findViewById(R.id.setting_headimage_layout);
        headimage = findViewById(R.id.setting_headimage);
        picknameLayout = findViewById(R.id.setting_pickname_layout);
        pickname = findViewById(R.id.setting_pickname);
        phoneLayout = findViewById(R.id.setting_phone_layout);
        phone = findViewById(R.id.setting_phone);
        passwordLayout = findViewById(R.id.setting_password);
        aliLayout = findViewById(R.id.setting_ali_Layout);
        searchLayout = findViewById(R.id.setting_searchview_control);
        searchSwitch = findViewById(R.id.setting_search_control);
        checkUpdateLayout = findViewById(R.id.setting_check_update);
        logoutLayout = findViewById(R.id.setting_logout);
    }

    private void initData(){
        title.setText("设置");
        UserEntity userEntity = StaticValue.USER;
        if(userEntity==null){
            aBoolean = false;
            headimage.setImageResource(R.drawable.me_head_image);
            pickname.setText("请登录");
            phone.setText("请登录");
        }else {
            aBoolean = true;
            Glide.with(MeSettingActivity.this).load(userEntity.getHeadimage()).into(headimage);
            pickname.setText(userEntity.getPickName());
            phone.setText(userEntity.getPhone());
        }
    }

    private void setListener(){
        backImage.setOnClickListener(clickListener);
        headimageLayout.setOnClickListener(clickListener);
        picknameLayout.setOnClickListener(clickListener);
        phoneLayout.setOnClickListener(clickListener);
        passwordLayout.setOnClickListener(clickListener);
        aliLayout.setOnClickListener(clickListener);
        searchLayout.setOnClickListener(clickListener);
        checkUpdateLayout.setOnClickListener(clickListener);
        logoutLayout.setOnClickListener(clickListener);
        searchSwitch.setOnCheckedChangeListener(checkListener);
    }

    private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){

              }
        }
    };

    private View.OnClickListener clickListener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            Map<String,String> params = null;
            if (!aBoolean) {
                if(v.getId() == R.id.but_a_backimage){
                    exit();
                }else {
                    turnForResultToActivity(LoginActivity.class, null,FinalValue.SETTING_TO_SETTINGA);
                }
            } else {
                switch (v.getId()) {
                    case R.id.but_a_backimage:
                        exit();
                        break;
                    case R.id.setting_headimage_layout:
                        if (profilePictureDialog == null) {
                            @SuppressLint("InflateParams") View rootView = LayoutInflater.from(MeSettingActivity.this).inflate(R.layout.item_profile_picture, null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MeSettingActivity.this);
                            rootView.findViewById(R.id.tv_take_photo).setOnClickListener(clickListener);
                            rootView.findViewById(R.id.tv_choose_photo).setOnClickListener(clickListener);
                            builder.setView(rootView);
                            profilePictureDialog = builder.create();
                            profilePictureDialog.show();
                        } else {
                            profilePictureDialog.show();
                        }
                        break;
                    case R.id.tv_take_photo:
                        dismissProfilePictureDialog();
                        if (EasyPermissions.hasPermissions(mContext, PERMISSION_CAMERA, PERMISSION_WRITE)) {
                            takePhoto();
                        } else {
                            EasyPermissions.requestPermissions(MeSettingActivity.this, "need camera permission", FinalValue.REQUEST_PERMISSION_CAMERA, PERMISSION_CAMERA, PERMISSION_WRITE);
                        }
                        break;
                    case R.id.tv_choose_photo:
                        dismissProfilePictureDialog();
                        if (EasyPermissions.hasPermissions(mContext, PERMISSION_WRITE)) {
                            choosePhoto();
                        } else {
                            EasyPermissions.requestPermissions(MeSettingActivity.this, "need camera permission", FinalValue.REQUEST_PERMISSION_WRITE, PERMISSION_WRITE);
                        }
                        break;
                    case R.id.setting_pickname_layout:
                        myDialog1 = new MyDialog1(MeSettingActivity.this, 0);
                        myDialog1.setTitle("修改昵称").setTitle1("请输入8个字符以内的昵称").setEditHint("请输入昵称")
                                .setYesOnclickListener("确定", new MyDialog1.onYesOnclickListener() {
                                    @Override
                                    public void onYesOnclick(String editText) {
                                        if (editText == null || "".equals(editText)) {
                                            toastShow("请输入昵称");
                                            return;
                                        }
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Map<String, String> params = new HashMap<>();
                                                Message message = new Message();
                                                Bundle b = new Bundle();
                                                Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.SETTING_SETPICKNAME));
                                                if (result == null) {
                                                    message.what = 0;
                                                    b.putString("msg", "网络请求异常");
                                                    message.setData(b);
                                                    handler.sendMessage(message);
                                                    return;
                                                }
                                                String res = (String) result.get("res");
                                                String msg = (String) result.get("msg");
                                                if (res.equals("0")) {
                                                    message.what = 0;
                                                    b.putString("msg", msg);
                                                    message.setData(b);
                                                    handler.sendMessage(message);
                                                } else if (res.equals("1")) {
                                                    message.what = 1;
                                                    b.putString("key", "updatePickName");
                                                    b.putString("data", (String) result.get("data"));
                                                    message.setData(b);
                                                    handler.sendMessage(message);
                                                } else {
                                                    message.what = 0;
                                                    b.putString("msg", "网络请求异常");
                                                    message.setData(b);
                                                    handler.sendMessage(message);
                                                }
                                            }
                                        }).start();
                                    }
                                }).setNoOnclickListener("取消", new MyDialog1.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                myDialog1.dismiss();
                            }
                        });
                        break;
                    case R.id.setting_phone_layout:
                        params = new HashMap<>();
                        params.put("key",MeSettingAActivity.PHONELAYOUT_SHOW+"");
                        turnForResultToActivity(MeSettingAActivity.class,params,FinalValue.SETTING_TO_SETTINGA);
                        break;
                    case R.id.setting_password:
                        params = new HashMap<>();
                        params.put("key",MeSettingAActivity.PASSWORDLAYOUT_SHOW+"");
                        turnForResultToActivity(MeSettingAActivity.class,params,FinalValue.SETTING_TO_SETTINGA);
                        break;
                    case R.id.setting_ali_Layout:
                        params = new HashMap<>();
                        params.put("key",MeSettingAActivity.ALILAYOUT_SHOW+"");
                        turnForResultToActivity(MeSettingAActivity.class,params,FinalValue.SETTING_TO_SETTINGA);
                        break;
                    case R.id.setting_check_update:
                        checkUpdate();
                        break;
                    case R.id.setting_logout:
                        myDialog2 = new MyDialog1(MeSettingActivity.this, 0);
                        myDialog2.setTitle("是否确认退出当前账号")
                                .setYesOnclickListener("确定", new MyDialog1.onYesOnclickListener() {
                                    @Override
                                    public void onYesOnclick(String editText) {
                                        StaticValue.USER = null;
                                        myDialog2.dismiss();
                                        exit();
                                    }
                                }).setNoOnclickListener("取消", new MyDialog1.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                myDialog2.dismiss();
                            }
                        });
                        break;
                }
            }
        }
    };

    //拍照
    private void takePhoto() {
        //用于保存调用相机拍照后所生成的文件
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        captureFile = new File(rootFile, "temp.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本 如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, getPackageName(), captureFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureFile));
        }
        startActivityForResult(intent, FinalValue.REQUEST_PERMISSION_CAMERA);
    }

    private void dismissProfilePictureDialog() {
        if (profilePictureDialog != null && profilePictureDialog.isShowing()) {
            profilePictureDialog.dismiss();
        }
    }

    //从相册选择
    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, FinalValue.REQUEST_PERMISSION_WRITE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == FinalValue.REQUEST_PERMISSION_CAMERA) {
            takePhoto();
        } else if (requestCode == FinalValue.REQUEST_PERMISSION_WRITE) {
            choosePhoto();
        }
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        cropFile = new File(rootFile, "avatar.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, FinalValue.CROP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FinalValue.REQUEST_PERMISSION_CAMERA:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(mContext, getPackageName(), captureFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(captureFile));
                    }
                    break;
                case FinalValue.REQUEST_PERMISSION_WRITE:
                    cropPhoto(data.getData());
                    break;
                case FinalValue.CROP_REQUEST_CODE:
                    saveImage(cropFile.getAbsolutePath());
                    uploadPhoto(cropFile);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param path
     * @return
     */
    public String saveImage(String path) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        try {
            FileOutputStream fos = new FileOutputStream(cropFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return cropFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void uploadPhoto(final File picture){
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,Object> params = new HashMap<>();
                params.put("uid",StaticValue.USER.getUid());
                params.put("image",picture);
                Message message = new Message();
                Bundle bundle = new Bundle();
                Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().UploadFilePost(params,URLValue.SETTING_UPLOADPHOTO));
                if(result==null){
                    message.what = 0;
                    bundle.putString("msg","网络请求异常");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    return;
                }
                int res = (int)result.get("res");
                String msg = (String)result.get("msg");
                if(res==0){
                    message.what = 0;
                    bundle.putString("msg",msg);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }else if(res==1){
                    message.what = 1;
                    bundle.putString("key","uploadfile");
                    bundle.putString("filepath",(String)result.get("url"));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }else {
                    message.what = 0;
                    bundle.putString("msg","网络请求异常");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        });
        th1.start();
    }

    /**
     * 检测更新
     */
    private void checkUpdate(){
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = new HashMap<>();
                Message message = new Message();
                Bundle b = new Bundle();
                Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.SETTING_CHECKUPDATE));
                if (result == null) {
                    message.what = 0;
                    b.putString("msg", "网络请求异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                String res = (String) result.get("res");
                String msg = (String) result.get("msg");
                if (res.equals("0")) {
                    message.what = 0;
                    b.putString("msg", msg);
                    message.setData(b);
                    handler.sendMessage(message);
                } else if (res.equals("1")) {
                    message.what = 1;
                    b.putString("key", "checkUpdate");
                    b.putString("data", (String) result.get("data"));
                    message.setData(b);
                    handler.sendMessage(message);
                } else {
                    message.what = 0;
                    b.putString("msg", "网络请求异常");
                    message.setData(b);
                    handler.sendMessage(message);
                }
            }
        });
        th1.start();
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        switch (key){
            case "updatePickName":
                String editText = myDialog1.getEditText();
                myDialog1.dismiss();
                toastShow("修改成功");
                pickname.setText(editText);
                StaticValue.USER.setPickName(editText);
                break;
            case "checkUpdate":
                String data = bundle.getString("data");
                if(true){
                    myDialog3 = new MyDialog1(MeSettingActivity.this, 0);
                    myDialog3.setTitle("检测到新版本是否立即更新")
                            .setYesOnclickListener("确定", new MyDialog1.onYesOnclickListener() {
                                @Override
                                public void onYesOnclick(String editText) {
                                    //更新
                                    intit_getClick();
                                    myDialog2.dismiss();
                                }
                            }).setNoOnclickListener("取消", new MyDialog1.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            myDialog2.dismiss();
                        }
                    });
                }else {
                    toastLongShow("已是最新版本");
                }
                break;
            case "uploadfile":
                String filepath = bundle.getString("filepath");
                StaticValue.USER.setHeadimage(filepath);
                Glide.with(MeSettingActivity.this).load(StaticValue.USER.getHeadimage()).into(headimage);
                toastShow("保存成功");
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
}
