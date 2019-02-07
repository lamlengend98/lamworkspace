import React, { Component } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import axios from 'axios'

//GET
class Get extends React.Component {
  constructor() {
    super()
    this.state = {
      user: []
    }
  }

  componentDidMount() {
    axios.get('http://localhost:8888/student')
      .then(response => {
        console.log(response.data)
        this.setState({ user: response.data });
        console.log(this.state.user);
      })
  }

  render() {
    return (

      <div style={text_center}>
        <table style={width1}>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
          </tr>

          {this.state.user.map(users =>
            <tr>
              <td key={users.id}>{users.id}</td>
              <td key={users.id}>{users.name}</td>
              <td key={users.id}>{users.age}</td>
            </tr>
          )}
        </table>
      </div>

    )
  }
}

// DELETE
class Delete extends Component {
  state = {
    id: 0,
  }

  handleChange = event => {
    this.setState({ id: event.target.value });
  }

  handleSubmit = event => {
    event.preventDefault();

    axios
      .delete('http://localhost:8888/student/' + this.state.id)
      .then(response => {
        alert('Delete successfully')
        console.log(response);
        console.log(response.data);
      })
  }

  render() {
    return (
      <div>
        <form style={text_center} onSubmit={this.handleSubmit}>
          <input type="text" placeholder="ID" onChange={this.handleChange} />
          <input type="submit" value="Delete" />
        </form>
      </div>
    )
  }
}

//POST
class Post extends Component {
  state = {
    name: '',
    age: 0
  }

  handleName = event => {
    this.setState({ name: event.target.value })
  }
  handleAge = event => {
    this.setState({ age: event.target.value })
  }

  handleSubmit = event => {
    event.preventDefault();

    const name = this.state.name;
    const age = this.state.age;


    axios
      .post('http://localhost:8888/student/', { name, age })
      .then(response => {
        console.log(response.data)
      })
  }
  render() {
    return (
      <div>
        <form style={text_center} onSubmit={this.handleSubmit}>
          <label>
            <input type="text" placeholder="Name" onChange={this.handleName} />
          </label>
          <br></br>
          <label>
            <input type="text" placeholder="Age" onChange={this.handleAge} />
          </label>
          <br></br>
          <button type="submit">Add</button>
        </form>
      </div>
    )
  }
}

//PUT
class Put extends Component {
  state = {
    id: 0,
    name: '',
    age: 0
  }

  handleName = event => {
    this.setState({ name: event.target.value })
  }
  handleID = event => {
    this.setState({ id: event.target.value })
  }
  handleAge = event => {
    this.setState({ age: event.target.value })
  }

  handleSubmit = event => {
    event.preventDefault();

    const name = this.state.name;
    const age = this.state.age;
    const id = this.state.id;


    axios
      .put('http://localhost:8888/student/'+id, {id,name,age})
      .then(response => {
        console.log(response.data)
      })
  }
  render() {
    return (
      <div>
        <form style={text_center} onSubmit={this.handleSubmit}>
          <label>
            <input type="text" placeholder="ID" onChange={this.handleID} />
          </label>
          <br></br>
          <label>
            <input type="text" placeholder="Name" onChange={this.handleName} />
          </label>
          <br></br>
          <label>
            <input type="text" placeholder="Age" onChange={this.handleAge} />
          </label>
          <br></br>
          <button type="submit">Update</button>
        </form>
      </div>
    )
  }
}

const width = {
  width: '100px',
  marginLeft: '10px'
}
const width1 = {
  marginLeft: '10%', width: '80%', textAlign: 'center'
}
const text_center = {
  textAlign: 'center'
}
const margin = {
  margin: '2% 0'
}
function Show() {
  return (
    <Router>
      <div>
        <div style={Object.assign(margin, text_center)}>
          <Link to="/get"><button id="get" style={width}>GET</button></Link>
          <Link to="/post"><button id="post" style={width}>POST</button></Link>
          <Link to="/put"><button id="put" style={width}>PUT</button></Link>
          <Link to="/delete"><button id="delete" style={width}>DELETE</button></Link>
        </div>

        <Route path="/get" component={Get1} />
        <Route path="/post" component={Post1} />
        <Route path="/put" component={Put1} />
        <Route path="/delete" component={Delete1} />
      </div>
    </Router>
  );


}

function Get1() {
  return (
    <div>
      <Get />
    </div>
  );
}
function Post1() {
  return (
    <div>
      <Post />
    </div>
  );
}
function Put1() {
  return(
    <div>
      <Put />
    </div>
  );
}
function Delete1() {
  return (
    <div>
      <Delete />
    </div>
  );
}

export default Show;
