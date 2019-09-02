cd /d %~dp0
set ANT_HOME=e:\documents\!dev\HnH\Ant\
set JAVA_HOME=e:\documents\!dev\HnH\jdk1.8.0_112\
set PATH=%PATH%;%ANT_HOME%\bin

ant -f build.xml
pause