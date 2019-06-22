@echo off
title Cucumber test run

set dev_local=%userprofile%\desktop\dev\
echo Current path: %dev_local%
set PATH=%PATH%;%dev_local%apps\apache-maven-3.5.2\bin
set PATH=%PATH%;%dev_local%apps\git\bin

set JAVA_HOME=%dev_local%java\jdk1.8.0_152

cd /d %~dp0
REM call mvn clean test install package verify
call mvn clean test -Dtest=eu.b24u.cucumber.CucumberRunner
REM timeout /T 3
pause