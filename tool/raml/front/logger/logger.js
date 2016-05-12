var log4js = require('log4js');
var fs = require('fs');
var path = require('path');
var colors = require('colors');

var logs = path.join(__dirname, '../logs');
if (!fs.existsSync(logs)) {
  fs.mkdirSync(logs);
  console.log('create folder '.green + logs);
}

// config
log4js.configure({
  "appenders": [{
    "type": "console"
  }, {
    "type": "file",
    "absolute": true,
    "filename": logs + "/log.log",
    "maxLogSize": 20480,
    "backups": 3
  }, {
    "type": "logLevelFilter",
    "level": "error",
    "appender": {
      "type": "file",
      "absolute": true,
      "filename": logs + "/errors.log",
      "maxLogSize": 204800,
      "backups": 3
    }
  }]
});



module.exports = log4js.getLogger();