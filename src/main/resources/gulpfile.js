
var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var ngAnnotate = require('gulp-ng-annotate');
var sourcemaps = require('gulp-sourcemaps');
var browserify = require('browserify');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var gutil = require('gulp-util');

gulp.task('js', function () {
//  gulp.src(['app/**/module.js'])
//    .pipe(concat('app.js'))
//    .pipe(gulp.dest('.'))
    browserify({
        entries : ['./static/app.js'],
        paths : ['./static/']
    })
        .bundle()
        .on('error', function(e){
            gutil.log(e);
        })
        .pipe(source('./static/dist/bundle.js'))
//        .pipe(buffer())
//        .pipe(sourcemaps.init({loadMaps: true}))
//        .pipe(ngAnnotate())
//         .pipe(uglify())
//        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest('.'))

})

gulp.task('watch', ['js'], function () {
  gulp.watch('static/**/*.js', ['js'])
})
