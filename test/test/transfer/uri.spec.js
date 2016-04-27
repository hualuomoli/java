var assert = require('assert');
var should = require('should');
var fs = require('fs');
var path = require('path');

var request = require('../request');

describe('URI参数转换', function () {

  describe('get', function () {

    it('没有参数', function (done) {

      request
        .get('/api/uri')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('0');
        })
        .end(done);

    });

    it('只有uri参数', function (done) {

      request
        .get('/api/uri/info/hualuomoli/51b45c0c38804df6bcee1d8a0422ae2e')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('0');
        })
        .end(done);

    });

    it('只有query参数(get方式需要对参数编码,不然中文乱码有空格参数导致错误)', function (done) {

      request
        .get('/api/uri/msg')
        .query('nickname=' + encodeURIComponent('花落莫离'))
        .query('registDate=' + encodeURIComponent('2001-02-03 04:05:06'))
        .query('age=20')
        .query('manager=false')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('0');
        })
        .end(done);

    });

    it('只有form参数', function (done) {

      request
        .post('/api/uri/msg')
        .send('nickname=花落莫离')
        .send('registDate=2001-02-03 04:05:06')
        .send('age=20')
        .send('manager=false')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('9');
          res.body.msg.should.equal('success');
        })
        .end(done);

    });

    it('uri和query参数', function (done) {

      request
        .get('/api/uri/list/hualuomoli/51b45c0c38804df6bcee1d8a0422ae2e/1/10')
        .query('nickname=' + encodeURIComponent('花落莫离'))
        .query('registDate=' + encodeURIComponent('2001-02-03 04:05:06'))
        .query('age=20')
        .query('manager=false')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('0');
        })
        .end(done);

    });

    it('uri和form参数', function (done) {

      request
        .post('/api/uri/list/hualuomoli/51b45c0c38804df6bcee1d8a0422ae2e/1/10')
        .send('nickname=花落莫离')
        .send('registDate=2001-02-03 04:05:06')
        .send('age=20')
        .send('manager=false')
        .expect(200)
        .expect(function (res) {
          res.body.code.should.equal('9');
          res.body.msg.should.equal('success');
        })
        .end(done);

    });

    it('参数不合法', function (done) {

      request
        .get('/api/uri/list/hualuomoli/51b45c0c38804df6bcee1d8a0422ae2e/1/1')
        .query('nickname=' + encodeURIComponent('花落莫离'))
        .query('registDate=' + encodeURIComponent('2001-02-03 04:05:06'))
        .query('age=5')
        .query('manager=false')
        .expect(400)
        .expect(function (res) {
          res.body.code.should.equal('9979');
        })
        .end(done);

    });



  });



});