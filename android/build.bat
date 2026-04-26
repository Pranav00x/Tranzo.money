@echo off
REM Build script for Tranzo Android App
REM Works around gradle wrapper cygpath issues on Windows

setlocal enabledelayedexpansion

REM Set Java home if not already set
if not defined JAVA_HOME (
    set "JAVA_HOME=C:\Program Files\Java\jdk-19"
)

REM Check if Java exists
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo ERROR: Java not found at %JAVA_HOME%
    echo Please install Java 19+ or set JAVA_HOME environment variable
    exit /b 1
)

REM Run gradle build
echo Building Tranzo Android App...
"%JAVA_HOME%\bin\java.exe" -Xmx2048m -cp "gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*

exit /b %ERRORLEVEL%
