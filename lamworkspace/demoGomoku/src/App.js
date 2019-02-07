import React, { Component } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';

class Square extends React.Component {
  render() {
    return (
      <button className="square" onClick={() => this.props.onClick()}>
        {this.props.value}
      </button>
    );
  }
}

class Board extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      squares: Array(900).fill(null),
      xIsNext: true,
      winner: null
    };
  }
  handleClick(i) {
    const squares = this.state.squares;
    console.log(this.state.squares);
    if (squares[i] || this.state.winner) {
      return;
    }
    squares[i] = this.state.xIsNext ? 'X' : 'O';
    this.setState({
      squares: squares,
      xIsNext: !this.state.xIsNext,
    });
    if (calculateWinner(i, squares[i], squares)) {
      this.setState({
        winner: squares[i],
      });
    }
  }
  renderSquare(i) {
    return <Square value={this.state.squares[i]} onClick={() => this.handleClick(i)} />;
  }
  reset() {
    this.setState(
      {
        squares: Array(900).fill(null),
        xIsNext: true,
        winner: null
      }
    )
  }
  render() {
    let status;
    if (this.state.winner) {
      status = 'Winner: ' + this.state.winner;
    } else {
      status = 'Next player: ' + (this.state.xIsNext ? 'X' : 'O');
    }
    let i = -1;
    const row = Array(30).fill(null);
    const col = Array(30).fill(null);
    return (
      <div className='container'>
        <div className="status">{status}</div>
        <button className='btn btn-primary' onClick={() => this.reset()}>Re-Play</button>
        <br />
        {
          row.map(e => {
            return (<div className="board-row">{
              col.map(v => { i++; return this.renderSquare(i) })
            }</div>)
          })
        }

      </div>

    );
  }
}

class Game extends React.Component {
  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Board />
        </div>
        <div className="game-info">
          <div>{/* status */}</div>
          <ol>{/* TODO */}</ol>
        </div>
      </div>
    );
  }
}

function calculateWinner(i, square, squares) {
  for (let m = 1; m <= 5; m++) {
    //Ngang
    for (let n = i - 5 + m; n <= i - 1 + m; n++) {

      if (squares[n] !== square)
        break;
      if (n === (i - 1 + m)) {
        return square;
      }
    }
    //Doc
    for (let n = i - (30 * (5 - m)); n <= i - 30 + (30 * m); n += 30) {
      if (squares[n] !== square)
        break;
      if (n === (i - 30 + (m * 30))) {
        return square;
      }
    }
    //Cheo tang
    for (let n = i - (31 * (5 - m)); n <= i - 31 + (31 * m); n += 31) {
      if (squares[n] !== square)
        break;
      if (n === (i - 31 + (m * 31))) {
        return square;
      }
    }
    //Cheo giam
    for (let n = i - (29 * (5 - m)); n <= i - 29 + (29 * m); n += 29) {
      if (squares[n] !== square)
        break;
      if (n === (i - 29 + (m * 29))) {
        return square;
      }
    }
  }
  return null;
}


export default Game;
