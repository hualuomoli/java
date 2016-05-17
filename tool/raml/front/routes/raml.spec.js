var should = require('should')
var fs = require('fs');
var path = require('path');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('Raml', function () {


  it('login', function (done) {
    request
      .post('/login')
      .send({
        username: 'admin',
        password: 'admin'
      })
      .end(done);
  })

  var raml;
  var description = 'this is update description';

  it('add', function (done) {
    request
      .post('/raml')
      .send({
        "headerParams": [],
        "uriParams": [],
        "queryParams": [],
        "responseParams": [],
        "method": "get",
        "url": "/simple",
        "responseMimeType": "application/json",
        "description": "没有任何参数和返回"
      })
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('success');
      })
      .end(done);
  });


  it('list', function (done) {
    request
      .get('/raml')
      .expect(200)
      .expect(function (res) {
        // res.body.should.be.not.Empty()
        raml = res.body[0];
      })
      .end(done);
  });

  it('get', function (done) {
    request
      .get('/raml/' + raml.id)
      .expect(200)
      .expect(function (res) {
        res.body.id.should.be.equal(raml.id);
      })
      .end(done);
  })


  it('update', function (done) {

    raml.description = description;

    request
      .put('/raml/' + raml.id)
      .send(raml)
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('success');
      })
      .end(done);
  })

  it('get data which has updated', function (done) {

    request
      .get('/raml/' + raml.id)
      .expect(200)
      .expect(function (res) {
        res.body.id.should.be.equal(raml.id);
        res.body.description.should.be.equal(description);
      })
      .end(done);
  })

  it('delete', function (done) {

    raml.description = description;

    request
      .delete('/raml/' + raml.id)
      .send(raml)
      .expect(200)
      .expect(function (res) {
        res.body.code.should.equal(0);
        res.body.msg.should.equal('success');
      })
      .end(done);
  })


  it('get data which has deleted', function (done) {

    request
      .get('/raml/' + raml.id)
      .expect(200)
      .expect(function (res) {
        res.body.should.not.have.property('id');
      })
      .end(done);
  })

});