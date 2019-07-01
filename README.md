<p align="center"><img src="https://i.imgur.com/TltWc5R.png"></p>
Alpaca Java Backtest is a simple and fast stock trading algorithm backtesting library. It uses
[Polygon](https://polygon.io/) for historic quotes, trades, aggregates, and other equity data points to give
instantaneously give backtesters a more accurate understanding of what their algorithm might do in a live market.
You should have a live trading account with [Alpaca](https://alpaca.markets) in order to get a free API key for Polygon to
use this backtester. This Library uses [alpaca-java](https://github.com/mainstringargs/alpaca-java) exclusively as it
contains a PolygonAPI and useful POJO classes.

## To-Do
* Finish 1.0
  * Ticker Data caching and indexing for fastest retrieval speed (chronological ordering system)
  * Actual backtesting functionality
* Add various strategies to test in src/test/java with JUnit
* More stats (like shown in the 'stats' output [here](http://pmorissette.github.io/bt/index.html#a-quick-example)
* Add JFreeCharts for P&L charts, comp. of SPX, etc.