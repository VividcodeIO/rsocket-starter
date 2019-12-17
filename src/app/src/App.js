import React from 'react';
import './App.css';
import ResponseMessage from "./ResponseMessage";
import MessageService from "./MessageService";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      connected: false,
      error: null,
      input: '',
      messages: [],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSend = this.handleSend.bind(this);
    this._service = new MessageService(this.handleConnection.bind(this), this.handleResponseMessage.bind(this));
  }

  handleConnection(error) {
    this.setState({
      connected: !error,
      error,
    });
  }

  handleChange(event) {
    this.setState({input: event.target.value});
  }

  handleSend() {
    this._service.send(this.state.input);
  }

  handleResponseMessage(error, message) {
    if (error) {
      this.setState({
        error,
      });
      console.error(error);
    } else {
      this.setState({
        input: '',
        messages: [
          ...this.state.messages,
          {
            message,
            timestamp: new Date().toISOString(),
          },
        ],
      });
    }
  }

  componentDidMount() {
    this._service.connect();
  }

  render() {
    const {input, connected, error, messages} = this.state;
    return (
      <div className="App">
        <h1>RSocket WebSocket example</h1>
        {error && <div className="error">{error.message}</div>}
        <div>
          <label>
            Input:
            <input type="text" value={input} disabled={!connected} onChange={this.handleChange}/>
            <button disabled={!connected} onClick={this.handleSend}>Send</button>
          </label>
        </div>
        <h2>Response messages</h2>
        {messages.map((message, index) => ResponseMessage({message, index}))}
      </div>
    );
  }
}

export default App;
