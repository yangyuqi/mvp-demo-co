package com.wxjz.module_aliyun.NineGridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.wxjz.module_aliyun.NineGridView.gallery.HackyViewPager;
import com.wxjz.module_aliyun.NineGridView.gallery.PhotoView;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.utils.FileUtils;
import com.wxjz.module_aliyun.utils.SaveBitmapAsync;
import com.wxjz.module_aliyun.utils.StringUtils;
import com.wxjz.module_base.widgt.ActionSheetDialog;

import java.io.File;
import java.util.List;

/**
 * 图片浏览界面
 */
public class PhotoActivity extends Activity implements OnClickListener, OnPageChangeListener {

    private static final String TAG = "PhotoActivity";

    public static final String IMAGES = "IMAGES";
    public static final String DESC = "DESC";
    public static final String IS_SAVE = "is_save";
    public static final String TITLE_BAR_ISSHOW = "titleBarIsShow";
    public static final String IMAGE_POSITION = "IMAGE_POSITION";
    private static final String STATE_POSITION = "STATE_POSITION";
    private static final int PAGER_MARGIN_DP = 30;

    private HackyViewPager mViewPager;
    private int mPosition;
    private boolean mPaused;
    private TextView photo_title, photo_desc;
    private LinearLayout left_button_back;
    private ImageView right_menu_button;
    private RelativeLayout photoTitleBar;
    private String[] imageArr = null;
    private String[] strArr = null;
    // 组件图片选择预览
    public boolean barIsShow;
    private int limit;
    private LinearLayout photoSelectLinear;
    private TextView backTxt;
    private CheckBox photoSelect;
    //记录是否被选择
    private SparseBooleanArray selectionMap;
    private String selectionMapStr = "";
    private boolean isSave = true;
    private RequestOptions options;

