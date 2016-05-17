// 进程工具类
var express = require('express');
var childProcess = require('child_process');


// 执行脚本
function executeScript(filename, params) {
  childProcess.execFile(filename, params);
}


var util = {};

util.executeScript = executeScript;


module.exports = util;