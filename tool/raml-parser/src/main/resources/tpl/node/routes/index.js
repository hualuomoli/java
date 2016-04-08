var express = require('express');
var util = require('util');

// routes
module.exports = function (app) {

  setCross(app);

  // user
  app.use('/demo', require('./demo'));

}

function setCross(app) {

  //设置跨域访问
  app.all('*', function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
    next();
  });

}