var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('../request');

describe('参数类型', function () {

  it('文件类型', function (done) {

    request
      .post('/api/type/typeFile')
      .field('username', 'admin')
      .attach('photo', path.join(__dirname, '../../favicon.ico'))
      .attach('pic', path.join(__dirname, '../mocha.opts'))
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('文件类型,不合法参数', function (done) {

    request
      .post('/api/type/typeFile')
      .field('username', 'admin')
      .attach('pic', path.join(__dirname, '../mocha.opts'))
      .expect(400)
      .end(done);

  });



});