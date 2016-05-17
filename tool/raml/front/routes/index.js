var express = require('express');
var util = require('util');

module.exports = function (app) {

  setCross(app);

  // demo
  app.use('/', require('./login'));
  app.use('/user', require('./user'));
  app.use('/raml', require('./raml'));

}

function setCross(app) {

  //设置跨域访问
  app.all('*', function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "file://");
    res.header("Access-Control-Allow-Credentials", true);
    res.header("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
    res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
    // res.header("X-Powered-By", ' 3.2.1')
    next();
  });

}