package com.njz.letsgoapp.editor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by LGQ
 * Time: 2018/7/31
 * Function:
 */

public class EditorActivity extends BaseActivity {

    RichEditor mEditor = null;
    TextView mPreview = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_editor;
    }

    @Override
    public void initView() {


        mEditor = (RichEditor) findViewById(R.id.editor);

        //初始化编辑高度
        mEditor.setEditorHeight(200);
        //初始化字体大小
        mEditor.setEditorFontSize(18);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);

        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置编辑框背景，可以是网络图片
        // mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        // mEditor.setBackgroundColor(Color.BLUE);
//        mEditor.setBackgroundResource(R.drawable.bg);
        //设置默认显示语句
        mEditor.setPlaceholder("Insert text here...");
        //设置编辑器是否可用
        mEditor.setInputEnabled(true);

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        /**
         * 加粗
         */
        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mEditor.focusEditor();
                mEditor.setBold();
            }
        });
        /**
         * 斜体
         */
        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mEditor.focusEditor();
                mEditor.setItalic();
            }
        });

        /**
         *下划线
         */
        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mEditor.focusEditor();
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });
//        /**
//         * 设置字体颜色
//         */
//        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEditor.setTextColor(Color.RED);
//            }
//        });
//
//        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEditor.setTextBackgroundColor(Color.YELLOW);
//            }
//        });

        /**
         * 插入图片
         */
        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alt在h5中的作用是图片的失效后的替换文本，同样也是鼠标放上去显示的文本，还可以告诉搜索引擎这个图片是什么，用来加关键字
//                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
//                        "dachshund" + "\" style=\"max-width:100%");
                getImage();
            }
        });

//        /**
//         * 获取并显示Html
//         */
//        findViewById(R.id.tv_showhtml).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
//                intent.putExtra("contextURL", mEditor.getHtml());
//                startActivity(intent);
//            }
//        });

    }

    private void getImage() {
        TakePhotoUtils.getGalleryImg(activity, CamaraRequestCode.CAMARA_GET_IMG);//相册选择图片
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消操作", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CamaraRequestCode.CAMARA_GET_IMG) {
            String filePath = insertImg(data.getData());
            mEditor.insertImage(filePath,
                    "dachshund" + "\" style=\"max-width:100%");
        }
    }

    public String insertImg(Uri bitmapUri){
        /**
         * 下面是对图片进行压缩处理---并且统一复制到sdcard的takephoto文件夹
         */
        String filePath = TakePhotoUtils.getRealFilePathByUri(context, bitmapUri);//图片的真实路径
        try {
            filePath = TakePhotoUtils.saveFile(context, BitmapFactory.decodeFile(filePath), filePath, 20);//压缩图片得到真实路径，imgQuality为图片的质量，按100制，默认图片质量20%（即压缩80%），现在主流手机使用20%最佳---平均下来150k左右
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * imageview对大图显示效果不好，这里对原图进行压缩
         */
        Bitmap images = BitmapFactory.decodeFile(filePath, TakePhotoUtils.getOptions(filePath, 4));//压缩图片的大小，按4倍来压缩

        return filePath;
    }

    @Override
    public void initData() {

    }
}
