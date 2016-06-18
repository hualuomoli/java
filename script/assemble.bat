@ECHO OFF

cd %~dp0
rem set bat script path
set basepath=%cd%

cd ..
set batPath=%cd%

SET CLASSPATH=%CLASSPATH%;%basepath%\assemble.jar

rem 项目目录
set projectPath=%batPath%
rem 输出目录
set outputPath=%batPath%\web-all
rem 项目 groupId
set useGroupId=com.github.hualuomoli
rem 项目名称
set projectName=web-all
rem 是否强制删除输出目录
set force=true


rd /s/q %outputPath%

rem 默认
java com.github.hualuomoli.tool.Assemble %projectPath% %outputPath% %useGroupId%

rem 指定输出项目名称
rem java com.github.hualuomoli.tool.Assemble %projectPath% %outputPath% %useGroupId% %projectName%

rem 指定输出项目名称,强制删除输出目录
rem java com.github.hualuomoli.tool.Assemble %projectPath% %outputPath% %useGroupId% %projectName% %force%


cd %basepath%

pause