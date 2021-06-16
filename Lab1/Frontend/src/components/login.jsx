import React from "react";
import {Redirect} from "react-router-dom";
import { fetchPOST } from "../other/utils";
import { SERVER_URL } from "../other/constants";

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            loggedIn: false,
            errorMessage: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit(event) {
        fetchPOST(SERVER_URL + "/login", { 
            username: this.state.username, 
            password: this.state.password 
        })
		.then(result => {
			if (result.token !== "") {
				localStorage.setItem("token", result.token);
				localStorage.setItem("role", result.role);
                console.log(`Got user ${result.token}`);
                this.setState({
                    loggedIn: true
                });
			} else {
				alert("Wrong credentials");
			}
        })
        .catch(error => {
            this.setState({ errorMessage: error.toString() });
            console.error('Error occurred.', error);
        });

		event.preventDefault();
    }

    render() {
        if (this.state.loggedIn) {
            return <Redirect to="/periodicals"/>;
        }

        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Username
                    <input 
                        name="username"
                        type="text"
                        onChange={this.handleChange}
                    />
                </label>
                <br/>
                <label>
                    Password
                    <input 
                        name="password"
                        type="password"
                        onChange={this.handleChange}
                    />
                </label>
                <input type="submit" value="Log In" />
            </form>
        );
    }
}

export default Login;