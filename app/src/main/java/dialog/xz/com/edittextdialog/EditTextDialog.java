package dialog.xz.com.edittextdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.xadapter.XRvPureAdapter;

import dialog.xz.com.edittextdialog.utils.EmojiUtils;


/**
 * Created by xz on 2017/6/14 0014.
 * 用户输入dialog
 */

public class EditTextDialog implements View.OnClickListener {
    private Context mContext;
    private AlertDialog mAlertDialog;
    private View mView;
    private EditText mEditText;
    private ImageView mFaceIco;
    private TextView mSubmit;
    private RecyclerView mRecyclerView;
    private EditEmojiAdapter mEmojiAdapter;
    private ArrayMap<String, Integer> mEmotionMap;

    public EditTextDialog(Context context) {
        this.mContext = context;
    }

    public EditTextDialog initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_edit, null);
        mEditText = (EditText) mView.findViewById(R.id.de_edit);
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        mFaceIco = (ImageView) mView.findViewById(R.id.de_ico);
        mFaceIco.setOnClickListener(this);
        mSubmit = (TextView) mView.findViewById(R.id.de_submit);
        mSubmit.setOnClickListener(this);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.de_rv);//图片快
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mSubmit.setTextColor(ContextCompat.getColor(mContext, R.color.app_txt_gray_light));
                    mSubmit.setBackgroundResource(R.drawable.edit_bt_no_shape);
                } else {
                    mSubmit.setTextColor(ContextCompat.getColor(mContext, R.color.app_white_color));
                    mSubmit.setBackgroundResource(R.drawable.edit_bt_yes_selector);
                }
            }
        };
        mEditText.addTextChangedListener(textWatcher);
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && mRecyclerView.getVisibility() == View.VISIBLE) {
                    hideEmoji();
                }
                return false;
            }
        });
        initEmojiData();
        return this;
    }

    /**
     * 设置文本框初始值
     *
     * @return
     */
    public EditTextDialog setEditTextValue(String valueStr) {
        if (TextUtils.isEmpty(valueStr)) {
            mEditText.setText("");
        } else {
            mEditText.setText(EmojiUtils.getEmotionContent(mContext,
                    mEditText, valueStr));
            mEditText.setSelection(valueStr.length());
        }
        return this;
    }

    /**
     * 设置提交按钮文字
     *
     * @param textStr
     * @return
     */
    public EditTextDialog setButtonText(String textStr) {
        mSubmit.setText(textStr);
        return this;
    }

    /**
     * 设置表情是否需要显示,true-显示
     * 默认显示
     *
     * @param display
     * @return
     */
    public EditTextDialog setEmojiDisplay(Boolean display) {
        if (display)
            mFaceIco.setVisibility(View.VISIBLE);
        else
            mFaceIco.setVisibility(View.GONE);
        return this;
    }

    private void initEmojiData() {
        mEmojiAdapter = new EditEmojiAdapter();//适配器
        mEmotionMap = EmojiUtils.EMOTION_CLASSIC_MAP;//表情map
        mEmojiAdapter.setOnItemClickListener(new XRvPureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = mEditText.getSelectionStart();
                String emojiName = (String) mEmojiAdapter.getSparseArray().keyAt(position);//得到key
                StringBuilder sb = new StringBuilder(mEditText.getText().toString());
                sb.insert(curPosition, emojiName);

                // 特殊文字处理,将表情等转换一下
                mEditText.setText(EmojiUtils.getEmotionContent(mContext,
                        mEditText, sb.toString()));
                // 将光标设置到新增完表情的右侧
                mEditText.setSelection(curPosition + emojiName.length());
            }
        });

        mRecyclerView.setAdapter(mEmojiAdapter);
        mEmojiAdapter.setSparseArray(mEmotionMap);
        mEmojiAdapter.notifyDataSetChanged();
    }

    public void showDialog() {
        // 创建对话
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.EdittextAlertDialog).create();
        // 设置返回键失
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (editTextDissmissListener != null) {
                    editTextDissmissListener.editTextContent(mEditText.getText().toString());
                }
                mContext = null;
                mEmojiAdapter = null;
                mAlertDialog = null;
                mEditText = null;
                mView = null;
            }
        });
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(mView);
        mAlertDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) mEditText.getContext()// 弹出软键盘
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        // 必须放到显示对话框下面，否则显示不出效果
        Window window = mAlertDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        // 加载布局组件
    }


    private void hideKeyBord() {
//        ((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
//                hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }

    private void showKeyBord() {
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mView,InputMethodManager.SHOW_FORCED);
        InputMethodManager im = ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
        im.showSoftInput(mEditText, 0);
//        InputMethodManager imm = (InputMethodManager) mEditText.getContext()// 弹出软键盘
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void hideEmoji() {
        mRecyclerView.setVisibility(View.GONE);
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyBord();
            }
        }, 500);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.de_ico:
                //表情切换
                if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    hideEmoji();
                } else {
                    hideKeyBord();
                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                }
                break;
            case R.id.de_submit:
                //评论提交
                if (buttonClickListener != null) {
                    buttonClickListener.submitClick(mEditText.getText().toString());
                }
                mAlertDialog.dismiss();
                break;
        }
    }

    private ButtonClickListener buttonClickListener;

    public EditTextDialog setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
        return this;
    }

    private EditTextDissmissListener editTextDissmissListener;

    public EditTextDialog setEditTextDissmissListener(EditTextDissmissListener editTextDissmissListener) {
        this.editTextDissmissListener = editTextDissmissListener;
        return this;
    }

    /**
     * 确认按钮监听
     */
    public interface ButtonClickListener {
        void submitClick(String contentStr);
    }

    /**
     * Dialog关闭时，会返回当前dittText的文字
     */
    public interface EditTextDissmissListener {
        void editTextContent(String contentStr);
    }

}
