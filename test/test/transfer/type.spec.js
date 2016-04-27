var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('../request');

describe('参数类型', function () {

  it('字符串类型', function (done) {

    request
      .post('/api/type/typeString')
      .send('username=hualuomoli')
      .send('time=10:26:43')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('字符串类型,不合法参数', function (done) {

    request
      .post('/api/type/typeString')
      .send('username=hu')
      .send('time=1026')
      .expect(400)
      .expect(function (res) {
        res.body.code.should.equal('9979');
      })
      .end(done);

  });

  it('整形类型', function (done) {

    request
      .post('/api/type/typeInteger')
      .send('age=20')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('整形类型,不合法参数', function (done) {

    request
      .post('/api/type/typeInteger')
      .send('age=10')
      .expect(400)
      .expect(function (res) {
        res.body.code.should.equal('9979');
      })
      .end(done);

  });

  it('日期类型', function (done) {

    request
      .post('/api/type/typeDate')
      .send('date=2015-08-26 05:16:34')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('日期类型,不合法参数', function (done) {

    request
      .post('/api/type/typeDate')
      .send('date=20150826051634')
      .expect(400)
      .end(done);

  });

  it('布尔类型', function (done) {

    request
      .post('/api/type/typeBoolean')
      .send('manager=false')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('布尔类型,0|1为true|false', function (done) {

    request
      .post('/api/type/typeBoolean')
      .send('manager=1')
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal('0');
      })
      .end(done);

  });

  it('布尔类型,不合法参数', function (done) {

    request
      .post('/api/type/typeBoolean')
      .send('manager=9')
      .expect(400)
      .end(done);

  });


});