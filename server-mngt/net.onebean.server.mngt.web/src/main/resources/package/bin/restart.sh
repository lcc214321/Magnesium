#!/bin/bash

#======================================================================
# 项目重启shell脚本
# 先调用shutdown.sh停服
# 然后调用startup.sh启动服务
#
# author: 0neBean
# date: 2018-12-2
#======================================================================

# 项目名称
APPLICATION="@project.name@"

# 项目启动jar包名称
APPLICATION_JAR="@build.finalName@.jar"

# 进入bin目录
cd `dirname $0`
# 返回到上一级项目根目录路径
cd ..
# 打印项目根目录绝对路径
# `pwd` 执行系统命令并获得结果
BASE_PATH=`pwd`

# 停服
echo stop ${APPLICATION} Application...
sh ${BASE_PATH}/bin/shutdown.sh

# 启动服务
echo start ${APPLICATION} Application...
sh ${BASE_PATH}/bin/startup.sh