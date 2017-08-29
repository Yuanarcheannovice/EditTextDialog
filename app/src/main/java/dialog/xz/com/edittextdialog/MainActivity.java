package dialog.xz.com.edittextdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import dialog.xz.com.edittextdialog.utils.EmojiUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mShowTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mShowTv = (TextView) findViewById(R.id.am_show);
        mShowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              new EditTextDialog(MainActivity.this).initView().setButtonClickListener(new EditTextDialog.ButtonClickListener() {
                    @Override
                    public void submitClick(String contentStr) {
                        //把文字里面的表情，转化显示
                        SpannableString emotionContent = EmojiUtils.getEmotionContent(MainActivity.this, mShowTv, contentStr);
                        mShowTv.setText(emotionContent);
                    }
                }).setEditTextDissmissListener(new EditTextDialog.EditTextDissmissListener() {
                  @Override
                  public void editTextContent(String contentStr) {
                      //dialog消失时的文字
                      //把文字里面的表情，转化显示
                      SpannableString emotionContent = EmojiUtils.getEmotionContent(MainActivity.this, mShowTv, contentStr);
                      mShowTv.setText(emotionContent);
                  }
              }).showDialog();
            }
        });
    }
}
