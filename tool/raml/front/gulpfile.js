var gulp = require('gulp');
var path = require('path');
// clean
var clean = require('gulp-clean');
// concat
var concat = require('gulp-concat');
// rename
var rename = require("gulp-rename");

// js
var jshint = require('gulp-jshint');
var sourcemaps = require('gulp-sourcemaps');
var ngAnnotate = require('gulp-ng-annotate');
var uglify = require('gulp-uglify');

// html
var htmlreplace = require('gulp-html-replace');

// md5
var rev = require('gulp-rev');

// browser
var browserSync = require('browser-sync').create();

var dist = './public';

var tmp = {
  // css-assets
  // css-app
  // js-assets
  // js-app
};

// clean
gulp.task('clean', function () {
  return gulp.src(dist, {
      read: false
    })
    .pipe(clean());
});

// js - assets
gulp.task('js:assets', function () {
  return gulp.src([
      './bower_components/jquery/dist/jquery.js',
      './bower_components/bootstrap/dist/js/bootstrap.js',
      './bower_components/angular/angular.js',
      './bower_components/angular-ui-router/release/angular-ui-router.js',
      './bower_components/oclazyload/dist/ocLazyLoad.js',

      './bower_components/ngstorage/ngStorage.js',
      './bower_components/angular-animate/angular-animate.js',
      './bower_components/angular-cookies/angular-cookies.js',
      './bower_components/angular-resource/angular-resource.js',
      './bower_components/angular-sanitize/angular-sanitize.js',
      './bower_components/angular-touch/angular-touch.js',

      './bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      './bower_components/front-angular-blocks/dist/front-angular-blocks.js'
    ])
    .pipe(concat('assets.js'))
    .pipe(rev())
    .pipe(rename(function (path) {
      tmp['js-assets'] = path.basename;
    }))
    .pipe(gulp.dest(path.join(dist, 'js')))
    .pipe(sourcemaps.init())
    .pipe(uglify())
    .pipe(rename(function (path) {
      path.basename += ".min";
    }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(path.join(dist, 'js')));
});

// js - app
gulp.task('js:app', function () {
  return gulp.src([
      '!./src/app/test/**/*', // can not concat test js

      './src/app/**/*.module.js', // module
      './src/app/**/*.provider.js', // provider
      './src/app/**/*.factory.js', // factory
      './src/app/**/*.service.js', // service
      './src/app/**/*.controller.js', // controller
      './src/app/**/*.config.js', // config
      './src/app/**/*.router.js', // router

      './src/js/**/*'
    ])
    .pipe(jshint())
    .pipe(jshint.reporter('default'))
    .pipe(concat('app.js'))
    .pipe(rev())
    .pipe(rename(function (path) {
      tmp['js-app'] = path.basename;
    }))
    .pipe(ngAnnotate())
    .pipe(gulp.dest(path.join(dist, 'js')))
    .pipe(sourcemaps.init())
    .pipe(uglify())
    .pipe(rename(function (path) {
      path.basename += ".min";
    }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(path.join(dist, 'js')));
});

// js
gulp.task('js', ['js:assets', 'js:app'], function (cb) {
  return cb();
});

// fonts
gulp.task('fonts', function () {
  return gulp.src([
      './bower_components/bootstrap/dist/fonts/**/*',
      './bower_components/font-awesome/fonts/**/*',
      './bower_components/simple-line-icons/fonts/**/*',
      './src/fonts/**/*'
    ])
    .pipe(gulp.dest(path.join(dist, 'fonts')));
});

// css - assets
gulp.task('css:assets', ['fonts'], function () {
  return gulp.src([
      './bower_components/bootstrap/dist/css/bootstrap.css',
      './bower_components/font-awesome/css/font-awesome.css',
      './bower_components/simple-line-icons/css/simple-line-icons.css'
    ])
    .pipe(concat('assets.css'))
    .pipe(rev())
    .pipe(rename(function (path) {
      tmp['css-assets'] = path.basename;
    }))
    .pipe(gulp.dest(path.join(dist, 'css')))
    .pipe(sourcemaps.init())
    .pipe(rename(function (path) {
      path.basename += ".min";
    }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(path.join(dist, 'css')));
});

// css - app
gulp.task('css:app', function () {
  return gulp.src([
      './src/css/animate.css',
      './src/css/font.css',
      './src/css/app.css'
    ])
    .pipe(concat('app.css'))
    .pipe(rev())
    .pipe(rename(function (path) {
      tmp['css-app'] = path.basename;
    }))
    .pipe(gulp.dest(path.join(dist, 'css')))
    .pipe(sourcemaps.init())
    .pipe(rename(function (path) {
      path.basename += ".min";
    }))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(path.join(dist, 'css')));

});

// css
gulp.task('css', ['css:assets', 'css:app'], function (cb) {
  return cb();
});

// image
gulp.task('image', function () {
  return gulp.src([
      './src/img/**/*'
    ])
    .pipe(gulp.dest(path.join(dist, 'img')));
});

// html - index
gulp.task('html:index', ['css'], function () {
  return gulp.src('./src/index.html')
    .pipe(htmlreplace({
      // css
      'css-assets': './css/' + tmp['css-assets'] + '.css',
      'css-app': './css/' + tmp['css-app'] + '.css',
      // js
      'js-assets': './js/' + tmp['js-assets'] + '.js',
      'js-app': './js/' + tmp['js-app'] + '.js'
    }))
    .pipe(gulp.dest(dist));
});

// html - tpl
gulp.task('html:tpl', function () {
  return gulp.src(
      ['./src/tpl/**/*']
    )
    .pipe(gulp.dest(path.join(dist, 'tpl')));
});

// html
gulp.task('html', ['html:index', 'html:tpl'], function (cb) {
  return cb();
});

// publish
gulp.task('pub', ['clean'], function (cb) {
  gulp.start('js');
  gulp.start('css');
  gulp.start('image');
  gulp.start('html');
  return cb();
});

// watch
gulp.task('watch', ['pub'], function (cb) {

  browserSync.init({
    port: 3000, // 端口
    server: {
      baseDir: [dist], // 主目录
      index: "index.html", // 主页
      routes: { // 路由
        // "/bower_components": "bower_components",
        '/favicon.ico': '../favicon.ico'
      }
    },
    startPath: "./" // 启动路径
  });

  // js - app
  gulp.watch('./src/app/**/*', ['js:app']).on('change', browserSync.reload);
  gulp.watch('./src/tpl/**/*', ['html:tpl']).on('change', browserSync.reload);
  gulp.watch('./src/index.html', ['html:index']).on('change', browserSync.reload);
  gulp.watch('./src/css/**/*', ['css:app']).on('change', browserSync.reload);

  return cb();

});

// default
gulp.task('default', ['clean'], function () {
  return gulp.start('watch');
});