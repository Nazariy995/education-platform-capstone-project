var gulp = require('gulp');
var htmlmin = require('gulp-htmlmin');
var stripDebug = require('gulp-strip-debug');
var templateCache = require('gulp-angular-templatecache');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var concatCss = require('gulp-concat-css');
var ngAnnotate = require('gulp-ng-annotate');
var sourcemaps = require('gulp-sourcemaps');
var browserify = require('browserify');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var gutil = require('gulp-util');
var del = require('del');
var path = require('path');

gulp.task('clean', function() {
	return del['src/main/resources/static/**'];
});

gulp.task('templates', function(){
    return gulp.src([
        'src/main/js/**/*.html'
        ])
        .pipe(htmlmin({collapseWhitespace: false}))
        .pipe(templateCache({
            module : 'app.templates',
            standalone : true
        }))
        .pipe(concat('templates.js'))
        .pipe(gulp.dest('src/main/js/dist/'))
});

gulp.task('browserify', [ 'clean' ], function() {
	return browserify({
		entries : [ 'src/main/js/app.js' ],
		paths : [ 'src/main/js/' ]
	}).bundle().on('error', function(e) {
		gutil.log(e);
		process.exit(1);
	})
        .pipe(source('src/main/js/dist/bundle.js'))
        .pipe(buffer())
        .pipe(stripDebug())
        .pipe(ngAnnotate())
        .pipe(uglify().on('error', function(e){
            gutil.log(e);
        }))
        .pipe(gulp.dest('.'));

});

gulp.task('css',function(){
    return gulp.src([
        'src/main/js/views/**/*.css',
        'src/main/js/components/**/*.css'
        ])
        .pipe(concatCss('src/main/js/dist/bundle.css'))
        .pipe(gulp.dest('.'));
});

gulp.task('copy', [ 'browserify', 'css', 'templates' ], function() {
	return gulp.src('src/main/js/**').pipe(
			gulp.dest('src/main/resources/static/'));
});

gulp.task('default', [ 'copy' ]);
