@echo off

rem /**
rem  * GitHub:https://github.com/hualuomoli/java
rem  *
rem  * Author:hualuomoli
rem  */
echo.
echo [Message] Install
echo.


cd %~dp0
cd ..
set basePath=%cd%

echo Installing........
call mvn install

pause