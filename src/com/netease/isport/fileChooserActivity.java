package com.netease.isport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.*;

public class fileChooserActivity extends Activity {
	private Button mBackView;
	private GridView mGridView;
	private Button select_all_button;
	private Button select_inverse_button;
	private Button ok_return_button;
	private Button cancel_return_button;
	private TextView mTvPath;

	private String mSdcardRootPath; // sdcard 根路径
	private String mLastFilePath; // 当前显示的路径

	private ArrayList<FileInfo> mFileLists;// 当前文件路径下的信息
	private fileChooserAdapter mAdapter;
    
	
	private static final String TAG="fileChosserActivity";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.filechooser_show);

		mSdcardRootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		mBackView = (Button) findViewById(R.id.imgBackFolder);
		mGridView = (GridView) findViewById(R.id.gvFileChooser);
		select_all_button = (Button) findViewById(R.id.select_all);
		select_inverse_button = (Button) findViewById(R.id.invert_select);
		ok_return_button = (Button) findViewById(R.id.ok_button);
		cancel_return_button = (Button) findViewById(R.id.btExit);
		mTvPath = (TextView) findViewById(R.id.tvPath);

		mBackView.setOnClickListener(mClickListener);
		select_all_button.setOnClickListener(mClickListener);
		select_inverse_button.setOnClickListener(mClickListener);
		ok_return_button.setOnClickListener(mClickListener);
		cancel_return_button.setOnClickListener(mClickListener);

		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mSdcardRootPath);
	}

	// 配置适配器
	private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mAdapter = new fileChooserAdapter(this, mFileLists);
		mGridView.setAdapter(mAdapter);
	}

	// 根据路径更新数据，并且通知Adatper数据改变
	private void updateFileItems(String filePath) {
		mLastFilePath = filePath;
		mTvPath.setText(mLastFilePath);

		if (mFileLists == null)
			mFileLists = new ArrayList<FileInfo>();
		if (!mFileLists.isEmpty())
			mFileLists.clear();

		File[] files = folderScan(filePath);
		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isHidden()) // 不显示隐藏文件
				continue;

			String fileAbsolutePath = files[i].getAbsolutePath();
			String fileName = files[i].getName();
			boolean isDirectory = false;
			if (files[i].isDirectory()) {
				isDirectory = true;
			}
			FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName,
					isDirectory);
			mFileLists.add(fileInfo);
		}
		// When first enter , the object of mAdatper don't initialized
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged(); // 重新刷新
	}

	// 获得当前路径的所有文件
	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	// 返回上一层目录的操作
	public void backProcess() {
		// 判断当前路径是不是sdcard路径 ， 如果不是，则返回到上一层。
		if (!mLastFilePath.equals(mSdcardRootPath)) {
			File thisFile = new File(mLastFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
		} else { // 是sdcard路径 ，直接结束
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	public void select_all_process() {
		File[] current_files = folderScan(mLastFilePath);
		for (int i = 0; i < current_files.length; i++) {
			if (current_files[i].isHidden()) // 不显示隐藏文件
				continue;

			String fileAbsolutePath = current_files[i].getAbsolutePath();
			String fileName = current_files[i].getName();
			boolean isDirectory = false;
			if (current_files[i].isDirectory()) {
				isDirectory = true;
			}
			FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName,
					isDirectory);
			if (fileInfo.isImgineFile()) {
				Log.v(fileChooserActivity.TAG,"添加了一个fileInfo");
				Select_file_set.selected_files.add(fileInfo);
			}
		}
		// When first enter , the object of mAdatper don't initialized
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged(); // 重新刷新界面
	}

	public void inverse_select_process() {
		File[] current_files = folderScan(mLastFilePath);
		for (int i = 0; i < current_files.length; i++) {
			if (current_files[i].isHidden()) // 不显示隐藏文件
				continue;

			String fileAbsolutePath = current_files[i].getAbsolutePath();
			String fileName = current_files[i].getName();
			boolean isDirectory = false;
			if (current_files[i].isDirectory()) {
				isDirectory = true;
			}
			FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName,
					isDirectory);
			if (fileInfo.isImgineFile()) {
				if (!Select_file_set.selected_files.contains(fileInfo))
					Select_file_set.selected_files.add(fileInfo);
				else {
					Select_file_set.selected_files.remove(fileInfo);
				}
			}
		}
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged(); // 重新刷新界面
	}

	private void toast(CharSequence hint) {
		Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}

	private View.OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBackFolder:
				backProcess();
				break;
			case R.id.select_all:
				select_all_process();
				break;
			case R.id.invert_select:
				inverse_select_process();
				break;
			case R.id.ok_button:
				//finish_test();// 测试输出选择的结果,已经将对此方法的调用放到了MainActivity中
				setResult(RESULT_OK);
				finish();// 结果已经保存在了Select_file_set中
				int len = Select_file_set.selected_files.size();
				FileInfo[] fileInfos=Select_file_set.selected_files.toArray(new FileInfo[len]);
				doFileUpload(fileInfos[0].getFilePath());
				break;
			case R.id.btExit:
				Select_file_set.selected_files.clear();// 清空结果
				setResult(RESULT_CANCELED);
				finish();
				break;
			default:
				break;
			}
		}
	};
	private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			Log.v(fileChooserActivity.TAG,"哎呀，我（mItemClickListener）被点了一下");
			FileInfo fileInfo = (FileInfo) (((fileChooserAdapter) adapterView
					.getAdapter()).getItem(position));
			if (fileInfo.isDirectory()) // 点击项为文件夹, 显示该文件夹下所有文件
				updateFileItems(fileInfo.getFilePath());
			else if (fileInfo.isImgineFile()) { // 是图像文件 ， 则将该路径通知给调用者
				ViewHolder vh = (ViewHolder) view.getTag();
				if (vh.is_selected.isChecked() == false) {//点中之前处于未选中状态
					Select_file_set.selected_files.add(fileInfo);

				} else {
					Select_file_set.selected_files.remove(fileInfo);
				}
				vh.is_selected.toggle();// 改变状态
			} else { // 其他文件.....
				toast(getText(R.string.open_file_error_format));
			}
		}
	};

	public void doFileUpload(String path){
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String lineEnd = "\r\n";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        String urlString = "http://efly.freeshell.ustc.edu.cn:54322/photos/upload";   // server ip
        try
        {
	         //------------------ CLIENT REQUEST
	         FileInputStream fileInputStream = new FileInputStream(new File(path) );
	         // open a URL connection to the Servlet
	         URL url = new URL(urlString);
	         // Open a HTTP connection to the URL
	         conn = (HttpURLConnection) url.openConnection();
	         // Allow Inputs
	         conn.setDoInput(true);
	         // Allow Outputs
	         conn.setDoOutput(true);
	         // Don't use a cached copy.
	         conn.setUseCaches(false);
	         // Use a post method.
	         conn.setRequestMethod("POST");
	         conn.setRequestProperty("Connection", "Keep-Alive");
	         conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+"    ");
	         dos = new DataOutputStream( conn.getOutputStream() );
	         dos.writeBytes(lineEnd);
	         dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + path + "\"" + lineEnd);
	         dos.writeBytes(lineEnd);
	
	         // create a buffer of maximum size
	         bytesAvailable = fileInputStream.available();
	         bufferSize = Math.min(bytesAvailable, maxBufferSize);
	         buffer = new byte[bufferSize];
	
	         // read file and write it into form...
	         bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	         while (bytesRead > 0)
	         {
		          dos.write(buffer, 0, bufferSize);
		          bytesAvailable = fileInputStream.available();
		          bufferSize = Math.min(bytesAvailable, maxBufferSize);
		          bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	         }
	
	         // send multipart form data necesssary after file data...
	         dos.writeBytes(lineEnd);
	         dos.writeBytes(lineEnd);
	
	         // close streams
	         Log.e("Debug","File is written");
	         fileInputStream.close();
	         dos.flush();
	         dos.close();
        }
        catch (MalformedURLException ex)
        {
             Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        catch (IOException ioe)
        {
             Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }

        //------------------ read the SERVER RESPONSE
        try {
              inStream = new DataInputStream ( conn.getInputStream() );
              String str;
              while (( str = inStream.readLine()) != null)
              {
                   Log.e("Debug","Server Response "+str);
              }
              inStream.close();
        }
        catch (IOException ioex){
             Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
	}
/*	private void finish_test() {
		LayoutInflater lay_out=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=lay_out.inflate(R.layout.activity_main, null);
		TextView show_choose_res = (TextView) view.findViewById(R.id.Test_chooses_files);
		for (FileInfo f_temp : Select_file_set.selected_files) {
			Log.v(fileChooserActivity.TAG,f_temp.getFileName());
			show_choose_res.append(f_temp.getFileName() + "\n");
		}
		
	}*/
}
