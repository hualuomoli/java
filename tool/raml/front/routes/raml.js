var express = require('express');
var fs = require('fs');
var path = require('path');
var config = require('../config/config');


var logger = require('../logger/logger');
var ramlUtil = require('../util/raml-util')
var processUtil = require('../util/process-util')

var router = express.Router();

// 验证用户
router.all('*', function (req, res, next) {
  var user = req.session.user;

  if (!user) {
    res
      .status(401)
      .type('json')
      .send({
        "code": 0,
        "msg": "no permission"
      });
  } else {
    next();
  }

});


// list
router.get('/', function (req, res) {
  var username = getUserName(req);

  var ramls = getUserRamls(username);

  res
    .status(200)
    .type('json')
    .send(ramls);
});

// get
router.get('/:id', function (req, res) {

  var username = getUserName(req);
  var id = req.params.id;

  var raml = getUserRaml(username, id);

  res
    .status(200)
    .type('json')
    .send(raml);

});

// update
router.put('/:id', function (req, res) {

  var raml = req.body;
  var id = req.params.id;
  raml.id = id;
  var username = getUserName(req);

  updateUserRaml(username, raml);

  send(res);

});

// add
router.post('/', function (req, res) {

  var raml = req.body;
  var id = Math.random().toString(36).substr(2);
  raml.id = id;

  var username = getUserName(req);

  addUserRaml(username, raml);

  send(res);

});

// delete
router.delete('/:id', function (req, res) {

  var username = getUserName(req);
  var id = req.params.id;

  deleteUserRaml(username, id);

  send(res);

});


//////////////////////////////////////
//////////// function ///////////////
//////////////////////////////////////

// 获取用户名
function getUserName(req) {
  return req.session.user.username;
}

// 发送返回数据
function send(res) {
  res.send({
    "code": 0,
    "msg": "success"
  });
}

// 获取用户配置的RAML文件
function getUserRamls(username) {
  var filename = getJsonFileName(username);
  return read2Array(filename);
}

// 获取用户RAML
function getUserRaml(username, id) {
  var filename = getFileName(id);
  return read2Object(filename);
}

// 添加用户RAML
function addUserRaml(username, raml) {

  // add json
  var filename = getJsonFileName(username);
  var ramls = getUserRamls(username);

  ramls[ramls.length] = raml;
  writeData(filename, ramls);

  // add file
  var ramlFileName = getFileName(raml.id);
  writeData(ramlFileName, raml);

  // create raml file
  createRamlFile(raml);

}

// 修改raml
function updateUserRaml(username, raml) {

  // update json
  var filename = getJsonFileName(username);
  var ramls = getUserRamls(username);
  var index = getUserRamlIndex(ramls, raml.id);
  if (index >= 0) {
    ramls[index] = raml;
    writeData(filename, ramls);
  }

  // update file
  var ramlFileName = getFileName(raml.id);
  writeData(ramlFileName, raml);

  // create raml file
  createRamlFile(raml);

}

// 删除
function deleteUserRaml(username, id) {

  // delte json
  var filename = getJsonFileName(username);
  var ramls = getUserRamls(username);
  var index = getUserRamlIndex(ramls, id);
  if (index >= 0) {
    ramls.splice(index, 1);
    writeData(filename, ramls);
  }

  // delte file
  var ramlFileName = getFileName(id);
  remove(ramlFileName);

}

// 获取raml数据在集合中的位置
function getUserRamlIndex(datas, id) {
  var index = -1;
  for (var i = 0; i < datas.length; i++) {
    var data = datas[i];
    if (data.id == id) {
      index = i;
      break;
    }
  }
  return index;
}

// 获取用户RAML集合的json文件名
function getJsonFileName(username) {
  return getFileName(username) + '.json';
}

/////////////////////////////////////////////

// 读数据
function read2Array(filename) {
  var datas = readData(filename);
  if (!datas) {
    return [];
  } else {
    return JSON.parse(datas);
  }
}

// 读数据
function read2Object(filename) {
  var data = readData(filename);
  if (!data) {
    return {};
  } else {
    return JSON.parse(data);
  }
}

// 读取文件
function readData(filename) {
  if (!exists(filename)) {
    return;
  }
  return fs.readFileSync(filename, 'UTF-8');
}

// 写数据
function writeData(filename, objectData) {
  if (!objectData) {
    return;
  }
  fs.writeFileSync(filename, JSON.stringify(objectData), "UTF-8");
}

// 删除文件
function remove(filename) {
  fs.unlinkSync(filename);
}

// 生成Raml文件
function createRamlFile(raml) {

  // create raml file
  var ramlData = ramlUtil.getRaml(raml);
  var ramlFileName = getFileName(raml.id + '.raml');
  writeData(ramlFileName, ramlData)


  // create html file
  var scriptFileName = path.join(__dirname, '../script/create.' + config.scriptSuffix);
  var params = [raml.id];

  processUtil.executeScript(scriptFileName, params);

}

// 获取文件名
function getFileName(name) {
  var filepath = getFilePath();
  var filename = path.join(filepath, name);
  return filename;
}

// 获取文件路径
function getFilePath() {
  var filepath = path.join(__dirname, '../files');
  createPath(filepath);
  return filepath;
}

// 创建路径
function createPath(filepath) {
  if (!exists(filepath)) {
    logger.info('create path '.green + filepath);
    return fs.mkdirSync(filepath);
  }
}

// 文件/目录是否存在
function exists(file) {
  return fs.existsSync(file);
}

module.exports = router;