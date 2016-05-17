var express = require('express');

var logger = require('../logger/logger');

var router = express.Router();

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

// 个人信息
router.get('/', function (req, res) {
  res.send(req.session.user);
});

// 修改个人信息
router.put('/', function (req, res) {

  req.session.user.address = req.param('address');

  res.send({
    code: 0,
    msg: 'update user success'
  });
});


module.exports = router;