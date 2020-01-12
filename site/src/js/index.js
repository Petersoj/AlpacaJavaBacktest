import React, {Component} from "react";
import ReactDOM from 'react-dom';

// import DateRangePicker from "@wojtekmaj/react-daterange-picker"

class ShoppingList extends Component {
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

ReactDOM.render(<ShoppingList name="Jacob"/>, document.getElementById("app"));
