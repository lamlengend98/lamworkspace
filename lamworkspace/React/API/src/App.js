import React, { Component } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import { Button } from 'react-bootstrap';
import { Table } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
// import { ConnectionOptions } from 'mysql';
// import { connect } from 'net';

// class DBConnect extends ConnectionOptions{
//   host: '127.0.0.1';
//   port: '3306';
//   user: 'root';
//   password: 'root@123';
//   database: 'student';
//   charset: 'UTF-8'
// }

function Show() {
  return (
    <Router>
      <div className="container">
        <div style={Object.assign(margin, text_center)}>
          <Link to="/get"><Button variant="dark" id="get" style={width}>GET</Button></Link>
          <Link to="/post"><Button variant="dark" id="post" style={width}>POST</Button></Link>
          <Link to="/put"><Button variant="dark" id="put" style={width}>PUT</Button></Link>
          <Link to="/delete"><Button variant="dark" id="delete" style={width}>DELETE</Button></Link>
        </div>

        <Route path="/get" component={Gett} />
        <Route path="/post" component={Postt} />
        <Route path="/put" component={Putt} />
        <Route path="/delete" component={Deletee} />
      </div>
    </Router>
  );
}

function Gett() {
  return (
    <div>
      <Get />
    </div>
  );
}
function Postt() {
  return (
    <div>
      <Post />
    </div>
  );
}
function Putt() {
  return (
    <div>
      <Put />
    </div>
  );
}
function Deletee() {
  return (
    <div>
      <Delete />
    </div>
  );
}

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
      <div className="container">
      <div style={text_center}>
        <Table style={width1}>
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
        </Table>
      </div>
      </div>

    )
  }
}

// DELETE
class Delete extends Component {
  state = {
    id: 0,
    isDeleted: false,
    idError: 'Please insert an ID'
  }

  handleChange = event => {
    this.setState({ id: event.target.value });
  }

  handleSubmit = event => {
    event.preventDefault();
    const isValid = this.validate();

    if (isValid) {
      // if(event.local.conn.query('delete from student where id = '+this.state.id)){
      //   console.log('true')
      // }
      axios
        .delete('http://localhost:8888/student/' + this.state.id)
        .then(response => {
          alert('Delete successfully!!');
          this.setState({ isDeleted: true })
        })
    } else {
      alert(this.state.idError)
    }

  }

  validate = () => {
    let idError = "";

    if (!this.state.id) {
      idError = "Please insert an ID";
    }
    if (idError) {
      this.setState({ idError: idError })
      return false;
    }
    console.log(idError)
    return true;
  }

  render() {
    if (this.state.isDeleted == true) {
      return <Redirect to="/get" />
    }
    return (
      <div>
        <Form style={text_center} onSubmit={this.handleSubmit}>
          <Form.Group>
            <Form.Control type="number" style={form_control} placeholder="ID" onChange={this.handleChange} />
            <Button variant="danger" type="submit" >Delete</Button>
          </Form.Group>

        </Form>
      </div>
    )
  }
}

//POST
class Post extends Component {
  state = {
    name: '',
    age: 0,
    isAdded: false,
    isError: 'Please insert value to all fields'

  }

  handleName = event => {
    this.setState({ name: event.target.value })
  }
  handleAge = event => {
    this.setState({ age: event.target.value })
  }

  validate = () => {
    let isError = "";


    if (!this.state.name || !this.state.age) {
      isError = "Please insert value to all fields";

    } if (isError) {
      this.setState({ isError: isError });
      return false;
    }
    return true;
  }

  handleSubmit = event => {
    event.preventDefault();

    const name = this.state.name;
    const age = this.state.age;

    const isValid = this.validate()
    if (isValid) {
      // this.setState(defaultState);

      axios
        .post('http://localhost:8888/student/', { name, age })
        .then(response => {
          alert("Add successfully!!")
          this.setState({ isAdded: true })
        }).catch((err) => {
          console.log(err)
        })
    } else {
      alert(this.state.isError)
    }


  }
  render() {
    if (this.state.isAdded == true) {
      return <Redirect to="/get" />
    }
    return (
      <div>
        <Form style={text_center} onSubmit={this.handleSubmit}>
          <Form.Group>
            <Form.Control type="text" style={form_control} placeholder="Name" onChange={this.handleName} />
            <Form.Control type="number" style={form_control} placeholder="Age" onChange={this.handleAge} />
            <Button variant="info" type="submit" >Add</Button>
          </Form.Group>

        </Form>
      </div>
    )
  }
}



//PUT
class Put extends Component {
  state = {
    id: 0,
    name: '',
    age: 0,
    isUpdated: false,
    isError: 'Please insert value to all fields'
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
  validate = () => {
    let isError = '';

    if (!this.state.id || !this.state.name || !this.state.age) {
      isError = 'Please insert value to all fieldsror'
    } if (isError) {
      this.setState({ isError: isError })
      return false;
    }
    return true;
  }

  handleSubmit = event => {
    event.preventDefault();

    const name = this.state.name;
    const age = this.state.age;
    const id = this.state.id;
    const isValid = this.validate();
    if (isValid) {
      axios
        .put('http://localhost:8888/student/' + id, { id, name, age })
        .then(response => {
          alert('Update successfully!!')
          this.setState({ isUpdated: true })
        })
    } else{
      alert(this.state.isError)
    }

  }
  render() {

    if (this.state.isUpdated == true) {
      return <Redirect to="/get" />
    }
    return (
      <div>
        <Form style={text_center} onSubmit={this.handleSubmit}>
          <Form.Group>
            <Form.Control type="number" style={form_control} placeholder="ID" onChange={this.handleID} />
            <Form.Control type="text" style={form_control} placeholder="Name" onChange={this.handleName} />
            <Form.Control type="number" style={form_control} placeholder="Age" onChange={this.handleAge} />
            <Button variant="success" type="submit" >Update</Button>
          </Form.Group>

        </Form>
      </div>
    )
  }
}

const width = {
  width: '100px',
  marginLeft: '10px'
}
const width1 = {
  marginLeft: '', width: '100%', textAlign: 'center'
}
const text_center = {
  textAlign: 'center'
}
const form_control = {
  margin: '1% 0 1% 22%', width: '433px'
}
const margin = {
  margin: '2% 0'
}


export default Show;
