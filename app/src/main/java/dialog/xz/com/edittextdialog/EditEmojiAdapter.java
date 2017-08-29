package dialog.xz.com.edittextdialog;

import android.support.v4.util.ArrayMap;
import android.widget.ImageView;

import com.xz.xadapter.XRvPureAdapter;
import com.xz.xadapter.xutil.XRvViewHolder;


/**
 * Created by xz on 2017/6/15 0015.
 * 表情适配器
 */

public class EditEmojiAdapter extends XRvPureAdapter {


    private ArrayMap<String,Integer> sparseArray = new ArrayMap();

    public ArrayMap getSparseArray() {
        return sparseArray;
    }

    public void setSparseArray(ArrayMap sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.dialog_item_edit_emoji;
    }

    @Override
    public void onBindViewHolder(XRvViewHolder holder, int position) {
        ImageView iv = holder.getView(R.id.d_i_e_e_iv);
        iv.setBackgroundResource(sparseArray.get(sparseArray.keyAt(position)));
    }

    @Override
    public int getItemCount() {
        return sparseArray.size();
    }
}
