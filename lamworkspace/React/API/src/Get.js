import React, { Component } from 'react'
import axios from 'axios'

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

  export default Get;