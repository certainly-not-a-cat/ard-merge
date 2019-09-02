cd /d %~dp0
java -Xss1024k -Xms512m -Xmx1024m -jar build\hafen.jar -U https://game.havenandhearth.com/hres/ game.havenandhearth.com
pause