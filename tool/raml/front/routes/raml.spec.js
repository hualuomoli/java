var should = require('should')
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('Raml的基础维护', function () {

  var id = '';
  var raml = {};



  it('添加数据', function (done) {

    var url = Math.random().toString(36).substr(2);

    request
      .post('/raml')
      .set('content-type', 'application/json')
      .send({
        url: url,
        type: 'get',
        description: '获取用户信息'
      })
      .expect(200)
      .expect(function (res) {
        should.exist(res.body.id);
        id = res.body.id;
      })
      .end(done);

  });

  it('获取数据', function (done) {

    request
      .get('/raml/' + id)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
        raml = res.body;
      })
      .end(done);

  });

  it('修改数据', function (done) {

    raml.description = "修改描述信息";

    request
      .put('/raml/' + id)
      .set('content-type', 'application/json')
      .send(raml)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
      })
      .end(done);

  });

  it('查询列表', function (done) {
    request
      .get('/raml')
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
        var datas = res.body;
        datas.length.should.not.equal(0);
      })
      .end(done);
  });

  it('删除数据', function (done) {

    request
      .delete('/raml/' + id)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.be.empty();
      })
      .end(done);

  });



});