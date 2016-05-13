<#list tableList as table>
DROP TABLE IF EXISTS `${table.name}`;
CREATE TABLE `${table.name}` (
  <#list table.columnList as column>
	`${column.name}` ${column.type}<#if column.length?? && column.length?length gt 0>(${column.length})</#if><#if column.nullable?? && column.nullable == 'N'> NOT NULL</#if><#if column.defaultValue?? && column.defaultValue?length gt 0> DEFAULT ${column.defaultValue}</#if><#if column.comment?? && column.comment?length gt 0> COMMENT '${column.comment}'</#if>,
  </#list>
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.comment}';
</#list>