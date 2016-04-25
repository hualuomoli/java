@ECHO OFF

cd %~dp0
rem set bat script path
cd ..
set batPath=%cd%


cd %batPath%
call mvn clean install -Dmaven.test.skip=true

pause