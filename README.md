<p align="center"><img src="https://i.imgur.com/TltWc5R.png"></p>
<p align="center">
<a href="https://github.com/Petersoj/AlpacaJavaBacktest"><img src="https://img.shields.io/github/license/Petersoj/AlpacaJavaBacktest.svg" alt="License"></a>
</p>

AlpacaJavaBacktest is a simple and fast Stock Trading Algorithm Backtesting Library for Java that uses <a href="https://polygon.io/">Polygon</a> for historic quotes, trades, aggregates, and other data points to give trading algorithm developers a more accurate understanding of what their algorithm might do in a live market. You should have a live trading account with <a href="https://alpaca.markets">Alpaca</a> in order to get a free API key for Polygon to use this backtesting library. This Library uses <a href="https://github.com/mainstringargs/alpaca-java">alpaca-java</a> exclusively as it contains a Polygon Java API and useful POJO classes.

## To-Do
* Finish 1.0
* Add various strategies to test in src/test/java with JUnit
* Implement Ticker Data caching and indexing for fastest retrieval speed (chronological ordering system) for overlapping dates with ticker data queries
* Historical ETB and HTB ticker data
* More stats (like shown in the 'stats' output [here](http://pmorissette.github.io/bt/index.html#a-quick-example))
* Add JFreeCharts OR internal website that hosts interactive charts via [Plotly](https://plot.ly/javascript/) for P&L charts, comparison of SPX, executed trades displayed on graph, etc.
