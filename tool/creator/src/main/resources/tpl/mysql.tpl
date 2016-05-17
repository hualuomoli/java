CREATE TABLE `${name}` (
  <#list columnList as column>
	`${column.name}` ${column.type}<#if column.length??>(${column.length})</#if><#if column.notNull> NOT NULL</#if><#if column.defaultValue??> DEFAULT ${column.defaultValue}</#if>,
  </#list>
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${comment}';