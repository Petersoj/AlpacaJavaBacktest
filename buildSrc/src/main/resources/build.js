/*
"css:dev": "mkdir -p dist/css && node-sass src/scss/ --output dist/css/",
"js:dev": "mkdir -p dist/js && browserify src/js -p [ tsify ] > dist/js/bundle.js",

"//": " --- BUILD (GENERIC) --- ",
"clean": "rimraf dist",
"mkdirs": "mkdir -p dist/js && mkdir -p dist/css",
"copy:assets": "cp -r assets/* dist",
"copy:html": "find src -name '*.html' -type f -exec cp {} dist \\;",
"copy": "npm run copy:assets && npm run copy:html",

"//": " --- BUILD (DEVELOPMENT) --- ",
"prebuild:dev": "npm run clean && npm run mkdirs && npm run copy",
"build:dev": "npm run css:dev && npm run js:dev && npm run js:vendor",

"//": " --- BUILD (PRODUCTION) --- ",
"prebuild:prod": "npm run clean && npm run mkdirs && npm run copy",
"build:prod": "npm run css:prod && npm run js:prod && npm run js:vendor",

"//": " --- DEPLOYMENT & ZIP --- ",
"prezip": "npm run build:prod",
"zip": "cd dist && zip -r -X ../app.zip ."
*/

const path = require("path");
const fs = require("fs");
const shell = require('shelljs');
const sass = require('node-sass');

// Source Directories
const srcDir = "src";
const assetsSrcDir = path.join(srcDir, "assets");
const scssSrcDir = path.join(srcDir, "scss");
const tsSrcDir = path.join(srcDir, "ts");

// Distribution Directories
const distDir = "dist";
const assetsDistDir = path.join(distDir, "assets");
const jsDistDir = path.join(distDir, "js");
const cssDistDir = path.join(distDir, "css");

function createDistDirs() {
    process.stdout.write("Creating distribution directories")

    shell.mkdir("-p", [assetsDistDir, jsDistDir, cssDistDir])
}

function buildAssets() {
    process.stdout.write("Building assets")
}

function buildHTML() {
    process.stdout.write("Building HTML")
}

// https://github.com/sass/node-sass#usage
function buildSASS() {
    process.stdout.write("Building SASS")
    // var result = sass.renderSync({
    //     file: ,
    // });
}

function buildTSandBrowserify() {
    process.stdout.write("Building TS/Browserify")
}

createDistDirs()
buildAssets()
buildHTML()
buildSASS()
buildTSandBrowserify()
