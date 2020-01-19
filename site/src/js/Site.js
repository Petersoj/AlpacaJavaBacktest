import React from "react";
import ReactDOM from "react-dom";
import Panel from "./Panel";
import BacktestChart from "./BacktestChart";

import "../scss/main.scss";

class Site {

    run() {
        ReactDOM.render(
                <div>
                    <Panel/>
                    <BacktestChart/>
                </div>,
                document.getElementsByClassName("site")[0]);
    }
}

const site = new Site();
site.run();
