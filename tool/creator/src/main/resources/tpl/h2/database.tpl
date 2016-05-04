<#list tableList as table>
DROP TABLE IF EXISTS ${table.name};
CREATE TABLE ${table.name} (
  <#list table.columnList as column>
	${column.name} ${column.type}<#if column.length?? && column.length?length gt 0>(${column.length})</#if><#if column.notNull> not null</#if><#if column.defaultValue?? && column.defaultValue?length gt 0> default ${column.defaultValue}</#if><#if column.comment?? && column.comment?length gt 0> COMMENT '${column.comment}'</#if>,
  </#list>
  PRIMARY KEY (id)
) COMMENT='${table.comment}';
</#list>