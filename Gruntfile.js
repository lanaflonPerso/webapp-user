module.exports = function(grunt) {

// 1. Toutes les configurations vont ici: 
grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    app: {
    	source: 'src/main/sourceapp',
    	dist: 'src/main/webapp'
    },
    // Configuration du module Copy
    copy: {
        main: {
            files: [
			// bootstrap css
              {
                    expand: true, // Etendre la copie pour récupérer le contenu du repertoire
                    flatten: true, // Ne remonte pas l'arborescence depuis bower_components
                    dest: '<%= app.dist %>/inc/css/libs/', // Répertoire de destination
                    src: ['bower_components/bootstrap/dist/css/bootstrap.min.css'] // Fichier cible
              },
//            {
//                expand: true, // Etendre la copie pour récupérer le contenu du repertoire
//                flatten: true, // Ne remonte pas l'arborescence depuis bower_components
//                cwd: 'bower_components/bootstrap/dist/css/',
//                src: 'bootstrap.min.css', // Fichier source
//                dest: '<%= app.dist %>/inc/css/libs/', // Répertoire de destination
//                rename: function(dest, src) {
//                	return dest + "_" + src.substring(0, src.indexOf('.')) + '.scss';
//                }                
//            },
  			// bootstrap css
              {
                    expand: true, // Etendre la copie pour récupérer le contenu du repertoire
                    flatten: true, // Ne remonte pas l'arborescence depuis bower_components
                    dest: '<%= app.dist %>/inc/js/libs/', // Répertoire de destination
                    src: ['bower_components/bootstrap/dist/js/bootstrap.min.js'] // Fichier cible
              },
			// JQuery
            {
                expand: true, // Etendre la copie pour récupérer le contenu du repertoire
                flatten: true, // Ne remonte pas l'arborescence depuis bower_components
                dest: '<%= app.dist %>/inc/js/libs/', // Répertoire de destination
                src: ['bower_components/jquery/dist/jquery.js'] // Fichier cible
            },
            // font-awesome css
            {
                expand: true, // Etendre la copie pour récupérer le contenu du repertoire
                flatten: true, // Ne remonte pas l'arborescence depuis bower_components
                dest: '<%= app.dist %>/inc/css/libs/', // Répertoire de destination
                src: ['bower_components/font-awesome/css/font-awesome.min.css'] // Fichier cible
            }
            ]
        }
    },
    concat: {
            // 2. la configuration pour la concaténation va ici.
            dist: {
            src: [
                '<%= app.dist %>/inc/js/libs/*.js', // tous les JS dans libs
                '<%= app.dist %>/inc/js/login.js'  // ce fichier là
            ],
            dest: '<%= app.dist %>/inc/js/prod/production.js'
        }
    },
    uglify: {
        build: {
            src: '<%= app.dist %>/inc/js/prod/production.js',
            dest: '<%= app.dist %>/inc/js/prod/production.min.js'
        }
    },
    imagemin: {
        dynamic: {
            files: [{
                expand: true,
                cwd: '<%= app.dist %>/inc/img/',
                src: ['**/*.{png,jpg,gif}'],
                dest: '<%= app.dist %>/inc/img/prod/'
            }]
        }
    },
    sass: {
        dist: {
            options: {
                style: 'compressed'
            },
            files: {
                '<%= app.dist %>/inc/css/prod/style.min.css': '<%= app.dist %>/inc/css/style.scss'
            }
        } 
    },
    watch: {
        options: {
            livereload: true,
        },
        scripts: {
            files: ['<%= app.dist %>/inc/js/*.js'],
            tasks: ['concat', 'uglify'],
            options: {
                spawn: false,
            },
        },
        images: {
            files: ['<%= app.dist %>/inc/img/*.{png,jpg,gif}'],
            tasks: ['imagemin'],
            options: {
                spawn: false,
            }
        },
        css: {
            files: ['<%= app.dist %>/inc/css/*.scss'],
            tasks: ['sass'],
            options: {
                spawn: false,
            }
        }       
    }
});

// 3. Nous disons à Grunt que nous voulons utiliser ce plug-in.
grunt.loadNpmTasks('grunt-contrib-copy');
grunt.loadNpmTasks('grunt-contrib-concat');
grunt.loadNpmTasks('grunt-contrib-uglify');
grunt.loadNpmTasks('grunt-contrib-imagemin');
grunt.loadNpmTasks('grunt-contrib-sass');
grunt.loadNpmTasks('grunt-contrib-watch');

// 4. Nous disons à Grunt quoi faire lorsque nous tapons "grunt" dans la console.
// exemple : "grunt dev" ou "grunt prod".
grunt.registerTask('dev', ['copy', 'concat', 'uglify', 'imagemin', 'sass', 'watch']);
grunt.registerTask('prod', ['copy', 'concat', 'uglify', 'imagemin']);

};