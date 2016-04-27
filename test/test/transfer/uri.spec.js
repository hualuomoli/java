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

    it('只有query参数', function (done) {

      request
        .get('/api/uri/msg')
        .query('nickname=' + encodeURIComponent('花落莫离'))
        .query('registDate=' + encodeURIComponent('2001-02-03 04:05:06'))
        .query('age=20')
        .query('manager=false')
        // .expect(200)
        .expect(function (res) {
          // res.body.code.should.equal('0');
          console.log(res.headers);
          console.log(res);
        })
        .end(done);

    });



  });



});