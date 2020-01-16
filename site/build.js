const path = require("path");
const fs = require("fs");
const browserify = require("browserify");
const scssify = require("scssify");
const browserifyCSS = require("browserify-css");
const babelify = require("babelify");

const outputDir = process.argv[2];

const srcDirPath = "src";
const mainJSPath = "src/js/main.js";
const bundleJSPath = path.join(outputDir, "bundle.js");
const bundleCSSPath = path.join(outputDir, "bundle.css");

if (fs.existsSync(bundleCSSPath)) {
    fs.unlinkSync(bundleCSSPath);
}

browserify(mainJSPath).transform(scssify).transform(browserifyCSS, {
    global: true,
    rootDir: srcDirPath,
    onFlush: function (options, done) {
        fs.appendFileSync(bundleCSSPath, options.data);
        done(null);
    }
}).transform(babelify, {
    presets: ["@babel/preset-env", "@babel/preset-react"],
    plugins: ["@babel/plugin-proposal-class-properties"]
}).bundle().pipe(fs.createWriteStream(bundleJSPath));
