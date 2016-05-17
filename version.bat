@echo off

set /p version=please input new version£º
echo.

call mvn versions:set -DnewVersion=%version%

pause