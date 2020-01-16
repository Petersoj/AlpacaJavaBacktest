import React, {Component} from "react";
import ReactDOM from "react-dom";
import DateRangePicker from "@wojtekmaj/react-daterange-picker";

class Panel extends Component {

    constructor(props) {
        super(props);

        this.state = {
            date: [new Date(), new Date()],
        };
    }

    onChange = (date) => {
        this.setState({date})
    };

    Header = () => {
        return <header>
            <img src="assets/logo.png" alt=""/>
            <img src="assets/name.png" alt=""/>
        </header>;
    };

    render() {
        return (<div className="panel">{this.Header()}
                    <DateRangePicker
                            onChange={(date) => this.onChange(date)}
                            value={this.state.date}
                    />
                </div>
        );
    }
}

export default Panel;
