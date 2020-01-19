const path = require("path");
const fs = require("fs");
const browserify = require("browserify");
const scssify = require("scssify");
const browserifyCSS = require("browserify-css");
const browserifyShim = require("browserify-shim");
const babelify = require("babelify");

const outputDir = process.argv[2];
const watchify = process.argv[3]; // true or false

const srcDirPath = "src";
const mainJSPath = "src/js/Site.js";
const bundleJSPath = path.join(outputDir, "bundle.js");
const bundleCSSPath = path.join(outputDir, "bundle.css");

if (fs.existsSync(bundleCSSPath)) {
    fs.unlinkSync(bundleCSSPath);
}

let browserifyInstance = browserify(mainJSPath);

// .bundle().pipe(fs.createWriteStream(bundleJSPath));

function applyTransforms(browserifyInstance) {
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
