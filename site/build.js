const path = require("path");
const rollup = require("rollup");
const babel = require("rollup-plugin-babel");
const postcss = require("rollup-plugin-postcss");
const resolve = require("@rollup/plugin-node-resolve");
const commonjs = require("@rollup/plugin-commonjs");
const replace = require("@rollup/plugin-replace");

const mainJSPath = "src/js/main.js";

const outputDir = process.argv[2];
const resolveExtensions = [".js", ".jsx", ".ts", ".tsx", ".css", ".scss"];

const inputOptions = {
    input: mainJSPath,
    // external: ["react", "react-dom"],
    plugins: [
        babel({
            presets: ["@babel/preset-env", "@babel/preset-react"],
            plugins: ["@babel/plugin-proposal-class-properties"],
            exclude: 'node_modules/**'
        }),
        postcss({extract: true}),
        // jsx({factory: 'React.createElement'}),
        resolve({extensions: resolveExtensions, browser: true}),
        commonjs({
            include: 'node_modules/**',
            namedExports:
                    {
                        './node_modules/react/react.js':
                                [
                                    'cloneElement',
                                    'createElement',
                                    'PropTypes',
                                    'Children',
                                    'Component'
                                ],
                    }
        }),
        replace({
            'process.env.NODE_ENV': JSON.stringify('development')
        }),
    ]
};
const outputOptions = {
    file: path.join(outputDir, "bundle.js"),
    format: "iife"
    // globals: {
    //     "react": "https://unpkg.com/react@16/umd/react.development.js",
    //     "react-dom": "https://unpkg.com/react-dom@16/umd/react-dom.development.js"
    // }
};

async function build() {
    const bundle = await rollup.rollup(inputOptions);
    const {output} = await bundle.generate(outputOptions);
    await bundle.write(outputOptions);
}

build();
