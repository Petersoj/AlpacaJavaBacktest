import React from "react";
import ReactDOM from "react-dom";
import DateRangePicker from "@wojtekmaj/react-daterange-picker";
import "../scss/main.scss";

class MyDateRangePicker extends React.Component {

    state = {
        date: [new Date(), new Date()],
    };

    onChange = date => this.setState({date});

    render() {
        return (<DateRangePicker
                onChange={this.onChange}
                value={this.state.date}
        />);
    }
}

class ShoppingList extends React.Component {
    render() {
        return (
                <div className="shopping-list">
                    <h1>Shopping List for {this.props.name}</h1>
                    <ul>
                        <li>Instagram</li>
                        <li>WhatsApp</li>
                        <li>Oculus</li>
                    </ul>
                </div>
        );
    }
}

ReactDOM.render(<MyDateRangePicker/>, document.getElementById("app"));
