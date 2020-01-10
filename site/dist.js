const path = require("path");
const filesys = require("fs");
const browserify = require("browserify");
const tsify = require("tsify");

const SRC_DIR = "src/";
const DIST_DIR = "dist/";

browserify()
    .add(path.join(SRC_DIR, "ts"))
    .plugin("tsify", {})
    .bundle()
    .pipe(filesys.createWriteStream(path.join(DIST_DIR, "bundle.js")));