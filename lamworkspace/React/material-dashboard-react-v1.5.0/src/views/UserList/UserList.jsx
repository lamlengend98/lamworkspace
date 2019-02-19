import React, { Component } from 'react'
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import { Button } from 'react-bootstrap';
import { Table } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import withStyles from "@material-ui/core/styles/withStyles";
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import Grid from '@material-ui/core/Grid';
import CustomInput from "components/CustomInput/CustomInput.jsx";
import TextField from '@material-ui/core/TextField';

const styles1 = {
  cardCategoryWhite: {
    "&,& a,& a:hover,& a:focus": {
      color: "rgba(255,255,255,.62)",
      margin: "0",
      fontSize: "14px",
      marginTop: "0",
      marginBottom: "0"
    },
    "& a,& a:hover,& a:focus": {
      color: "#FFFFFF"
    }
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none",
    "& small": {
      color: "#777",
      fontSize: "65%",
      fontWeight: "400",
      lineHeight: "1"
    }
  },
  tableUpgradeWrapper: {
    display: "block",
    width: "100%",
    overflowX: "auto",
    WebkitOverflowScrolling: "touch",
    MsOverflowStyle: "-ms-autohiding-scrollbar"
  },
  table: {
    width: "100%",
    maxWidth: "100%",
    marginBottom: "1rem",
    backgroundColor: "transparent",
    borderCollapse: "collapse",
    display: "table",
    borderSpacing: "2px",
    borderColor: "grey",
    "& thdead tr th": {
      fontSize: "1.063rem",
      padding: "12px 8px",
      verticalAlign: "middle",
      fontWeight: "300",
      borderTopWidth: "0",
      borderBottom: "1px solid rgba(0, 0, 0, 0.06)",
      textAlign: "inherit"
    },
    "& tbody tr td": {
      padding: "12px 8px",
      verticalAlign: "middle",
      borderTop: "1px solid rgba(0, 0, 0, 0.06)"
    },
    "& td, & th": {
      display: "table-cell"
    }
  },
  center: {
    textAlign: "center"
  }
};

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
          <Card>
            <CardHeader plain color="primary">
              <h4>Here is User List</h4>
            </CardHeader>
            <CardBody>
              <Table style={width1}>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Age</th>
                  <th>Options</th>
                </tr>

                {this.state.user.map(users =>
                  <tr>
                    <td key={users.id}>{users.id}</td>
                    <td key={users.id}>{users.name}</td>
                    <td key={users.id}>{users.age}</td>
                    <td>
                      <Router>
                        <div>
                          <Link onClick={() => { window.location.reload() }}
                            to={`/userList/edit/${users.id}&&${users.name}&&${users.age}`}>
                            <Button variant="info" style={buttonCs} type="submit"><i class="fas fa-edit"></i></Button></Link>

                          <Link onClick={() => { window.location.reload() }}
                            to={`/userList/delete/${users.id}`}>
                            <Button variant="danger" style={buttonCs} type="submit"><i class="fas fa-trash-alt"></i></Button></Link>
                        </div>

                      </Router>
                    </td>
                  </tr>
                )}
              </Table>
              <CardFooter>
                <Router>
                  <div>
                    <Link onClick={() => { window.location.reload(); }}
                      to="/userList/add/">
                      <Button style={buttonCs} color="primary"><i class="fas fa-plus-circle"></i></Button></Link>
                  </div>
                </Router>
              </CardFooter>
            </CardBody>
          </Card>

        </div>
      </div>

    )
  }
}

class Delete extends Component {
  state = {
    id: this.props.match.params.id,
    isDeleted: false,
    idError: 'Please insert an ID'
  }

  render() {

    console.log(this.state.id);

    axios
      .delete('http://localhost:8888/student/' + this.state.id)
      .then(response => {
        alert('Delete successfully!!');
        this.setState({ isDeleted: true });
        console.log(this.state.isDeleted)
      })

    return <Redirect to="/userList/" />

  }
}


