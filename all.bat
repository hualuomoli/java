@ECHO OFF

cd %~dp0
rem set bat script path
set batPath=%cd%

SET CLASSPATH=%CLASSPATH%;%cd%\all.jar

set destPath=%cd%\web-all
set tempPomFileName=%cd%\pom.xml
set modules=%cd%\commons,%cd%\base,%cd%\web
set groupId=com.github.hualuomoli




rd /s/q %destPath%

java com.github.hualuomoli.all.AllUtil %destPath% %tempPomFileName% %modules% %groupId%

pause