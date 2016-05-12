var express = require('express');
var util = require('util');
var fs = require('fs');
var path = require('path');

var logger = require('../logger/logger');

var router = express.Router();

////////////////////////////
// response type
////////////////////////////

// text/plain
router.get('/res/txt', function (req, res) {
  res.status(200) // Status Code:
    .type('txt') // Content-Type
    // .type('text/plain') // Content-Type
    .send('ok') // response
});

// application/json
router.get('/res/json', function (req, res, next) {
  res.status(200)
    .type('json')
    .send({
      "msg": "ok"
    });
});

// application/xml
router.get('/res/xml', function (req, res, next) {
  res.type('xml');
  res.status(200);
  res.send('<root><username>admin</username><token>123456</token></root>');
});


////////////////////////////
// query parameter
////////////////////////////
router.get('/query', function (req, res) {
  logger.debug('query parameter[username] is ' + req.query.username);
  logger.debug('query parameter[token] is ' + req.query.token);
  logger.debug('query parameter[address] is ' + JSON.stringify(req.query.address));
  res.send(req.query);
});

////////////////////////////
// body parameter
////////////////////////////
router.post('/post', function (req, res) {
  logger.debug('post object parameter username is ' + req.body.username);
  logger.debug('post object parameter token is ' + req.body.token);
  // simple object
  logger.debug('post object parameter info is object ' + util.isObject(req.body.info));
  logger.debug('post object parameter info is ' + JSON.stringify(req.body.info));
  logger.debug('post object parameter info[nickname] is ' + req.body.info.nickname);
  logger.debug('post object parameter info[gender] is ' + req.body.info.gender);
  logger.debug('post object parameter info[age] is ' + req.body.info.age);
  // complex object
  logger.debug('post object parameter order is object ' + util.isObject(req.body.order));
  logger.debug('post object parameter order is ' + JSON.stringify(req.body.order));
  logger.debug('post object parameter order[serialNumber] is ' + req.body.order.serialNumber);
  logger.debug('post object parameter order[state] is ' + req.body.order.state);
  // address
  logger.debug('post object parameter order[address] is object ' + util.isObject(req.body.order.address));
  logger.debug('post object parameter order[address] is ' + JSON.stringify(req.body.order.address));
  logger.debug('post object parameter order[address][code] is ' + req.body.order.address.code);
  logger.debug('post object parameter order[address][name] is ' + req.body.order.address.name);
  // products
  logger.debug('post object parameter order[products] is array ' + util.isArray(req.body.order.products));
  logger.debug('post object parameter order[products] is ' + JSON.stringify(req.body.order.products));
  for (var i = 0; i < req.body.order.products.length; i++) {
    logger.debug('post object parameter order[products][' + i + '][id] is ' + req.body.order.products[i].id);
    logger.debug('post object parameter order[products][' + i + '][name] is ' + req.body.order.products[i].name);
    logger.debug('post object parameter order[products][' + i + '][price] is ' + req.body.order.products[i].price);
    logger.debug('post object parameter order[products][' + i + '][amount] is ' + req.body.order.products[i].amount);
  }
  // simple array
  logger.debug('post object parameter deliverTime is array ' + util.isArray(req.body.deliverTime));
  for (var j = 0; j < req.body.deliverTime.length; j++) {
    logger.debug('post object parameter deliverTime[' + j + '] is ' + JSON.stringify(req.body.deliverTime[i]));
  }
  // complex array
  logger.debug('post object parameter educations is array ' + util.isArray(req.body.educations));
  for (var k = 0; k < req.body.educations.length; k++) {
    logger.debug('post object parameter educations[' + k + '] is object ' + util.isObject(req.body.educations[i]));
    logger.debug('post object parameter educations[' + k + '][startTime] is ' + req.body.educations[i].startTime);
    logger.debug('post object parameter educations[' + k + '][endTime] is ' + req.body.educations[i].endTime);
    logger.debug('post object parameter educations[' + k + '][name] is ' + req.body.educations[i].name);
    logger.debug('post object parameter educations[' + k + '][degree] is ' + req.body.educations[i].degree);
  }

  res.send(req.body);
});

////////////////////////////
// uri parameter
////////////////////////////
router.post('/uri/:username/:token', function (req, res) {
  logger.debug('uri parameter[username] is ' + req.params.username);
  logger.debug('uri parameter[token] is ' + req.params.token);
  res.send(req.params);
});


////////////////////////////
// file upload
////////////////////////////
router.post('/upload', function (req, res) {

  logger.debug('upload file parameter[username] is ' + req.body.username);
  logger.debug('upload file parameter[token] is ' + req.body.token);
  logger.debug('upload file parameter[token] is ' + JSON.stringify(req.body.address));


  logger.debug('upload file photo is ' + req.files.photo.path);
  logger.debug('upload file background is ' + req.files.background.path);

  res.status(200)
    .type('json')
    .send({
      username: req.body.username,
      token: req.body.token,
      address: req.body.address,
      photo: req.files.photo.originalname,
      background: req.files.background.originalname
    });

});


////////////////////////////
// file download
////////////////////////////
router.get('/download/:id', function (req, res) {
  console.log('download id is ' + req.params.id);
  res.download(path.join(__dirname, 'index.js'), function (err) {
    if (err) {
      console.error(err);
    }
  });
});

module.exports = router;