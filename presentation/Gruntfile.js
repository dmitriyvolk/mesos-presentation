/* global module:false */
module.exports = function(grunt) {
	var port = grunt.option('port') || 8000;
	var helper_port = grunt.option('helper_port') || 8001;
	// Project configuration
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		meta: {
			banner:
				'/*!\n' +
				' * xbo-mesos-presentation <%= pkg.version %> (<%= grunt.template.today("yyyy-mm-dd, HH:MM") %>)\n' +
				' * https://github.com/dmitriyvolk/mesos-presentation/\n' +
				' * MIT licensed\n' +
				' *\n' +
				' * Copyright (C) 2015 Dmitriy Volk, dmitriy.volk@gmail.com\n' +
				' */'
		},

/*		qunit: {
			files: [ 'test/*.html' ]
		},
*/
		uglify: {
			options: {
				banner: '<%= meta.banner %>\n'
			},
			build: {
				src: 'js/logomarker.js',
				dest: 'js/logomarker.min.js'
			}
		},

/*		sass: {
			core: {
				files: {
					'css/reveal.css': 'css/reveal.scss',
				}
			},
			themes: {
				files: [
					{
						expand: true,
						cwd: 'css/theme/source',
						src: ['*.scss'],
						dest: 'css/theme',
						ext: '.css'
					}
				]
			}
		},

		autoprefixer: {
			dist: {
				src: 'css/reveal.css'
			}
		},
*/
		cssmin: {
			compress: {
				files: {
					'css/presentation.min.css': [ 'css/presentation.css' ]
				}
			}
		},

		jshint: {
			options: {
				curly: false,
				eqeqeq: true,
				immed: true,
				latedef: true,
				newcap: true,
				noarg: true,
				sub: true,
				undef: true,
				eqnull: true,
				browser: true,
				expr: true,
				globals: {
					head: false,
					module: false,
					console: false,
					unescape: false,
					define: false,
					exports: false,
					require: false,
					Reveal: false
				}
			},
			files: [ 'Gruntfile.js', 'js/logomarker.js' ]
		},

		connect: {
			server: {
				options: {
					port: port,
					base: '.',
					livereload: true,
					open: true
				}
			}
		},
/*
		helper: {
			port: helper_port
		},
*/
		zip: {
			'xbo-mesos-presentation.zip': [
				'index.html',
				'css/**',
				'js/**',
				'images/**',
				'bower_components/**'
			]
		},

		watch: {
			options: {
				livereload: true
			},
			js: {
				files: [ 'Gruntfile.js', 'js/logomarker.js' ],
				tasks: 'js'
			},
			theme: {
				files: [ 'css/theme/source/*.scss', 'css/theme/template/*.scss' ],
				tasks: 'css-themes'
			},
			css: {
				files: [ 'css/presentation.scss', 'css/presentation.css' ],
				tasks: 'css-core'
			},
			html: {
				files: [ 'index.html']
			}/*,
			helper: {
				files: [ 'helper_static/**', 'helper.js' ],
				tasks: 'helper'
			}*/
		}

	});

	// Dependencies
	// grunt.loadNpmTasks( 'grunt-contrib-qunit' );
	grunt.loadNpmTasks( 'grunt-contrib-jshint' );
	grunt.loadNpmTasks( 'grunt-contrib-cssmin' );
	grunt.loadNpmTasks( 'grunt-contrib-uglify' );
	grunt.loadNpmTasks( 'grunt-contrib-watch' );
	// grunt.loadNpmTasks( 'grunt-sass' );
	grunt.loadNpmTasks( 'grunt-contrib-connect' );
	// grunt.loadNpmTasks( 'grunt-autoprefixer' );
	grunt.loadNpmTasks( 'grunt-zip' );

	// Default task
	grunt.registerTask( 'default', [ 'css', 'js' ] );

	// JS task
	// grunt.registerTask( 'js', [ 'jshint', 'uglify', 'qunit' ] );
	grunt.registerTask( 'js', [ 'jshint', 'uglify'] );

	// Theme CSS
	//grunt.registerTask( 'css-themes', [ 'sass:themes' ] );

	// Core framework CSS
	// grunt.registerTask( 'css-core', [ 'sass:core', 'autoprefixer', 'cssmin' ] );
	grunt.registerTask( 'css-core', [ 'cssmin' ] );

	// All CSS
	// grunt.registerTask( 'css', [ 'sass', 'autoprefixer', 'cssmin' ] );
	grunt.registerTask( 'css', [ 'cssmin' ] );

	// Package presentation to archive
	grunt.registerTask( 'package', [ 'default', 'zip' ] );

	// Serve presentation locally
	grunt.registerTask( 'serve', [ 'connect', 'watch' ] );

	// Run tests
	// grunt.registerTask( 'test', [ 'jshint', 'qunit' ] );
	grunt.registerTask( 'test', [ 'jshint' ] );

	//Run background helper
/*	grunt.registerTask( 'helper', 'Run background helper', function () {
		var port = grunt.config('helper.port');
		grunt.log.writeln("Starting background helper on port " + port);
		require('./helper.js').listen(port);
	});
*/
};
