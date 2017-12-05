atguigu-parent 是所有模块的父 pom 工程, 起到对 jar 包和插件版本的统一管理.


电商行业的发展
	随着用户的增多, 并发量在不断怎加.
	
	并发量高, 技术要求高!
电商行业的特点
	技术新 (机器学习, 区块链)
	技术范围广 (Redis, Linux...)
	分布式 
	高并发, 集群, 负载均衡, 高可用
	海量数据 (PB级别的数据) hadoop 离线分析 spark 实时分析 habase(NoSQL数据库)
	业务复杂
	系统安全
	
分布式
	复杂的系统分开来做
集群
	同样的事情大家一起做
集群分类
	负载均衡集群
		负载设备
			硬件负载
				F5 netscaler A10
			软件负载
				Nginx LVS
			监控系统 zabbix ganglian
	高可用集群
		可用性很高的集群(主备)
	科学集群
云计算
	IaaS: Infrastructure-as-a-Service(基础设施即服务)
	Pass: Platform-as-a-Service (平台即服务)
	SaaS: Software-as-a-Service (软件即服务)
	
分布式架构:
    缺点: 方法的调用(RPC或接口的调用) 开发难度变高, 通过Dubbo简化难度
          团队分工更细了,专业性变高了

开发人员分配
	产品经理
	项目经理
	前端团队
	后端团队
	测试团队
	运维团队
	
聚合工程

	common
	manager
		pojo
		interface
		mapper
		service
	web
	
dubbo框架
	目标
		了解dubbo
		理解dubbo
		使用dubbo框架
	dubbo协议
		dubbo://
		rmi://
		hessian://
		http://
		webservice://
		thrift://
		memcached://
		redis://
	dubbo的演变
		application:应用
		service:服务
		cluster:集群
		
		ORM:所有在一起
			集群量:1~10 tomcat session共享
			
		MVC:三层架构 10~1000集群
			存在缺点:
				只能在系统中调用方法.
		RPC:远程过程调用模式 1000~10000
			存在缺点:
				代码重用性也不高,重复代码太多.
				方法调用混乱.
		SOA:面向服务框架 >10000
			这个就是dubbo
	dubbo架构
		节点角色说明
			provider
			consumer
			registry
			monitor
			container
			
	Dubbo使用方法
		
		
spring MVC
	注解驱动的作用

2017-12-4 课程安排 -----------------------------------
	FastDFS服务器搭建
	FastDFS的java客户端使用
	使用springmvc实现图片上传功能
	实现添加商品详细信息
	
什么是FastDFS?
	FastDFS是一个开源的轻量级分布式文件系统，它对文件进行管理，
	功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。
	特别适合以文件为载体的在线服务，如相册网站、视频网站等等。
	
其他文件系统
	ceph(pb级) tfs nfs

FastDFS 组成
	Client
	Tracker:负责就收请求,获取文件真实存储的服务器地址,返回给Client
	Storager:存储文件
	
	