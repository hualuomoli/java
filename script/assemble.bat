@ECHO OFF

cd %~dp0
rem set bat script path
cd ..
set batPath=%cd%

SET CLASSPATH=%CLASSPATH%;%batPath%\script\assemble.jar

set destPath=%batPath%\web-all
set tempPomFileName=%batPath%\script\pom.txt
set modules=%batPath%\commons,%batPath%\plugins,%batPath%\base,%batPath%\web
set groupId=com.github.hualuomoli




rd /s/q %destPath%

java com.github.hualuomoli.all.AllUtil %destPath% %tempPomFileName% %modules% %groupId%

pause