package com.netease.isport;

	public class ListItem {
		private String mUserName;
		private String mActivityTitile;
		private String mTime;
		private String mPeopleCount;
		private String mActivityContent;

		public ListItem(String userName, String activityTitile, String time, 
				String peopleCount, String activityContent) {
			super();
	        this.mUserName        = userName;
	        this.mActivityTitile  = activityTitile;
	        this.mTime            = time;
	        this.mPeopleCount     = peopleCount;
	        this.mActivityContent = activityContent;
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

}