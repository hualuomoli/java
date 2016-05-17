var express = require('express');

var logger = require('../logger/logger');

var router = express.Router();

var users = [{
  "username": "admin",
  "password": "admin",
  "nickname": "花落莫离"
}, {
  "username": "test",
  "password": "test",
  "nickname": "测试"
}];

// 登录
router.all('/login', function (req, res) {

  var username = req.param('username');
  var password = req.param('password');

  var user = getUser(username, password);



  if (!user) {
    res
      .status(200)
      .send({
        "code": 9,
        "msg": "username or password error"
      });
  } else {
    // 保存用户信息到sesson中
    req.session.user = user;

    res
      .status(200)
      .send({
        "code": 0,
        "msg": "login success"
      });
  }

});

// 登出
router.all('/logout', function (req, res) {
  req.session.destroy();

  res
    .status(200)
    .send({
      "code": 0,
      "msg": "logout success"
    });

});


// 获取登录用户
function getUser(username, password) {

  var user;
  for (var i = 0; i < users.length; i++) {
    var u = users[i];
    if (u.username == username && u.password == password) {
      user = u;
      break;
    }
  }
  return user;
}

module.exports = router;