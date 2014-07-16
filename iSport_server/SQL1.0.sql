CREATE TABLE `USER_TABLE` (
  `UserId` bigint(20) AUTO_INCREMENT NOT NULL COMMENT '用户id',
  `UserName` varchar(100) NOT NULL COMMENT '用户名', 
  `PassWord` varchar(200) NOT NULL COMMENT '密码', 
  `Location` varchar(100) COMMENT '当前位置', 
  `Score` double DEFAULT '0' COMMENT '用户信誉',
  `Completed_id` varchar(200) NOT NULL DEFAULT '0' COMMENT '已完成活动',
  `Uncompleted_id` varchar(200) NOT NULL DEFAULT '0' COMMENT '未完成活动',
  `Sex` varchar(2) NOT NULL DEFAULT '' COMMENT '性别',
  `UserImage` varchar(1024) COMMENT '头像',
  `Label` varchar(200) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `ACTIVITY_TABLE` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `Category` varchar(20) NOT NULL DEFAULT '0' COMMENT '活动分类',
  `BeginDateTime` date COMMENT '活动开始时间',
  `EndDateTime` date COMMENT '活动结束时间',
  `PeopleCount` bigint(255) COMMENT '参与人数',
  `PeopleJoinedIds` varchar(1024) COMMENT '已经参与进来的人的用户id',
  `Location` varchar(1024) COMMENT '活动地点',
  `SubmmitPeopleId` bigint(20) NOT NULL DEFAULT '0' COMMENT '发起人用户id',
  `Name` varchar(1024) COMMENT '活动名称',
  `Details` varchar(1024) COMMENT '活动详情',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动表';
