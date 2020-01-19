const path = require("path");
const fs = require("fs");
const StaticServer = require("static-server");
const browserify = require("browserify");
const scssify = require("scssify");
const browserifyCSS = require("browserify-css");
const browserifyShim = require("browserify-shim");
const babelify = require("babelify");
const watchify = require('watchify');

const outputDir = process.argv[2];
const doWatchify = process.argv[3]; // true or false

const srcDirPath = "src";
const mainJSPath = "src/js/Site.js";
const bundleJSPath = path.join(outputDir, "bundle.js");
const bundleCSSPath = path.join(outputDir, "bundle.css");

if (fs.existsSync(bundleCSSPath)) {
    fs.unlinkSync(bundleCSSPath);
}

let browserifyInstance = browserify(mainJSPath, {cache: {}, packageCache: {}});
applyTransforms();

if (doWatchify === "true") {
    browserifyInstance.plugin(watchify);
    browserifyInstance.on("update", bundle);
    browserifyInstance.on('log', function (msg) {
        console.log(msg)
    });

    bundle();

    const server = new StaticServer({
        rootPath: outputDir,
        port: 4567,
    });
    server.start(function () {
        console.log('Server listening to', server.port);
    });
} else {
    bundle();
}

function bundle() {
    browserifyInstance.bundle().on("error", console.error).pipe(fs.createWriteStream(bundleJSPath));
}

function applyTransforms() {
    browserifyInstance.transform(scssify).transform(browserifyCSS, {
        global: true,
        rootDir: srcDirPath,
        onFlush: function (options, done) {
            fs.appendFileSync(bundleCSSPath, options.data);
            done(null);
        }
    }).transform(babelify, {
        presets: ["@babel/preset-env", "@babel/preset-react"],
        plugins: ["@babel/plugin-proposal-class-properties"]
    }).transform(browserifyShim, {global: true})
}
