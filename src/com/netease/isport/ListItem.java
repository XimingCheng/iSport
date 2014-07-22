package com.netease.isport;

import android.graphics.Bitmap;

	public class ListItem {
		private String mAcTId;
		private String mUserName;
		private String mActivityTitile;
		private String mTime;
		private String mPeopleCount;
		private String mActivityContent;
		private Bitmap mUserImage;

		public ListItem(String userName, String activityTitile, String time, 
				String peopleCount, String activityContent, String id, Bitmap userImage) {
			super();
	        this.mUserName        = userName;
	        this.mActivityTitile  = activityTitile;
	        this.mTime            = time;
	        this.mPeopleCount     = peopleCount;
	        this.mActivityContent = activityContent;
	        this.mAcTId           = id;
	        this.setmUserImage(userImage);
	    }
		
		public String getmAcTId() {
			return mAcTId;
		}

		public void setmAcTId(String mAcTId) {
			this.mAcTId = mAcTId;
		}

		public String getmUserName() {
			return mUserName;
		}

		public void setmUserName(String mUserName) {
			this.mUserName = mUserName;
		}

		public String getmActivityTitile() {
			return mActivityTitile;
		}

		public void setmActivityTitile(String mActivityTitile) {
			this.mActivityTitile = mActivityTitile;
		}

		public String getmTime() {
			return mTime;
		}

		public void setmTime(String mTime) {
			this.mTime = mTime;
		}

		public String getmPeopleCount() {
			return mPeopleCount;
		}

		public void setmPeopleCount(String mPeopleCount) {
			this.mPeopleCount = mPeopleCount;
		}

		public String getmActivityContent() {
			return mActivityContent;
		}

		public void setmActivityContent(String mActivityContent) {
			this.mActivityContent = mActivityContent;
		}

		public Bitmap getmUserImage() {
			return mUserImage;
		}

		public void setmUserImage(Bitmap mUserImage) {
			this.mUserImage = mUserImage;
		}

}