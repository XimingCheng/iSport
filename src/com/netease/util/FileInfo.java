package com.netease.util;

import java.util.ArrayList;


public class FileInfo {
	enum FileType {
		FILE, DIRECTORY;
	}

	private static ArrayList<String> PPT_SUFFIX = new ArrayList<String>();

	static {
		PPT_SUFFIX.add(".png");
		PPT_SUFFIX.add(".jpg");
	}
	private FileType fileType;
	private String fileName;
	private String filePath;

	public FileInfo(String filePath, String fileName, boolean isDirectory) {
		this.filePath = filePath;
		this.fileName = fileName;
		fileType = isDirectory ? FileType.DIRECTORY : FileType.FILE;
	}

	public boolean isImgineFile() {// 判断是否是图片文件，抑或是目录及其他
		if (fileName.lastIndexOf(".") < 0) // Don't have the suffix
			return false;
		String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
		if (!isDirectory() && PPT_SUFFIX.contains(fileSuffix))
			return true;
		else
			return false;
	}

	public boolean isDirectory() {
		if (fileType == FileType.DIRECTORY)
			return true;
		else
			return false;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileInfo [fileType=" + fileType + ", fileName=" + fileName
				+ ", filePath=" + filePath + "]";
	}

	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}
	// 注意这里重写了equals方法
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (this.getClass() == obj.getClass()) {
				FileInfo u = (FileInfo) obj;
				if (this.toString().equals(u.toString())) {
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}
	}
}
