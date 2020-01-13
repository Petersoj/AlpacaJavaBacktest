import resolve from '@rollup/plugin-node-resolve';
import commonjs from '@rollup/plugin-commonjs';
import postcss from 'rollup-plugin-postcss'
import jsx from 'rollup-plugin-jsx'

const resolveExtensions = ['.js', '.jsx', '.ts', '.tsx'];

export default {
    input: 'src/js/index.js',
    output: {
        name: 'bundle',
        file: "bundle.js",
        format: 'umd',
        globals: {
            "react": "https://unpkg.com/react@16/umd/react.development.js",
            "react-dom": "https://unpkg.com/react-dom@16/umd/react-dom.development.js"
        }
    },
    external: ['react', 'react-dom'],
    plugins: [
        postcss({extract: true}),
        jsx({factory: 'React.createElement'}),
        resolve({extensions: resolveExtensions, browser: true}),
        commonjs()
    ],
};
