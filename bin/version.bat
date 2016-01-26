@echo off

rem /**
rem  * GitHub:https://github.com/hualuomoli/java
rem  *
rem  * Author:hualuomoli
rem  */
echo.
echo [Message] Version
echo.

cd %~dp0
cd ..
set basePath=%cd%

set /p version=Please Input Your Version:
echo.

echo Update project to %version%

cd %basePath%
call mvn versions:set -DnewVersion=%version%

pause