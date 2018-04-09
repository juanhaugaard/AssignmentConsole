const path = require('path');
var PUBLIC_DIR = path.resolve(__dirname, 'public');

module.exports = {
  entry: ["./src/webapp/index.js"],
  output: {
    path: PUBLIC_DIR,
    publicPath: "/",
    filename: "bundle.js"
  },
  module: {
    rules: [
      { test: /\.(css|html|jpg|png)$/, use: 'file-loader?name=[path][name].[ext]&context=./src/webapp/' },
      { test: /\.js$/,  use: 'babel-loader?cacheDirectory=true', exclude: /(node_modules|bower_components)/ }
    ]
  },
  mode: "development",
  resolve: {
    extensions: ['.js', '.jsx', '.css']  },
  devServer: {
    contentBase: "./"
  }
};
