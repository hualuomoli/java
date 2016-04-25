@ECHO OFF

cd %~dp0
rem set bat script path
cd ..
set batPath=%cd%

set /p version=please input new version£º
echo.


cd %batPath%
call mvn versions:set -DnewVersion=%version%

pause