CREATE TABLE `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(45) NOT NULL,
  `salary` int NOT NULL,
  `cmp_id` int NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_1_idx` (`cmp_id`),
  CONSTRAINT `fk_employee_1` FOREIGN KEY (`cmp_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) NOT NULL,
  `cmp_id` int NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_cmp_idx` (`cmp_id`),
  CONSTRAINT `fk_project_cmp` FOREIGN KEY (`cmp_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `project_emp_map` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `emp_id` int NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_idx` (`project_id`),
  KEY `fk_emp_idx` (`emp_id`),
  CONSTRAINT `fk_emp` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 CREATE TABLE `company_hierarchy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `p_cmp_id` int NOT NULL,
  `c_cmp_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_p_cmp_1` (`p_cmp_id`),
  KEY `fk_c_cmp_1` (`c_cmp_id`),
  CONSTRAINT `fk_c_cmp_1` FOREIGN KEY (`c_cmp_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_p_cmp_1` FOREIGN KEY (`p_cmp_id`) REFERENCES `company` (`id`)
);