//POST
class Post extends Component {
  constructor(p) {
    super(p)
    console.log("asdad")
  }
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
    if (this.state.isAdded === true) {
      return <Redirect to="/userList" />
    }
    return (
      <div>
        <Link to="/userList/"><Button><i class="fas fa-backward"></i></Button></Link>
        <Card>
          <CardHeader plain color="primary">
            <h4 style={text_center}>Add User</h4>
          </CardHeader>
          <CardBody>
            <Form style={text_center} onSubmit={this.handleSubmit}>
              <Grid container
                spacing={0}
                alignItems="center"
                justify="center">
                <GridItem xs={12} sm={12} md={6} alignItems="center">
                  <CustomInput
                    labelText="Name"
                    id="name"
                    formControlProps={{
                      fullWidth: true
                    }}
                    inputProps={{
                      onChange: this.handleName
                    }}


                  />
                </GridItem>
              </Grid>
              <Grid container
                spacing={0}
                alignItems="center"
                justify="center">
                <GridItem xs={12} sm={12} md={6}>
                  <CustomInput
                    labelText="Age"
                    id="age"
                    formControlProps={{
                      fullWidth: true
                    }}
                    inputProps={{
                      onChange: this.handleAge
                    }}
                  />
                </GridItem>
              </Grid>
              <Button variant="info" type="submit" style={buttonCs}><i class="fas fa-plus-circle"></i></Button>
            </Form>


          </CardBody>
        </Card>
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
    const inputName = event.target.value
    this.setState({
      name: inputName
    })
    //  console.log(this.state.name)
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
      isError = 'Please insert value to all fields'
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
    } else {
      alert(this.state.isError)
    }

  }
  render() {
    const id = this.props.match.params.id;
    const name = this.props.match.params.name;
    const age = this.props.match.params.age;
    if (this.state.id === 0 && this.state.name === "" && this.state.age === 0) this.setState({
      id: id, name: name, age: age
    })
    if (this.state.isUpdated === true) {
      return <Redirect to="/userList" />
    }
    return (
      <div>
        <Link to="/userList/"><Button><i class="fas fa-backward"></i></Button></Link>
        <Card>
          <CardHeader plain color="primary">
            <h4 style={text_center}>Update User</h4>
          </CardHeader>
          <CardBody>
            <Form style={text_center} onSubmit={this.handleSubmit}>
              <Grid container
                spacing={0}
                alignItems="center"
                justify="center">
                <GridItem xs={12} sm={12} md={6} alignItems="center">
                  <TextField
                    id="id"
                    label="ID"
                    value={this.state.id}
                    fullWidth
                    style={{ margin: "4% 0" }}
                    onChange={this.handleID}
                  />
                </GridItem>
              </Grid>
              <Grid container
                spacing={0}
                alignItems="center"
                justify="center">
                <GridItem xs={12} sm={12} md={6} alignItems="center">
                  <TextField
                    id="name"
                    label="Name"
                    value={this.state.name}
                    fullWidth
                    style={{ margin: "4% 0" }}
                    onChange={this.handleName}
                  />
                </GridItem>
              </Grid>
              <Grid container
                spacing={0}
                alignItems="center"
                justify="center">
                <GridItem xs={12} sm={12} md={6} alignItems="center">
                  <TextField
                    id="age"
                    label="Age"
                    value={this.state.age}
                    fullWidth
                    style={{ margin: "4% 0" }}
                    onChange={this.handleAge}
                  />
                </GridItem>
              </Grid>
              <Button variant="success" type="submit" style={buttonCs} ><i class="fas fa-edit"></i></Button>
            </Form>
          </CardBody>
        </Card>
      </div>
    )
  }
}

const width1 = {
  marginLeft: '', width: '100%', textAlign: 'center'
}
const text_center = {
  textAlign: 'center'
}
const buttonCs = {
  margin: '1% 2%',
  width: '65px',
  textAlign: 'center'
}

function userListPage() {
  return (
    <Router>
      <div>
        <Switch>
          <Route path="/userList/edit/:id&&:name&&:age" exact component={Put} />
          <Route path="/userList/add/" exact component={Postt} />
          <Route path="/userList/delete/:id" exact component={Delete} />
          <Route path="/userList/" exact component={Gett} />
        </Switch>
      </div>

    </Router>
  );
}

export default withStyles(styles1)(userListPage);
