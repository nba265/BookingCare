import React, {Component} from "react";
import UserService from "../services/UserService";
import axios from "axios";

const USER_REST_API_URL='http://localhost:8080/api/users';
class UserComponent extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            users: []
        }

    }

    async componentDidMount() {
        await axios.get(USER_REST_API_URL).then(r => {
            this.setState({
                users: r.data
            })
        }).catch(reason => console.log(reason))
        console.log(this.state.users)
    }
     /*componentDidMount() {
         fetch(USER_REST_API_URL)
             .then(response => response.json())
             .then(data => this.setState({users: data,}));
     }
*/
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