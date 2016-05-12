var express = require('express');
var util = require('util');
var fs = require('fs');
var path = require('path');

var logger = require('../logger/logger');

var router = express.Router();

var init = false;

// list
router.get('/', function (req, res) {
  var dataFile = path.join(__dirname, '../files/datas.json');
  if (!fs.existsSync(dataFile)) {
    res
      .status(200)
      .type('json')
      .send([]);
  } else {
    fs.readFile(dataFile, "UTF-8", function (err, datas) {
      res
        .status(200)
        .type('json')
        .send(datas);
    });
  }
});

// get
router.get('/:id', function (req, res) {

  // 创建目录
  var filepath = path.join(__dirname, '../files');

  // 随机文件名
  var id = req.params.id;
  var filename = path.join(filepath, id);

  fs.readFile(filename, "UTF-8", function (err, data) {
    res
      .status(200)
      .type('json')
      .send(data);
  });
});

// update
router.put('/:id', function (req, res) {
  var data = req.body;

  // 创建目录
  var filepath = path.join(__dirname, '../files');
  createPath(filepath);

  // 随机文件名
  var id = req.params.id;
  var filename = path.join(filepath, id);

  fs.writeFile(path.join(__dirname, '../files', id), JSON.stringify(data), "UTF-8", function (data) {
    res
      .status(200)
      .type('json')
      .send({
        "code": 0,
        "msg": "success"
      });
  });
});

// add
router.post('/', function (req, res) {
  var data = req.body;

  // 创建目录
  var filepath = path.join(__dirname, '../files');
  createPath(filepath);

  // 随机文件名
  var id = Math.random().toString(36).substr(2);
  data.id = id;
  var filename = path.join(filepath, id);

  fs.writeFile(filename, JSON.stringify(data), "UTF-8", function () {

    // add to json
    var datas = getDatas();
    datas[datas.length] = data;
    saveDatas(datas);

    res.send({
      "code": 0,
      "msg": "success",
      "id": id
    });
  });
});

// delete
router.delete('/:id', function (req, res) {

  // 创建目录
  var filepath = path.join(__dirname, '../files');

  // 随机文件名
  var id = req.params.id;
  var filename = path.join(filepath, id);

  fs.unlink(filename, function (err) {

    // add to json
    var datas = getDatas();
    var index = -1;
    for (var i = 0; i < datas.lenght; i++) {
      if (datas[i].id == id) {
        index = i;
        break;
      }
    }
    datas.splice(index, 1);
    saveDatas(datas);

    res.send({
      "code": 0,
      "msg": "success"
    });
  });

});

// 获取数据
function getDatas() {
  var datas = [];
  if (!init) {
    init = true;
    var dataFile = path.join(__dirname, '../files/datas.json');
    if (!fs.existsSync(dataFile)) {
      logger.info('threre is file '.green + dataFile);
      datas = [];
    } else {
      var d = fs.readFileSync(dataFile, "UTF-8");
      datas = JSON.parse(d);
    }
  }
  return datas;
}

function saveDatas(datas) {
  var dataFile = path.join(__dirname, '../files/datas.json');
  fs.writeFile(dataFile, JSON.stringify(datas), "UTF-8");
}


function createPath(filepath) {
  if (!fs.existsSync(filepath)) {
    logger.info('create path '.green + filepath);
    return fs.mkdirSync(filepath);
  }
}



module.exports = router;