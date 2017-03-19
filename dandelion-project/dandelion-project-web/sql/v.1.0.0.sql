use dandelion;
CREATE TABLE `product_sort` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `name` varchar(45) NOT NULL,
  `note` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '管理员名称',
  `chineseName` varchar(25) DEFAULT NULL COMMENT '用户中文名称',
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT 'OPEN',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `orderNumber` varchar(255) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  `productAllCount` int(10) DEFAULT NULL,
  `amount` decimal(15,2) NOT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `postCode` varchar(45) DEFAULT NULL ,
  `address` varchar(225) DEFAULT NULL ,
  `note` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `order_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `orderNumber` varchar(255) NOT NULL,
  `productCount` int(10) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `productCode` varchar(45) NOT NULL,
  `productSortName` varchar(225) DEFAULT NULL,
  `unitAmount` decimal(15,2) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `styleName` varchar(45) DEFAULT NULL,
  `unitName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `productCode` varchar(45) NOT NULL,
  `productSort_id` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `product_img` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `productCode` varchar(25) NOT NULL,
  `url` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `product_style` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `modifyDate` datetime NOT NULL,
  `productCode` varchar(25) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `unitAmount` decimal(15,2) NOT NULL,
  `unitName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
