package com.wxjz.module_aliyun.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveBitmapAsync extends AsyncTask<String, Void, Boolean> implements OnCancelListener {

	private Context mContext;

	/** 含有进度条的对话框 */
	private ProgressDialog mProgress;
	private boolean isVideo;

	public SaveBitmapAsync(Context context) {
		mContext = context;
	}

	public SaveBitmapAsync(Context context , boolean isVideo){
		mContext = context;
		this.isVideo = isVideo;
	}

	/*
	 * (non-Javadoc)加载之前的回调方法
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgress = new ProgressDialog(mContext);
		mProgress.setIndeterminate(true);
		mProgress.setCancelable(true);
		String tipMessage = "";
		if(isVideo){
			tipMessage = "正在保存视频...";
		}else {
			tipMessage = "正在保存图片...";
		}
		mProgress.setMessage(tipMessage);
		mProgress.setOnCancelListener(this);
		mProgress.show();
	}

	/*
	 * (non-Javadoc)后台执行的方法
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Boolean doInBackground(String... params) {
		File input = null;
		try {
			if(params[0].startsWith("drawable://")){
				params[0] = params[0].replace("drawable://" , "");
				int resId = Integer.valueOf(params[0]);
				input = Glide.with(mContext).load(resId).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
			}else {
				input = Glide.with(mContext).load(params[0]).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (input == null || !input.exists()) {
			return false;
		}
		File des = Environment.getExternalStorageDirectory();
		File filePath = new File(des, "/wismcp/download");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}

		String fileName = "";
		if(isVideo){
			fileName = String.valueOf(System.currentTimeMillis()) + ".mp4";
		}else {
			fileName = String.valueOf(System.currentTimeMillis()) + (FileUtils.isGifFile(input) ? ".gif" : ".jpg");
		}

		File output = new File(filePath, fileName);

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				fis = new FileInputStream(input);
				bis = new BufferedInputStream(fis);

				fos = new FileOutputStream(output);
				bos = new BufferedOutputStream(fos);

				byte[] b = new byte[1024 * 5];

				int len;

				while ((len = bis.read(b)) != -1) {

					bos.write(b, 0, len);
				}
				
				bos.flush();

				bis.close();
				bos.close();
				fos.close();
				fis.close();

                // 扫描多媒体文件广播
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(output));
                mContext.sendBroadcast(scanIntent);
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)加载之后的回调方法
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (mProgress.getWindow() != null) {
			mProgress.dismiss();
			mProgress = null;
		}
		if (result) {
			Toast.makeText(mContext, "保存成功" , Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext, "保存失败" , Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * (non-Javadoc)取消加载的回调方法
	 * 
	 * @see
	 * android.content.DialogInterface.OnCancelListener#onCancel(android.content
	 * .DialogInterface)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		cancel(true);
	}

	/*
	 * (non-Javadoc)加载取消之后的回调方法
	 * 
	 * @see android.os.AsyncTask#onCancelled()
	 */
	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

}