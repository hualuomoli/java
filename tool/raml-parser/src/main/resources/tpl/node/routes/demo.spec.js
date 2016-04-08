var assert = require('assert');
var fs = require('fs');
var path = require('path');

var logger = require('../logger/logger');

var request = require('supertest').agent(require('../app').listen());
// var request = require('supertest').agent('http://localhost:3000');

describe('test server response parameter and body', function () {

  // response type
  describe('test server response type', function () {

    it('should response text/plain ', function (done) {
      request
        .get('/demo/res/txt')
        .expect(200)
        .expect('Content-Type', 'text/plain; charset=utf-8')
        .expect(function (res) {
          assert.equal(res.text, 'ok');
        })
        .expect("ok", done);
    })

    it('should response application/json ', function (done) {
      request
        .get('/demo/res/json')
        .expect(200)
        .expect('Content-Type', 'application/json; charset=utf-8')
        .expect(function (res) {
          assert.equal(res.body.msg, 'ok');
        })
        .end(done);
    })

    it('should response application/xml ', function (done) {
      request
        .get('/demo/res/xml')
        .expect(200)
        .expect('Content-Type', 'application/xml; charset=utf-8')
        .expect('<root><username>admin</username><token>123456</token></root>', done);
    })

  });


  // send parameter by get method
  describe('test get method', function () {

    var queryObject = {
      "username": "admin",
      "token": "1234567890",
      "address": {
        "code": "370203",
        "home": "山东省青岛市市北区"
      }
    }

    it('shold send parameter by json', function (done) {
      request
        .get('/demo/query')
        .set('content-type', 'application/json')
        .query(queryObject)
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(queryObject));
        })
        .end(done);
    });

    it('shold send encoded parameter by urlencoded', function (done) {
      request
        .get('/demo/query')
        .set('content-type', 'application/x-www-form-urlencoded')
        .query('username=admin&token=1234567890&address[code]=370203&address[home]=' + encodeURIComponent('山东省青岛市市北区'))
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(queryObject));
        })
        .end(done);
    });

    it('shold send encoded parameter by parameter array', function (done) {
      request
        .get('/demo/query')
        .set('content-type', 'application/x-www-form-urlencoded')
        .query('username=admin')
        .query('token=1234567890')
        .query('address[code]=370203')
        .query('&address[home]=' + encodeURIComponent('山东省青岛市市北区'))
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(queryObject));
        })
        .end(done);
    });

  });

  // send parameter by post method
  describe('test post method', function () {

    // data
    var sendObject = {
      "username": "admin",
      "token": "1234567890",
      // simple object
      "info": {
        "nickname": "花落莫离",
        "gender": "M",
        "age": "28"
      },
      // complex objet
      "order": {
        "serialNumber": "20160407090102123",
        "state": "0",
        "address": {
          "code": "370203",
          "name": "山东省青岛市市北区"
        },
        "products": [{
          "id": "1234",
          "name": "iphone SE",
          "price": "3456.03",
          "amount": "1"
        }, {
          "id": "2345",
          "name": "ipad pro",
          "price": "6666.66",
          "amount": "2"
        }]
      },
      // simple array
      "deliverTime": ["1", "2", "3"],
      // complex array
      "educations": [{
        "startTime": "2004",
        "endTime": "2007",
        "name": "高中",
        "degree": "中学"
      }, {
        "startTime": "2007",
        "endTime": "2011",
        "name": "大学",
        "degree": "本科"
      }, {
        "startTime": "2011",
        "endTime": "2014",
        "name": "研究生",
        "degree": "硕士"
      }]
    };
    // $.param(sendObject);
    var sendStr = 'username=admin&token=1234567890&info[nickname]=花落莫离&info[gender]=M&info[age]=28&order[serialNumber]=20160407090102123&order[state]=0&order[address][code]=370203&order[address][name]=山东省青岛市市北区&order[products][0][id]=1234&order[products][0][name]=iphone+SE&order[products][0][price]=3456.03&order[products][0][amount]=1&order[products][1][id]=2345&order[products][1][name]=ipad+pro&order[products][1][price]=6666.66&order[products][1][amount]=2&deliverTime[]=1&deliverTime[]=2&deliverTime[]=3&educations[0][startTime]=2004&educations[0][endTime]=2007&educations[0][name]=高中&educations[0][degree]=中学&educations[1][startTime]=2007&educations[1][endTime]=2011&educations[1][name]=大学&educations[1][degree]=本科&educations[2][startTime]=2011&educations[2][endTime]=2014&educations[2][name]=研究生&educations[2][degree]=硕士';
    var sendEncodedStr = 'username=admin&token=1234567890&info%5Bnickname%5D=%E8%8A%B1%E8%90%BD%E8%8E%AB%E7%A6%BB&info%5Bgender%5D=M&info%5Bage%5D=28&order%5BserialNumber%5D=20160407090102123&order%5Bstate%5D=0&order%5Baddress%5D%5Bcode%5D=370203&order%5Baddress%5D%5Bname%5D=%E5%B1%B1%E4%B8%9C%E7%9C%81%E9%9D%92%E5%B2%9B%E5%B8%82%E5%B8%82%E5%8C%97%E5%8C%BA&order%5Bproducts%5D%5B0%5D%5Bid%5D=1234&order%5Bproducts%5D%5B0%5D%5Bname%5D=iphone+SE&order%5Bproducts%5D%5B0%5D%5Bprice%5D=3456.03&order%5Bproducts%5D%5B0%5D%5Bamount%5D=1&order%5Bproducts%5D%5B1%5D%5Bid%5D=2345&order%5Bproducts%5D%5B1%5D%5Bname%5D=ipad+pro&order%5Bproducts%5D%5B1%5D%5Bprice%5D=6666.66&order%5Bproducts%5D%5B1%5D%5Bamount%5D=2&deliverTime%5B%5D=1&deliverTime%5B%5D=2&deliverTime%5B%5D=3&educations%5B0%5D%5BstartTime%5D=2004&educations%5B0%5D%5BendTime%5D=2007&educations%5B0%5D%5Bname%5D=%E9%AB%98%E4%B8%AD&educations%5B0%5D%5Bdegree%5D=%E4%B8%AD%E5%AD%A6&educations%5B1%5D%5BstartTime%5D=2007&educations%5B1%5D%5BendTime%5D=2011&educations%5B1%5D%5Bname%5D=%E5%A4%A7%E5%AD%A6&educations%5B1%5D%5Bdegree%5D=%E6%9C%AC%E7%A7%91&educations%5B2%5D%5BstartTime%5D=2011&educations%5B2%5D%5BendTime%5D=2014&educations%5B2%5D%5Bname%5D=%E7%A0%94%E7%A9%B6%E7%94%9F&educations%5B2%5D%5Bdegree%5D=%E7%A1%95%E5%A3%AB';


    it('shold send parameter by json', function (done) {
      request
        .post('/demo/post')
        .set('content-type', 'application/json')
        .send(sendObject)
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(sendObject));
        })
        .end(done);
    });

    it('shold send parameter by urlencoded', function (done) {
      request
        .post('/demo/post')
        .set('content-type', 'application/x-www-form-urlencoded')
        .send(sendStr)
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(sendObject));
        })
        .end(done);
    });

    it('shold send encoded parameter by urlencoded', function (done) {
      request
        .post('/demo/post')
        .set('content-type', 'application/x-www-form-urlencoded')
        .send(sendEncodedStr)
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(sendObject));
        })
        .end(done);
    });

    it('shold send parameter by urlencoded array', function (done) {
      request
        .post('/demo/post')
        .set('content-type', 'application/x-www-form-urlencoded')
        .send('username=admin')
        .send('token=1234567890')
        // simple object
        .send('info[nickname]=花落莫离')
        .send('info[gender]=M')
        .send('info[age]=28')
        // complex object
        .send('order[serialNumber]=20160407090102123')
        .send('order[state]=0')
        .send('order[address][code]=370203')
        .send('order[address][name]=山东省青岛市市北区')
        .send('order[products][0][id]=1234')
        .send('order[products][0][name]=iphone SE')
        .send('order[products][0][price]=3456.03')
        .send('order[products][0][amount]=1')
        .send('order[products][1][id]=2345')
        .send('order[products][1][name]=ipad pro')
        .send('order[products][1][price]=6666.66')
        .send('order[products][1][amount]=2')
        // simple array
        .send('deliverTime[]=1')
        .send('deliverTime[]=2')
        .send('deliverTime[]=3')
        // complex array
        .send('educations[0][startTime]=2004')
        .send('educations[0][endTime]=2007')
        .send('educations[0][name]=高中')
        .send('educations[0][degree]=中学')
        .send('educations[1][startTime]=2007')
        .send('educations[1][endTime]=2011')
        .send('educations[1][name]=大学')
        .send('educations[1][degree]=本科')
        .send('educations[2][startTime]=2011')
        .send('educations[2][endTime]=2014')
        .send('educations[2][name]=研究生')
        .send('educations[2][degree]=硕士')
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(sendObject));
        })
        .end(done);
    });

    it('shold send encoded parameter by urlencoded array', function (done) {
      request
        .post('/demo/post')
        .set('content-type', 'application/x-www-form-urlencoded')
        .send('username=admin')
        .send('token=1234567890')
        // simple object
        .send('info[nickname]=' + encodeURIComponent('花落莫离'))
        .send('info[gender]=M')
        .send('info[age]=28')
        // complex object
        .send('order[serialNumber]=20160407090102123')
        .send('order[state]=0')
        .send('order[address][code]=370203')
        .send('order[address][name]=' + encodeURIComponent('山东省青岛市市北区'))
        .send('order[products][0][id]=1234')
        .send('order[products][0][name]=iphone SE')
        .send('order[products][0][price]=3456.03')
        .send('order[products][0][amount]=1')
        .send('order[products][1][id]=2345')
        .send('order[products][1][name]=ipad pro')
        .send('order[products][1][price]=6666.66')
        .send('order[products][1][amount]=2')
        // simple array
        .send('deliverTime[]=1')
        .send('deliverTime[]=2')
        .send('deliverTime[]=3')
        // complex array
        .send('educations[0][startTime]=2004')
        .send('educations[0][endTime]=2007')
        .send('educations[0][name]=' + encodeURIComponent('高中'))
        .send('educations[0][degree]=' + encodeURIComponent('中学'))
        .send('educations[1][startTime]=2007')
        .send('educations[1][endTime]=2011')
        .send('educations[1][name]=' + encodeURIComponent('大学'))
        .send('educations[1][degree]=' + encodeURIComponent('本科'))
        .send('educations[2][startTime]=2011')
        .send('educations[2][endTime]=2014')
        .send('educations[2][name]=' + encodeURIComponent('研究生'))
        .send('educations[2][degree]=' + encodeURIComponent('硕士'))
        .expect(200)
        .expect(function (res) {
          assert.equal(JSON.stringify(res.body), JSON.stringify(sendObject));
        })
        .end(done);
    });

  });


  // send parameter in uri route
  describe('test uri method', function () {

    it('shold send parameter in uri method', function (done) {
      request
        .post('/demo/uri/admin/1234567890')
        .expect(200)
        .expect(function (res) {
          assert.equal(res.body.username, 'admin');
          assert.equal(res.body.token, '1234567890');
        })
        .end(done);
    });

  });

  // upload
  describe('test upload file', function () {

    it('shold upload file', function (done) {
      request
        .post('/demo/upload')
        .field('username', 'admin')
        .field('token', '1234567890')
        .field('address[code]', '370203')
        .field('address[home]', '山东省青岛市市北区')
        .attach('photo', path.join(__dirname, 'index.js'))
        .attach('background', path.join(__dirname, 'demo.js'))
        .expect(200)
        .expect(function (res) {
          assert.equal(res.body.username, 'admin');
          assert.equal(res.body.token, '1234567890');
          assert.equal(res.body.address.code, '370203');
          assert.equal(res.body.address.home, '山东省青岛市市北区');
          assert.equal(res.body.photo, 'index.js');
          assert.equal(res.body.background, 'demo.js');
        })
        .end(done);
    });

  });


  // upload
  describe('test download file', function () {

    it('shold download file', function (done) {
      request
        .get('/demo/download/1234')
        .expect(200)
        .expect(function (res) {
          // get filename
          var filename = res.headers['content-disposition'].substring('attachment; filename='.length);
          filename = filename.substring(1, filename.length - 1);
          assert.equal(filename, 'index.js');

          // create folder
          var folder = path.join(__dirname, '../downloads');
          if (!fs.existsSync(folder)) {
            fs.mkdirSync(folder);
          }
          // write
          fs.writeFile(path.join(folder, filename), res.text, function (err) {
            if (err) {
              console.error(err);
            }
          });
        })
        .end(done);
    });

  });


});