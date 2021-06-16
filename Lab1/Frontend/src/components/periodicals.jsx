import React from "react";
import { fetchGET } from "../other/utils";
import { SERVER_URL } from "../other/constants";

function PeriodicalItem(props) {
    const priceFormat = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' });

    return (
        <div>
            <div>
                <img src={props.image} />
            </div>
            <div>
                <p>{props.description.slice(0, 97) + "..."}</p>
                <p>{priceFormat.format(props.priceForMonth)} for month</p>
            </div>
        </div>
    );
}

class Periodicals extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            periodicals: []
        };

        fetchGET(SERVER_URL + "/periodicals")
        .then(result => {
            this.setState({
                periodicals: result['periodicals']
            });
        });
    }

    render() {
        return (
            this.state.periodicals.map(periodical => <PeriodicalItem 
                image={periodical.image}
                description={periodical.description}
                priceForMonth={periodical.priceForMonth} />)
        );
    }
}

export default Periodicals;