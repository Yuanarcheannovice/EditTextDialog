/**
 *
 */
package dialog.xz.com.edittextdialog.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.ArrayMap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dialog.xz.com.edittextdialog.R;


/**
 * @author : xcoder_xz
 * @time : 2016年1月5日 上午11:30:39
 * @email : shinezejian@163.com
 * @description :文本中的emojb字符处理为表情图片
 */
public class EmojiUtils {

    public static SpannableString getEmotionContent(final Context context, final TextView tv, String source) {

        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getApplicationContext().getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            try {
                // 获取匹配到的具体字符
                String key = matcherEmotion.group();
                // 匹配字符串的开始位置
                int start = matcherEmotion.start();
                // 利用表情名字获取到对应的图片
                Integer imgRes = getImgByName(key);

                if (imgRes != null && imgRes != -1) {
                    // 压缩表情图片
                    int size = (int) tv.getTextSize() * 13 / 10;
                    Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                    Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                    ImageSpan span = new ImageSpan(context, scaleBitmap);
                    spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    bitmap.recycle();
//                    scaleBitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return spannableString;
    }

    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典表情

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


    static {
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        EMOTION_CLASSIC_MAP.put("[龇牙]", R.drawable.f001);
        EMOTION_CLASSIC_MAP.put("[调皮]", R.drawable.f002);
        EMOTION_CLASSIC_MAP.put("[无语]", R.drawable.f003);
        EMOTION_CLASSIC_MAP.put("[偷笑]", R.drawable.f004);
        EMOTION_CLASSIC_MAP.put("[拜拜]", R.drawable.f005);
        EMOTION_CLASSIC_MAP.put("[捶打]", R.drawable.f006);
        EMOTION_CLASSIC_MAP.put("[流汗]", R.drawable.f007);
        EMOTION_CLASSIC_MAP.put("[闭嘴]", R.drawable.f008);
        EMOTION_CLASSIC_MAP.put("[大哭]", R.drawable.f009);
        EMOTION_CLASSIC_MAP.put("[流泪]", R.drawable.f010);
        EMOTION_CLASSIC_MAP.put("[嘘嘘]", R.drawable.f011);
        EMOTION_CLASSIC_MAP.put("[耍帅]", R.drawable.f012);
        EMOTION_CLASSIC_MAP.put("[折磨]", R.drawable.f013);
        EMOTION_CLASSIC_MAP.put("[委屈]", R.drawable.f014);
        EMOTION_CLASSIC_MAP.put("[饥渴]", R.drawable.f015);
        EMOTION_CLASSIC_MAP.put("[可爱]", R.drawable.f016);
        EMOTION_CLASSIC_MAP.put("[色色]", R.drawable.f017);
        EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.f018);
        EMOTION_CLASSIC_MAP.put("[耍酷]", R.drawable.f019);
        EMOTION_CLASSIC_MAP.put("[呕吐]", R.drawable.f020);
        EMOTION_CLASSIC_MAP.put("[微笑]", R.drawable.f021);
        EMOTION_CLASSIC_MAP.put("[愤怒]", R.drawable.f022);
        EMOTION_CLASSIC_MAP.put("[惊恐]", R.drawable.f023);
        EMOTION_CLASSIC_MAP.put("[白眼]", R.drawable.f024);
        EMOTION_CLASSIC_MAP.put("[傲慢]", R.drawable.f025);
        EMOTION_CLASSIC_MAP.put("[难过]", R.drawable.f026);
        EMOTION_CLASSIC_MAP.put("[惊讶]", R.drawable.f027);
        EMOTION_CLASSIC_MAP.put("[疑问]", R.drawable.f028);
        EMOTION_CLASSIC_MAP.put("[打鼾]", R.drawable.f029);
        EMOTION_CLASSIC_MAP.put("[亲亲]", R.drawable.f030);
        EMOTION_CLASSIC_MAP.put("[憨笑]", R.drawable.f031);
        EMOTION_CLASSIC_MAP.put("[吓蒙]", R.drawable.f032);
        EMOTION_CLASSIC_MAP.put("[阴险]", R.drawable.f033);
        EMOTION_CLASSIC_MAP.put("[阴险]", R.drawable.f034);
        EMOTION_CLASSIC_MAP.put("[懵逼]", R.drawable.f035);
        EMOTION_CLASSIC_MAP.put("[左嘟]", R.drawable.f036);
        EMOTION_CLASSIC_MAP.put("[鄙视]", R.drawable.f037);
        EMOTION_CLASSIC_MAP.put("[晕死]", R.drawable.f038);
        EMOTION_CLASSIC_MAP.put("[好酷]", R.drawable.f039);
        EMOTION_CLASSIC_MAP.put("[可怜]", R.drawable.f040);
        EMOTION_CLASSIC_MAP.put("[咒骂]", R.drawable.f041);
        EMOTION_CLASSIC_MAP.put("[崩溃]", R.drawable.f042);
        EMOTION_CLASSIC_MAP.put("[抠鼻]", R.drawable.f043);
        EMOTION_CLASSIC_MAP.put("[鼓掌]", R.drawable.f044);
        EMOTION_CLASSIC_MAP.put("[出糗]", R.drawable.f045);
        EMOTION_CLASSIC_MAP.put("[哈切]", R.drawable.f046);
        EMOTION_CLASSIC_MAP.put("[瘪嘴]", R.drawable.f047);
        EMOTION_CLASSIC_MAP.put("[抱抱]", R.drawable.f048);
        EMOTION_CLASSIC_MAP.put("[爱爱]", R.drawable.f049);
        EMOTION_CLASSIC_MAP.put("[吐血]", R.drawable.f050);
        EMOTION_CLASSIC_MAP.put("[发抖]", R.drawable.f051);
        EMOTION_CLASSIC_MAP.put("[回头]", R.drawable.f052);
        EMOTION_CLASSIC_MAP.put("[猫咪]", R.drawable.f053);
        EMOTION_CLASSIC_MAP.put("[可以]", R.drawable.f054);
        EMOTION_CLASSIC_MAP.put("[夸张]", R.drawable.f055);
        EMOTION_CLASSIC_MAP.put("[藐视]", R.drawable.f056);
        EMOTION_CLASSIC_MAP.put("[握手]", R.drawable.f057);
        EMOTION_CLASSIC_MAP.put("[胜利]", R.drawable.f058);
        EMOTION_CLASSIC_MAP.put("[握拳]", R.drawable.f059);
        EMOTION_CLASSIC_MAP.put("[勾引]", R.drawable.f060);
        EMOTION_CLASSIC_MAP.put("[凋谢]", R.drawable.f061);
        EMOTION_CLASSIC_MAP.put("[花花]", R.drawable.f062);
        EMOTION_CLASSIC_MAP.put("[生日]", R.drawable.f063);
        EMOTION_CLASSIC_MAP.put("[猪头]", R.drawable.f064);
        EMOTION_CLASSIC_MAP.put("[菜刀]", R.drawable.f065);
        EMOTION_CLASSIC_MAP.put("[大便]", R.drawable.f066);
        EMOTION_CLASSIC_MAP.put("[示爱]", R.drawable.f067);
        EMOTION_CLASSIC_MAP.put("[心碎]", R.drawable.f068);
        EMOTION_CLASSIC_MAP.put("[爱心]", R.drawable.f069);
        EMOTION_CLASSIC_MAP.put("[晚安]", R.drawable.f070);
    }

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param imgName 名称
     * @return
     */
    public static int getImgByName(String imgName) {
        Integer integer = EMOTION_CLASSIC_MAP.get(imgName);
        return integer == null ? -1 : integer;
    }

}
