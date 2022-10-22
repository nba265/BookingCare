import React, {Component} from "react";
import UserService from "../services/UserService";

class UserComponent extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            users: []
        }

    }

    componentDidMount() {
        UserService.getUsers().then((response) => {
            this.setState({
                user: response.data
            })
        })
    }

    render() {
        return (
            <div>
                <h1>Simple Table</h1>

                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Full Name</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.users.map(value => {
                            return (<tr key={value.id}>
                                <td>{value["id"]}</td>
                                <td>{value["fullName"]}</td>
                                <td>{value["status"]}</td>
                            </tr>)
                        })
                    }
                    </tbody>
                </table>

            </div>
        )
    }
}

export default UserComponent;