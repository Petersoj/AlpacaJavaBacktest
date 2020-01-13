import React from "react";
import ReactDOM from "react-dom";
import DateRangePicker from "@wojtekmaj/react-daterange-picker";
import indexStyle from "../scss/style.scss";

class MyDateRangePicker extends React.Component {

    constructor(props) {
        super(props);
        this.state = {date: [new Date(), new Date()]};
    }

    onChange() {
        console.log("HEYO")
    }

    // onChange = date => this.setState({date});

    render() {
        return (<DateRangePicker
                onChange={this.onChange}
                value={this.state.date}/>);
    }
}

ReactDOM.render(<MyDateRangePicker/>, document.getElementById("app"));
