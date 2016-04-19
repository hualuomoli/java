@ECHO OFF

cd %~dp0
rem set bat script path
cd ..
set batPath=%cd%

SET CLASSPATH=%CLASSPATH%;%batPath%\script\assemble.jar

set destPath=%batPath%\web-all
set tempPomFileName=%batPath%\script\pom.txt
set modules=%batPath%\commons,%batPath%\base,%batPath%\api,%batPath%\plugins\activemq,%batPath%\plugins\redis,%batPath%\plugins\jpush,%batPath%\plugins\demosecurity,%batPath%\mvc,%batPath%\web
set groupId=com.github.hualuomoli




rd /s/q %destPath%

java com.github.hualuomoli.all.AllUtil %destPath% %tempPomFileName% %modules% %groupId%

pause