    /**
     * 打开浏览图片组件
     *
     * @param activity    上下文
     * @param urlList     图片列表
     * @param urlDesList  图片描述列表
     * @param selectIndex 当前选中位置
     */
    public static void openBrowseImages(Activity activity, List<String> urlList, List<String> urlDesList, int selectIndex) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PhotoActivity.IMAGES, urlList.toArray(new String[urlList.size()]));
        if (urlDesList != null && urlDesList.size() != 0) {
            bundle.putStringArray(PhotoActivity.DESC, urlDesList.toArray(new String[urlDesList.size()]));
        }
        bundle.putInt(PhotoActivity.IMAGE_POSITION, selectIndex);
        bundle.putBoolean(PhotoActivity.TITLE_BAR_ISSHOW, false);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 打开浏览图片组件
     *
     * @param context     上下文
     * @param urlList     图片列表
     * @param urlDesList  图片描述列表
     * @param selectIndex 当前选中位置
     */
    public static void openPhotoAlbum(Context context, List<String> urlList, List<String> urlDesList, int selectIndex) {
        Intent intent = new Intent(context, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PhotoActivity.IMAGES, urlList.toArray(new String[urlList.size()]));
        if (urlDesList != null && urlDesList.size() != 0) {
            bundle.putStringArray(PhotoActivity.DESC, urlDesList.toArray(new String[urlDesList.size()]));
        }
        bundle.putInt(PhotoActivity.IMAGE_POSITION, selectIndex);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 打开浏览图片组件
     *
     * @param context     上下文
     * @param urlList     图片列表
     * @param urlDesList  图片描述列表
     * @param selectIndex 当前选中位置
     */
    public static void openPhotoAlbum(Context context, String[] urlList, List<String> urlDesList, int selectIndex) {
        Intent intent = new Intent(context, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PhotoActivity.IMAGES, urlList);
        if (urlDesList != null && urlDesList.size() != 0) {
            bundle.putStringArray(PhotoActivity.DESC, urlDesList.toArray(new String[urlDesList.size()]));
        }
        bundle.putInt(PhotoActivity.IMAGE_POSITION, selectIndex);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 打开浏览图片组件
     *
     * @param context     上下文
     * @param urlList     图片列表
     * @param urlDesList  图片描述列表
     * @param selectIndex 当前选中位置
     */
    public static void openPhotoAlbum(Context context, List<String> urlList, List<String> urlDesList, int selectIndex, boolean isSave) {
        Intent intent = new Intent(context, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PhotoActivity.IMAGES, urlList.toArray(new String[urlList.size()]));
        if (urlDesList != null && urlDesList.size() != 0) {
            bundle.putStringArray(PhotoActivity.DESC, urlDesList.toArray(new String[urlDesList.size()]));
        }
        bundle.putInt(PhotoActivity.IMAGE_POSITION, selectIndex);
        bundle.putBoolean(PhotoActivity.IS_SAVE, isSave);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(onReturnContentLayout());

        Bundle bundle = getIntent().getExtras();
        barIsShow = bundle.getBoolean(TITLE_BAR_ISSHOW, true);
        limit = bundle.getInt("limit");
        selectionMapStr = bundle.getString("selectMap");
        isSave = bundle.getBoolean(IS_SAVE, true);
        try {
            imageArr = bundle.getStringArray(IMAGES);
            strArr = bundle.getStringArray(DESC);
            List<String> list = bundle.getStringArrayList(IMAGES);
            if (list != null) {
                imageArr = new String[list.size()];
                list.toArray(imageArr);
            }
        } catch (Exception e) {
            //INGORE
        }
        int pagerPosition = bundle.getInt(IMAGE_POSITION, 0);

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        photoSelectLinear = (LinearLayout) findViewById(R.id.photoSelectLinear);
        backTxt = (TextView) findViewById(R.id.backTxt);
        photoSelect = (CheckBox) findViewById(R.id.photoSelect);

        photoTitleBar = (RelativeLayout) findViewById(R.id.photoTitleBar);
        left_button_back = (LinearLayout) findViewById(R.id.left_button_back);
        photo_title = (TextView) findViewById(R.id.photo_title);
        photo_desc = (TextView) findViewById(R.id.photo_desc);
        right_menu_button = (ImageView) findViewById(R.id.right_menu_button);

        mViewPager = (HackyViewPager) findViewById(R.id.viewPager);
        mViewPager.setPageMargin(PAGER_MARGIN_DP);
        mViewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
        mViewPager.setAdapter(new SamplePagerAdapter(getApplicationContext(), imageArr, photoTitleBar, photo_desc));
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(pagerPosition);

        photo_title.setVisibility(imageArr.length >= 2 ? View.VISIBLE : View.INVISIBLE);
        photo_title.setText((pagerPosition + 1) + "/" + imageArr.length);
        photo_desc.setVisibility(strArr == null || strArr.length == 0 || TextUtils.isEmpty(strArr[0]) ? View.INVISIBLE : View.VISIBLE);
        if (strArr != null && strArr.length != 0) {
            photo_desc.setText(strArr[pagerPosition]);
        }
        left_button_back.setOnClickListener(this);
        if (isSave) {
            right_menu_button.setVisibility(View.VISIBLE);
            right_menu_button.setOnClickListener(this);
        } else {
            right_menu_button.setVisibility(View.GONE);
        }

        if (!barIsShow) {
            photoTitleBar.setVisibility(View.GONE);
            photo_desc.setVisibility(View.GONE);
            photoSelectLinear.setVisibility(View.VISIBLE);
            selectionMap = new SparseBooleanArray();
            if (!StringUtils.isEmpty(selectionMapStr) && !StringUtils.isEmpty(selectionMapStr.substring(1, selectionMapStr.length() - 1))) {
                selectionMapStr = selectionMapStr.substring(1, selectionMapStr.length() - 1);
                String[] strings = selectionMapStr.split(",");
                for (int i = 0; i < strings.length; i++) {
                    String[] strings1 = strings[i].split("=", 2);
                    selectionMap.put(Integer.parseInt(strings1[0].trim()), Boolean.valueOf(strings1[1].trim()));
                }
            }
        }

        backTxt.setOnClickListener(this);
        photoSelect.setOnClickListener(this);

        options = new RequestOptions();
        options.centerInside().diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt(STATE_POSITION);
        mViewPager.setCurrentItem(mPosition);
    }

    protected int onReturnContentLayout() {
        return R.layout.viewpager;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPaused = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPaused = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent m) {
        if (mPaused) {
            return true;
        }
        return super.dispatchTouchEvent(m);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_button_back) {
            finish();
        } else if (v.getId() == R.id.right_menu_button) {
            showSaveDialog();
        } else if (v.getId() == R.id.photoSelect) {
            if (limit == 1) {
                selectionMap.clear();
            }
            if (selectionMap.get(mPosition)) {
                selectionMap.delete(mPosition);
            } else {
                if (selectionMap.size() < limit) {
                    selectionMap.put(mPosition, true);
                } else {
                    photoSelect.setChecked(false);
                    Toast.makeText(PhotoActivity.this, "最多选择" + limit + "张", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.backTxt) {
            Intent intent = new Intent();
            intent.putExtra("selectionMap", selectionMap.toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void showSaveDialog() {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(PhotoActivity.this).builder();
        actionSheetDialog.addSheetItem("保存到本地", null, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if (imageArr[mPosition] == null) {
                    return;
                }
                new SaveBitmapAsync(PhotoActivity.this).execute(imageArr[mPosition]);
            }
        });
        actionSheetDialog.show();
    }

    class SamplePagerAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater inflater;
        private String[] imageUrls;
        RelativeLayout titleBar;
        TextView descTv;

        public SamplePagerAdapter(Context context, String[] imageUrls, RelativeLayout titleBar, TextView descTv) {
            this.context = context;
            this.imageUrls = imageUrls;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.titleBar = titleBar;
            this.descTv = descTv;
        }

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public View instantiateItem(final ViewGroup container, final int position) {
            final View imageLayout = inflater.inflate(R.layout.viewimage, container, false);
            final PhotoView gifView = (PhotoView) imageLayout.findViewById(R.id.image);
            final SubsamplingScaleImageView photoView = (SubsamplingScaleImageView) imageLayout.findViewById(R.id.image_scale);
            photoView.setMaxScale(30);
            final RelativeLayout placeLayout = (RelativeLayout) imageLayout.findViewById(R.id.relative_place_holder);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
            gifView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showSaveDialog();
                    return true;
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showSaveDialog();
                    return true;
                }
            });
            String url = imageUrls[position];
            if (!TextUtils.isEmpty(url)) {
                if (url.startsWith("http") || imageUrls[position].startsWith("https")) {
                    GlideUrl glideUrl = null;
                    String cookieImg = CookieManager.getInstance().getCookie(url);
                    if (TextUtils.isEmpty(cookieImg)) {
                        glideUrl = new GlideUrl(url);
                    } else {
                        glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", cookieImg).build());
                    }

                    loadNetImg(glideUrl, gifView, photoView, spinner, placeLayout);
                } else {
                    //本地图片
                    spinner.setVisibility(View.GONE);
                    placeLayout.setVisibility(View.GONE);
                    loadLocalImg(imageUrls[position], gifView, photoView);
                }
            }

            if (barIsShow) {
                photoView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                gifView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }

            container.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private void loadNetImg(final GlideUrl url, final ImageView gifView
                , final SubsamplingScaleImageView photoView, final ProgressBar spinner, final RelativeLayout placeLayout) {
            Glide.with(context).asFile().load(url).into(new SimpleTarget<File>() {
                @Override
                public void onResourceReady(File resource, Transition<? super File> transition) {
                    spinner.setVisibility(View.GONE);
                    placeLayout.setVisibility(View.GONE);

                    if (FileUtils.isGifFile(resource)) {
                        gifView.setVisibility(View.VISIBLE);
                        Glide.with(context).load(url).apply(options).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                spinner.setVisibility(View.GONE);
                                placeLayout.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                spinner.setVisibility(View.GONE);
                                placeLayout.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(gifView);

                    } else {
                        if (TextUtils.equals(FileUtils.getImageSuffix(resource.getPath()), ".bmp")) {
                            spinner.setVisibility(View.GONE);
                            placeLayout.setVisibility(View.GONE);
                            gifView.setVisibility(View.VISIBLE);
                            Glide.with(context).load(url).apply(options).into(gifView);
                            return;
                        }
                        loadBigImg(resource, photoView);
                    }
                }
            });
        }

        private void loadLocalImg(String url, ImageView gifView, SubsamplingScaleImageView photoView) {
            if (url.startsWith("drawable://")) {
                gifView.setVisibility(View.VISIBLE);
                String res = url.replace("drawable://", "");
                int resId = Integer.valueOf(res);
                Glide.with(context).load(resId).apply(options).into(gifView);
                return;
            }
            File file = new File(url);
            if (file.exists()) {
                if (FileUtils.isGifFile(file)) {
                    gifView.setVisibility(View.VISIBLE);
                    Glide.with(context).load(url).apply(options).into(gifView);
                } else {
                    loadBigImg(file, photoView);
                }
            }
        }

        private void loadBigImg(File file, SubsamplingScaleImageView photoView) {
            photoView.setVisibility(View.VISIBLE);
            int sWidth = 0;
            int sHeight = 0;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap != null) {
                sWidth = bitmap.getWidth();
                sHeight = bitmap.getHeight();
            }
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            if (sHeight >= height && sHeight / sWidth >= 3) {
                photoView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                photoView.setImage(ImageSource.uri(Uri.fromFile(file)), new ImageViewState(2.0F, new PointF(0, 0), 0));
            } else {
                Log.d("LF123", "sWidth= " + sWidth + "----sHeight= " + sHeight + "\n" + "height =" + height);
                Log.d("LF123", "--------------------------");
                photoView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                photoView.setImage(ImageSource.uri(Uri.fromFile(file)));
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mPosition = position;
        photo_title.setText((position + 1) + "/" + imageArr.length);
        if (strArr != null && strArr.length != 0) {
            photo_desc.setText(strArr[position]);
        }
        if (selectionMap != null) {
            if (selectionMap.get(mPosition)) {
                photoSelect.setChecked(true);
            } else {
                photoSelect.setChecked(false);
            }
        }

    }

}