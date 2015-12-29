CREATE TABLE `dubbo_invoke` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `invoke_date` date NOT NULL,
  `service` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `consumer` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT '',
  `invoke_time` bigint(20) DEFAULT NULL,
  `success` int(11) DEFAULT NULL,
  `failure` int(11) DEFAULT NULL,
  `elapsed` int(11) DEFAULT NULL,
  `concurrent` int(11) DEFAULT NULL,
  `max_elapsed` int(11) DEFAULT NULL,
  `max_concurrent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `invoke_date` (`invoke_date`),
  KEY `index_service` (`service`) ,
  KEY `index_method` (`method`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

  
drop table if exists    `day_stats`;
CREATE TABLE `day_stats` (
  `id` bigint(20) primary key AUTO_INCREMENT,
  `invoke_date` date NOT NULL,
  `app_id` bigint(20) NOT NULL,  
  `service_id` bigint(20) NOT NULL,  
  `method_id` bigint(20) NOT NULL,  
  `success_provider` int(11) NOT NULL,
  `failure_provider` int(11) NOT NULL,
  `elapsed_provider` int(11) NOT NULL,
  `success_consumer` int(11) NOT NULL,
  `failure_consumer` int(11) NOT NULL,
  `elapsed_consumer` int(11) NOT NULL,

  UNIQUE KEY `invoke_date_method` (`invoke_date`, `method_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


#服务定义标准
drop table if exists    `application`;
CREATE TABLE `application` (
  `id` bigint(20) primary key AUTO_INCREMENT,  
  `name` varchar(255) NOT NULL,
  unique key name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists    `application_service`;
CREATE TABLE `application_service` (
  `id` bigint(20) primary key AUTO_INCREMENT,  
  `app_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  unique key service_name (app_id,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists    `application_service_method`;
CREATE TABLE `application_service_method` (
  `id` bigint(20) primary key AUTO_INCREMENT,  
  `service_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,  
  `maxtime_provider` int(11) NOT NULL DEFAULT 100,
  `maxtime_consumer` int(11) NOT NULL DEFAULT 150,
  unique key method_name (service_id,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
