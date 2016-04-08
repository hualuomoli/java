var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var morgan = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var multer = require('multer');
var colors = require('colors');

var logger = require('./logger/logger');
var routes = require('./routes');

var app = express();

// set port
app.set('port', process.env.PORT || 3000);

// uncomment after placing your favicon in /public
// app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
// morgan
app.use(morgan('dev'));
// file upload
app.use(multer({
  dest: path.join(__dirname, 'uploads')
}));

// parse cookie
app.use(cookieParser());

// static
// app.use(express.static(path.join(__dirname, 'client', 'dist')));


// route
routes(app);

app.listen(app.get('port'), function () {
  logger.debug('server started in ' + app.get('port'));
})

// export
module.exports = app;