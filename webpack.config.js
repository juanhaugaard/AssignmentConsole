const path = require('path');

module.exports = {
  entry: ["./src/webapp/index.js"],
  output: {
    path: path.resolve(__dirname, "public"),
    publicPath: "/",
    filename: "bundle.js"
  },
  module: {
    rules: [
      { test: /\.css$/, use: 'css-loader' },
      { test: /\.js$/,  use: 'babel-loader?cacheDirectory=true', exclude: /(node_modules|bower_components)/ }
    ]
  },
  mode: "development",
  resolve: {
    extensions: [".js", ".jsx"]
  },
  devServer: {
    contentBase: "./"
  }
};
