@echo off
rem ======================================================================
rem windows startup script
rem
rem author: 0neBean
rem date: 2018-12-2
rem ======================================================================

rem Open in a browser

rem startup jar
java -jar  -Denv=@profileActive@ ../boot/@project.build.finalName@.jar --spring.config.location=../config/

pause
